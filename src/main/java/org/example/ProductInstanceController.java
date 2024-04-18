package org.example;

import org.example.dataRequest.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static org.springframework.http.ResponseEntity.status;

@RestController
public class ProductInstanceController {

    private final RequestMapper requestMapper;
    private final ProductInstanceService productInstanceService;
    @Autowired
    public ProductInstanceController(RequestMapper requestMapper, ProductInstanceService productInstanceService) {
        this.requestMapper = requestMapper;
        this.productInstanceService=productInstanceService;
    }
    public ResponseEntity<?> handleProductInstance (@RequestBody @Valid String jsonRequestData  )  {
        ValidatedProductInstanceRequest  validateRequest;
        ProductInstanceRequest request;
        validateRequest =requestMapper.mapToProductInstanceRequest(jsonRequestData);
        if (validateRequest.getErrormessage() !=null  && !validateRequest.getErrormessage().isEmpty())
              return status(HttpStatus.BAD_REQUEST).body ("Не заполнены параметры запроса: " + validateRequest.getErrormessage());
        request =validateRequest.getRequest();
        return productInstanceService.processProductInstance(request);

    }
}
