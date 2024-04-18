




import org.example.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.util.FileCopyUtils;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

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
