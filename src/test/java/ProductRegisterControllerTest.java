

import com.fasterxml.jackson.core.JsonProcessingException;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.example.MainApplication;
import org.example.ProductRegisterController;
import  org.example.ProductRegisterController.*;
import org.example.ProductRegisterServis;
import org.example.ProductRegisterServis.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataRequest.ProductRegisterRequest;
import org.example.dataRequest.ResponceBuilder;
import org.example.entity.AccountEntity;
import org.example.entity.TppProductRegisterEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.FileCopyUtils;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
    @SpringBootTest(classes = MainApplication.class)
    @AutoConfigureMockMvc
    public class ProductRegisterControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductRegisterServis productRegisterService;

        @Test
        @DisplayName("Тестируем проверку на дубль параметра registryTypeCode для экземпляра продукта ")
        public void testProcessRequestCheckDubl() throws Exception {
            // Создаем JSON-строку с данными запроса
            String jsonFilePath ="Jsontest/requestproductregist.json";
            ClassPathResource resource =new ClassPathResource(jsonFilePath);
            byte[] fileBytes  = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String requestJson = new String (fileBytes);
            //////////////////////
            when(productRegisterService.checkDubl(any(Integer.class), any(String.class))).thenReturn(true);

            // Отправляем запрос на обработку и проверяем статус ответа
            mockMvc.perform(MockMvcRequestBuilders.post("/corporate-settlement-account/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andExpect(MockMvcResultMatchers.content().string("Параметр registryTypeCode тип регистра <02.001.005_45343_CoDowFF> уже существует для ЭП с ИД  <20>."));

        }


        @Test
        @DisplayName("Тестируем проверку валидности параметра registryTypeCode по справочнику tpp_ref_product_register_type")
        void testProcessRequestValidRegisterTypeCode() throws JsonProcessingException,Exception {
         ProductRegisterServis productRegisterService = Mockito.mock(ProductRegisterServis.class);
         ProductRegisterController controller = new ProductRegisterController();
         String jsonFilePath ="Jsontest/requestproductregist.json";
         ClassPathResource resource =new ClassPathResource(jsonFilePath);
         byte[] fileBytes  = FileCopyUtils.copyToByteArray(resource.getInputStream());
         String requestJson = new String (fileBytes);
         ObjectMapper objectMapper = new ObjectMapper();
         ProductRegisterRequest request = objectMapper.readValue(requestJson, ProductRegisterRequest.class);
         when(productRegisterService.getType(any())).thenReturn(Optional.empty());
            mockMvc.perform(MockMvcRequestBuilders.post("/corporate-settlement-account/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andExpect(MockMvcResultMatchers.content().string(
                            "Код продукта <02.001.005_45343_CoDowFF> " +
                                    "не найдено в Каталоге продуктов <public.tpp_ref_product_register_type> для данного типа регистра"
                    ));
        }
        @Test
        @DisplayName("Тестируем создание записи ProductRegist отправку сообщения со статусом OK")
        void testProcessCreateProductRegister() throws JsonProcessingException,Exception {
            ProductRegisterServis productRegisterService = Mockito.mock(ProductRegisterServis.class);
            String axpectedJson ="{\"data\":{\"registerId\":\"23\"}}";
            AccountEntity mockAccount =new AccountEntity();
            mockAccount.setId(100);
            when (productRegisterService.getAccount(any(String.class),any(String.class),any(String.class),any(String.class),any(String.class)))
                    .thenReturn(mockAccount);
            TppProductRegisterEntity mockProductRegister =new TppProductRegisterEntity();
            mockProductRegister.setId(23);
            when (productRegisterService.createProductRegister(any(AccountEntity.class),any(TppProductRegisterEntity.class)))
                    .thenReturn(mockProductRegister);
            ResponseEntity<?> responseEntity=ResponseEntity.ok().body(ResponceBuilder.buildResponseRegisterId(23));
            assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
            assertNotNull(responseEntity.getBody());
            assertEquals(axpectedJson,responseEntity.getBody());
        }
    }


