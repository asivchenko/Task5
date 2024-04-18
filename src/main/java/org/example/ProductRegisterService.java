package org.example;

import org.example.dataRequest.Enum.ProductRegisterState;
import org.example.dataRequest.ProductRegisterRequest;
import org.example.entity.AccountEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static org.example.dataRequest.ResponceBuilder.buildResponseRegisterId;
@Service
public class ProductRegisterService {
    @Autowired
    ProductRegisterUtils registerUtils ;
    public ResponseEntity<?> processProductRegister (ProductRegisterRequest request)
    {
        // шаг 2  Проверка таблицы ПР (таблица tpp_product_register) на дубли.
        // Для этого необходимо отобрать строки по
        // условию tpp_product_register.product_id == Request.Body.InstanceID
        // и у результата отобрать строки по условию
        // tpp_product_register.type == Request.Body.registryTypeCode.
        // Если результат отбора не пуст, значит имеются повторы
        if (registerUtils.checkDubl(request.getInstanceId(),request.getRegistryTypeCode())) {
            // status 404
            return ResponseEntity.badRequest().body("Параметр registryTypeCode тип регистра <"
                    + request.getRegistryTypeCode() + "> уже существует для ЭП с ИД  <"
                    + request.getInstanceId() + ">.");
        }
        //Шаг 3.  Взять значение из Request.Body.registryTypeCode и
        // найти соответствующие ему записи в tpp_ref_product_register_type.value.
        //  Если найдено совпадение перейти на Шаг 4.
        //  Если совпадений не обнаружено

        Optional<TppRefProductRegisterTypeEntity> productRegisterTypeOptional = registerUtils.getType(request.getRegistryTypeCode());
        if (productRegisterTypeOptional.isPresent())
        {          TppRefProductRegisterTypeEntity productRegisterType=productRegisterTypeOptional.get();

            // Tpp_product_register.type внешний ключ на таблицу Трр_Ref_Product_Register_type.id
            // но type c типом  varchar и равен униальному значение Трр_Ref_Product_Register_type.value
            // неТ смысла запоминать ключ TppRefProductRegisterTypeEntity.id
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body ("Код продукта <"+ request.getRegistryTypeCode() +">"
                    + " не найдено в Каталоге продуктов <public.tpp_ref_product_register_type> для данного типа регистра");

        }

        //шаг 4 находим ссылку на aacount по параметрам
        AccountEntity account = registerUtils.getAccount(request.getBranchCode(),
                request.getCurrencyCode(),request.getMdmCode(),
                request.getPriorityCode(),request.getRegistryTypeCode());
        if (account==null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body ("Код Продукта <" +request.getRegistryTypeCode()
                    + "> не найден счет по указанным реквизитам  для данного типа Регистра");
        ///заполняем запись для добавления для TppProductRegistr

        TppProductRegisterEntity productRegister = new TppProductRegisterEntity();
        productRegister.setProductId((long) request.getInstanceId());
        productRegister.setType(request.getRegistryTypeCode());
        productRegister.setAccount((long) account.getId());
        productRegister.setCurrencyCode(request.getCurrencyCode());
        productRegister.setState(ProductRegisterState.OPEN.name());
        productRegister.setAccountNumber(account.getAccountNumber());
        productRegister = registerUtils.createProductRegister(account, productRegister);
        return ResponseEntity.ok().body( buildResponseRegisterId(productRegister.getId()));
    }


}
