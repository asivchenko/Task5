package org.example.dataRequest;

import lombok.Data;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.constraints.NotNull;

@Data
public class ProductRegisterRequest {
    @NotNull
    private Integer instanceId;
    private String registryTypeCode;
    private String accountType;
    private String currencyCode;
    private String branchCode;
    private String priorityCode;
    private String mdmCode;
    private String clientCode;
    private String trainRegion;
    private String counter;
    private String salesCode;

}
