package org.example.dataRequest;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Collections;
import java.util.List;
@Data
public class ResponseData {
    @SerializedName("productId")
    private Integer productId;

    @SerializedName("registerId")
    private List<Integer> registerId;

    @SerializedName("supplementaryAgreementId")
    private List<Integer> agreementId;

    public ResponseData(Integer instanceId, List<Integer> registerId, List<Integer> agreementId) {
        this.productId = instanceId;
        this.registerId = (registerId != null) ? registerId : Collections.emptyList();
        this.agreementId = (agreementId != null) ? agreementId : Collections.emptyList();
    }
}
