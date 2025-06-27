package org.example;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataRequest.AdditionalProperty;
import org.example.dataRequest.InstanceArrangement;
import org.example.dataRequest.ProductInstanceRequest;
import org.example.dataRequest.ProductRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
public class RouterController {

   @Autowired
    private  ObjectMapper objectMapper;
    @Autowired
    private ProductRegisterController productRegisterController;

    @Autowired
    private ProductInstanceController productInstanceController;
    @PostMapping (value="/corporate-settlement-account/create",
            //consumes = "application/json;charset=UTF-8",
            //produces = "application/json;charset=UTF-8"  //всегда  UTF-8

            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
                 )

    public ResponseEntity<?> RouteToRegisterController  (@RequestBody @Valid ProductRegisterRequest requestBody,
                                                         BindingResult result) {
    return productRegisterController.handleProductRegister(requestBody,result);
    }

    // вариант получения запроса и обработки (через Mapper) ошибок
    @PostMapping(value = "/corporate-settlement-instance/create",
                         //consumes = "application/json;charset=UTF-8",
                         //produces = "application/json;charset=UTF-8"
                         consumes = MediaType.APPLICATION_JSON_VALUE,
                         produces = MediaType.APPLICATION_JSON_VALUE
                 )
    public ResponseEntity<?> RouteToInstanceController( @RequestBody @Valid  String jsonRequestData ) {
        return productInstanceController.handleProductInstance(jsonRequestData);
    }

//
////////------------------------------Отладка ------------------------------
//    @PostMapping(value = "/corporate-settlement-instance/create", consumes = "application/json;charset=UTF-8",
//                                                                   produces = "application/json;charset=UTF-8")
////--              // produces = MediaType.APPLICATION_JSON_UTF8_VALUE
//// --  // public ResponseEntity<?> routeToRegisterController11(
//// --   //        @RequestBody ProductInstanceRequest request  ) {
//     public ResponseEntity<?> RouteToInstanceController( @RequestBody String jsonRequestData  ) {
////--    //    ObjectMapper custom =new ObjectMapper();
////--      //  objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
////--        //JsonFactory jsonFactory= new JsonFactory();
////--        //jsonFactory.setCodec(objectMapper);
////--      //  objectMapper.setFa.setFactory
////--      //  JsonFactory jsonFactory =objectMapper.getFactory();
////--      //  objectMapper.setFa
////
//        try {
//            StringBuilder stroka = new StringBuilder();
//            ProductInstanceRequest request = objectMapper.readValue(jsonRequestData, ProductInstanceRequest.class);
//            List<AdditionalProperty> properties =request.getAdditionalProperties();
//            stroka.append(request.getInstanceId());
//            stroka.append("data.key="+request.getData().getKey());
//            stroka.append("data.value="+request.getData().getValue());
////--            //for (AdditionalProperty property : properties )
////--           // {
////--           //     stroka.append("Key=" +property.getKey());
////--            //}
//
//             List<InstanceArrangement>  arrangements =request.getInstanceArrangement();
//            for (InstanceArrangement arrangement : arrangements )
//            {
//                stroka.append(" rate=" +arrangement.getMaximalnterestRateCoefficient());
//            }
//
//        return ResponseEntity.ok().body(" Привет 888 Сивченко request=" + stroka);
//        }
//        catch (IOException e){
//            e.printStackTrace();
//
//        return ResponseEntity.badRequest().body("Ошибка в запросе");
//            }
//    }
////    //-----------------------------Конец отладки
}



