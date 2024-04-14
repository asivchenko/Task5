package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dataRequest.*;
import org.example.exception.OtherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.lang.StringBuilder;
@Component
public class RequestMapper {
    private final Validator validator;

    public RequestMapper (Validator validator)
    {
        this.validator=validator;
    }
    @Autowired
    private ObjectMapper objectMapper;
    public ValidatedProductInstanceRequest mapToProductInstanceRequest(@RequestBody @Valid String  jsonRequestData ) {
        ProductInstanceRequest request;
        ValidatedProductInstanceRequest validatedRequest;
        try {
            if (jsonRequestData == null || jsonRequestData.isEmpty()) {
                throw new IllegalAccessException("Нет запроса");
            }
            request = objectMapper.readValue(jsonRequestData, ProductInstanceRequest.class);
            validatedRequest =new ValidatedProductInstanceRequest(request);
            String errors ="";  // строка всех ошибок по всем объектам запроса
            String errorMessage=createErrorObject(request,validator );
            System.out.println("errorMessage==========" + errorMessage);

            if (errorMessage.length()>0 && errorMessage !=null ) {
                errors = "request.body: " + errorMessage;
            }
            AdditionalPropertyVip requestproperties =request.getAdditionalPropertiesVip();
            if (requestproperties !=null) {
                List<AdditionalProperty> properties = requestproperties.getData();
                errorMessage = createErrorIterable(properties, validator);
                if (errorMessage != null && errorMessage.length() > 0)
                    errors = errors + "\n AdditionalProperty: : " + errorMessage;
            }
            List<InstanceArrangement>  arrangements =request.getInstanceArrangement();
            if (arrangements !=null ) {
                errorMessage = createErrorIterable(arrangements, validator);
                if (errorMessage != null && errorMessage.length() > 0)
                    errors = errors + "\n instanceArrangement: " + errorMessage;
            }
            if (errors !=null && errors.length()>0 ) {
                validatedRequest.setErrormessage(errors);
                return validatedRequest;
            }
        }
        catch (IllegalAccessException e ){
            e.printStackTrace();
            throw new OtherException("Нет запроса :" +e.getMessage());

        }
        catch (JsonProcessingException e) {
            throw new OtherException(" Ошибка парсинга Jso: "+ e.getMessage());
        }
        return validatedRequest;
    }
    private <T> String createErrorObject (T object,Validator validator)
    { // для обобщеного типа
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        String errorMessage="";
        if (!violations.isEmpty()) {
            // Если есть ошибки валидации, собираем их в строку
            for (ConstraintViolation<T> violation : violations) {
                String  fieldname =violation.getPropertyPath().toString();
                errorMessage=errorMessage.length()>0 ? errorMessage+ ", '"+fieldname +"'" : errorMessage+ "'"+fieldname +"'";
            }
        }
        return errorMessage;
    }
    private String createErrorIterable (Iterable<?> request ,Validator validator)
    {  // для коллеций реализующих интерфейс Itrable
        String errorMessage = "";
        int kol=0; //количество экземпляров List
        boolean  firstError=true; // признак первой ошибки в строке
        for (Object obj: request) {
            kol=kol+1;
            String error=createErrorObject(obj,validator );
            if (error !=null && error.length()>0){
                errorMessage=errorMessage +"\n Строка " +kol +": "+error;
            }
        }
        return errorMessage;
    }
}
