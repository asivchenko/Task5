




import com.fasterxml.jackson.core.JsonProcessingException;
import org.checkerframework.checker.nullness.qual.AssertNonNullIfNonNull;
import org.example.*;
import  org.example.ProductRegisterController.*;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.*;
@SpringBootTest(classes = MainApplication.class)
@AutoConfigureMockMvc
public class ProductInstanceControllerTest {
     @Autowired
     private ProductInstanceController productInstanceController;
    //@Autowired
    //private MockMvc mockMvc;

    //@MockBean
    //private ProductInstanceServis productInstanceService;


    @Test
    @DisplayName( "Тестирование мапера  ")
    public void testWorkWraper_returnBadRequest () throws Exception {
        String jsonFilePath ="Jsontest/RequestProductInstance.json";
        ClassPathResource resource =new ClassPathResource(jsonFilePath);
        byte[] fileBytes  = FileCopyUtils.copyToByteArray(resource.getInputStream());
        String requestJson = new String (fileBytes);
        // получили строку json с bad request
        String excpected = "Не заполнены параметры запроса: request.body: 'contractNumber'\n" +
                " instanceArrangement: \n" +
                " Строка 1: 'number'";
        ResponseEntity<?> responceEntity= productInstanceController.handleProductInstance(requestJson);
        assertEquals(HttpStatus.BAD_REQUEST,responceEntity.getStatusCode()); //
        assertTrue(responceEntity.getBody() instanceof  String); //тело отвечает
        assertEquals(excpected,responceEntity.getBody());
    }
    //  и так далее  писать через
    //  ResponseEntity<?> responceEntity= productInstanceController.handleProductInstance(requestJson);
    //  и делая моки на методы можно написать тесты  по всем веткам
}
