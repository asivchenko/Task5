package org.example.dataRequest;

import lombok.Data;

@Data
public class ValidatedProductInstanceRequest {
    private ProductInstanceRequest request;
    private String errormessage;

    public ValidatedProductInstanceRequest (ProductInstanceRequest request)
    {
        this.request =request;
    }
}
