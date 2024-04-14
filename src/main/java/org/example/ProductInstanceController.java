package org.example;

import org.example.dataRequest.*;
import org.example.entity.*;
import org.example.exception.OtherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import static org.springframework.http.ResponseEntity.status;
import static org.example.dataRequest.ResponceBuilder.*;
import org.example.ProductRegisterServis.*;

@RestController
public class ProductInstanceController {

    private final RequestMapper requestMapper;
    @Autowired
    public ProductInstanceController(RequestMapper requestMapper) {   this.requestMapper = requestMapper;
    }
    @Autowired
     ProductInstanceServis productInstanceServis;
    @Autowired
    ProductRegisterServis productRegisterServis;
    public ResponseEntity<?> handleProductInstance (@RequestBody @Valid String jsonRequestData  )  {
        ValidatedProductInstanceRequest  validateRequest;
        ProductInstanceRequest request;
        validateRequest =requestMapper.mapToProductInstanceRequest(jsonRequestData);
            //System.out.println("errorMessage="+ validateRequest.getErrormessage());
            if (validateRequest.getErrormessage() !=null  && !validateRequest.getErrormessage().isEmpty())
                return status(HttpStatus.BAD_REQUEST).body ("Не заполнены параметры запроса: " + validateRequest.getErrormessage());
            request =validateRequest.getRequest();

        if (request.getInstanceId()==null)   // Шаг 2
        {// Сздание ЭП и счетов для ЭП
            //Optional<TppProductEntity> optionalproduct = productInstanceServis.checkDublProduct(request); //Шаг 1.1
            Optional<TppProductEntity> optionalproduct = productInstanceServis.checkDublProduct(request.getContractNumber());
            if (optionalproduct.isPresent()) {
                TppProductEntity productEntity = optionalproduct.get();

                return status(HttpStatus.BAD_REQUEST).body("Параметр ContractNumber № договора <" +
                        request.getContractNumber() + "> уже существует для ЭП с ИД <" +productEntity.getProductCodeId()+">");
            }
            //Шаг 1.2
            //Проверка таблицы ДС (agreement) на дубли
            //Отобрать записи по условию agreement.number == Request.Body.Arrangement[N].Number
            //проверка на дубли agreements

            String errorMessage= productInstanceServis.checkDublAgreement(request.getInstanceArrangement());
            if (errorMessage!=null && errorMessage.length()>0)
                return status(HttpStatus.BAD_REQUEST).body(errorMessage);

            //Шаг 1.3
            //По КодуПродукта найти связные записи в Каталоге Типа регистра
            //Request.Body.ProductCode == tpp_ref_product_class.value
            //tpp_ref_product_class.value справочник value unique проверяем что значение productCode -валидно
            Optional<TppRefProductClassEntity> optimalProductClas =productInstanceServis.findByValue(request.getProductCode());
            if (optimalProductClas.isPresent())
            {   // проверили по справочнику Request.Body.ProductCode
                TppRefProductClassEntity productClass=optimalProductClas.get();
                Long productClassId =  (long) productClass.getInternalId();  // id productClass

                List<TppRefProductRegisterTypeEntity>  productRegistryTypes =
                         productInstanceServis.findByValueAndAccountType(request.getProductCode(),"Клиентский");
                if (productRegistryTypes.isEmpty())
                {
                    return status(HttpStatus.BAD_REQUEST).body("В каталоге типов продуктов TppRefProductRegisterType  нет записей  c кодом продукта <" +
                            request.getProductCode() +"> и account_type = 'Клиентский'");
                }
                try {
                    ResponseData responseData = productInstanceServis.createProductIdAndListProductRegisterID(productClassId,
                            productRegistryTypes, request);
                    return ResponseEntity.ok().body(buildResponse(responseData));
                }
                catch (OtherException e )
                {
                    return status(HttpStatus.NOT_FOUND).body (" Ошибка при поиске счета для договора "+e.getMessage());
                }
            }
            else {
                return status(HttpStatus.BAD_REQUEST).body(": КодПродукта <" + request.getProductCode() + "> не найдено в Каталоге продуктов tpp_ref_product_class");

            }

        }
        else
        {  // экземпляр instanceId задан идем на Шаг 2.1
            // Если ИД ЭП в поле Request.Body.instanceId не пустое, то изменяется состав ДС // сделка (доп.Соглашение)
           //	Перейти на шаг 2.1
            //Проверка таблицы ЭП (tpp_product) на существование ЭП. Для этого необходимо отобрать строки по условию
            // tpp_product.id == Request.Body. instanceId
            Optional<TppProductEntity> optionalproduct = productInstanceServis.findById(request.getInstanceId());
            if (optionalproduct.isPresent()) {
                TppProductEntity product = optionalproduct.get(); //нам нужен id продукта
                // если найден пордукт Шаг 2.2
                //Проверка таблицы ДС (agreement) на дубли
                //Отобрать записи по условию agreement.number == Request.Body.Arrangement[N].Number
                // Если записи найдены•	вернуть Статус: 400/Bad Request
                String errors =productInstanceServis.checkDublAgreement(request.getInstanceArrangement());
                if (errors !=null && errors.length()>0 )
                    return status(HttpStatus.BAD_REQUEST).body("Найдены дубли: " + errors);
                else {
                    ////Контроль пройден для всех
                    //добавляем в agreemens шаг. 2.3
                    ResponseData responseData =productInstanceServis.createAgreementIds(product.getId(),
                                                                           request.getInstanceArrangement());
                    return ResponseEntity.ok().body(buildResponse(responseData));
                }
            }
            else //
                // не найденн экземпляр instanceId
                return status(HttpStatus.NOT_FOUND).body(
                        "Экземпляр продукта с параметром instanceId <"+request.getInstanceId() +"> не найден");

        }
    }
}
