package org.example;
import org.example.dataRequest.Enum.ProductRegisterState;
import org.example.dataRequest.ProductInstanceRequest;
import org.example.dataRequest.ProductRegisterRequest;
import org.example.dataRequest.ResponceBuilder;
import org.example.dataRequest.ValidatedProductInstanceRequest;
import org.example.entity.AccountEntity;
import org.example.entity.TppProductRegisterEntity;
import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.example.dataRequest.ResponceBuilder.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductRegisterController {

    @Autowired
    ProductRegisterService productRegisterService;

    public ResponseEntity<?> handleProductRegister ( @RequestBody @Valid ProductRegisterRequest request,BindingResult result)
    {
        //шаг 1
        if (result.hasErrors() ) {
            List<FieldError> errors =result.getFieldErrors();
            StringBuilder errorMessage =new StringBuilder();
            for (FieldError error: errors) {
                errorMessage.append(" ").append(error.getField()).append("-")
                        .append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ошибки в запросе:" +errors);
        }
        return productRegisterService.processProductRegister(request);
    }

}
