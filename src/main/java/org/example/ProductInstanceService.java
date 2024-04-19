package org.example;
import org.example.dataRequest.ProductInstanceRequest;
import org.example.dataRequest.ResponseData;
import org.example.entity.TppProductEntity;
import org.example.entity.TppRefProductClassEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import static org.example.dataRequest.ResponceBuilder.buildResponse;

@Service
public class ProductInstanceService {
    @Autowired
    ProductInstanceUtils instanceUtils ;
    public ProductRegisterResponse processProductInstance (ProductInstanceRequest request) {
        if (request.getInstanceId() == null) {
            return processNewProductInstance(request);
        } else {
            return processExistingProductInstance(request);
        }
    }

    public ProductRegisterResponse processNewProductInstance (ProductInstanceRequest request ) {
        // Сздание ЭП и счетов для ЭП   шаг 1.1
        Optional<TppProductEntity> optionalproduct = instanceUtils.checkDublProduct(request.getContractNumber());
        if (optionalproduct.isPresent()) {
            TppProductEntity productEntity = optionalproduct.get();
            return new ProductRegisterResponse("Параметр ContractNumber № договора <" +
                              request.getContractNumber() + "> уже существует для ЭП с ИД <" +productEntity.getProductCodeId()+">",
                              HttpStatus.BAD_REQUEST);
        }
        //Шаг 1.2
        //Проверка таблицы ДС (agreement) на дубли
        //Отобрать записи по условию agreement.number == Request.Body.Arrangement[N].Number
        //проверка на дубли agreements

        String errorMessage= instanceUtils.checkDublAgreement(request.getInstanceArrangement());
        if (errorMessage!=null && errorMessage.length()>0)
            return new ProductRegisterResponse(errorMessage,HttpStatus.BAD_REQUEST);


        //Шаг 1.3
        //По КодуПродукта найти связные записи в Каталоге Типа регистра
        //Request.Body.ProductCode == tpp_ref_product_class.value
        //tpp_ref_product_class.value справочник value unique проверяем что значение productCode -валидно
        Optional<TppRefProductClassEntity> optimalProductClas =instanceUtils.findByValue(request.getProductCode());
        if (optimalProductClas.isPresent())
        {   // проверили по справочнику Request.Body.ProductCode
            TppRefProductClassEntity productClass=optimalProductClas.get();
            Long productClassId =  (long) productClass.getInternalId();  // id productClass

            List<TppRefProductRegisterTypeEntity>  productRegistryTypes =
                    instanceUtils.findByValueAndAccountType(request.getProductCode(),"Клиентский");
            if (productRegistryTypes.isEmpty())
            {
                return new ProductRegisterResponse("В каталоге типов продуктов TppRefProductRegisterType  нет записей  c кодом продукта <" +
                        request.getProductCode() +"> и account_type = 'Клиентский'",HttpStatus.BAD_REQUEST);
            }
            try {
               ResponseData responseData = instanceUtils.createProductIdAndListProductRegisterID(productClassId,
                        productRegistryTypes, request);
               return new ProductRegisterResponse(buildResponse(responseData),HttpStatus.OK);

            }
            catch (org.example.exception.OtherException e )
            {
                return new ProductRegisterResponse("Ошибка при поиске счета для договора",HttpStatus.NOT_FOUND );
            }

        }

        else {
            return new ProductRegisterResponse("КодПродукта <" + request.getProductCode() + "> не найдено в Каталоге продуктов tpp_ref_product_class",
                    HttpStatus.BAD_REQUEST);

        }

    }
    public ProductRegisterResponse processExistingProductInstance (ProductInstanceRequest request ) {

        // экземпляр instanceId задан идем на Шаг 2.1
        // Если ИД ЭП в поле Request.Body.instanceId не пустое, то изменяется состав ДС // сделка (доп.Соглашение)
        //	Перейти на шаг 2.1
        //Проверка таблицы ЭП (tpp_product) на существование ЭП. Для этого необходимо отобрать строки по условию
        // tpp_product.id == Request.Body. instanceId
        Optional<TppProductEntity> optionalproduct = instanceUtils.findById(request.getInstanceId());
        if (optionalproduct.isPresent()) {
            TppProductEntity product = optionalproduct.get(); //нам нужен id продукта
            // если найден пордукт Шаг 2.2
            //Проверка таблицы ДС (agreement) на дубли
            //Отобрать записи по условию agreement.number == Request.Body.Arrangement[N].Number
            // Если записи найдены•	вернуть Статус: 400/Bad Request
            String errors =instanceUtils.checkDublAgreement(request.getInstanceArrangement());
            if (errors !=null && errors.length()>0 )
                return new ProductRegisterResponse("Найдены дубли: " + errors,HttpStatus.BAD_REQUEST);
            else {
                ////Контроль пройден для всех
                //добавляем в agreemens шаг. 2.3
                ResponseData responseData =instanceUtils.createAgreementIds(product.getId(),
                        request.getInstanceArrangement());
                return new ProductRegisterResponse(buildResponse(responseData),HttpStatus.OK);
            }
        }
        else //
            // не найденн экземпляр instanceId
            return new ProductRegisterResponse("Экземпляр продукта с параметром instanceId <"+request.getInstanceId() +"> не найден",
                    HttpStatus.NOT_FOUND);

    }
}
