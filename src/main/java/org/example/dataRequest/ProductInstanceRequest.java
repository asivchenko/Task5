package org.example.dataRequest;

import java.util.Date;
import java.util.List;
//import org.example.dataRequest.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

@Data
public class ProductInstanceRequest {
    @JsonProperty("additionalPropertyVip")
    private AdditionalPropertyVip additionalPropertyVip;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer instanceId;
    @NotNull
    private String productType;
    @NotNull
    private String productCode;
    @NotNull
    private String registerType;
    @NotNull
    private String mdmCode;
    @NotNull
    private String contractNumber;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date contractDate;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer priority;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float interestRatePenalty;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float minimalBalance;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float thresholdAmount;
    private String accountingDetails;
    private String rateType;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float taxPercentageRate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float technicalOverdraftLimitAmount;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer contractId;
    @NotNull
    private String branchCode;
    @NotNull
    private String isoCurrencyCode;
    @NotNull
    private String urgencyCode;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer referenceCode;
    @SerializedName(value = "additionalPropertiesVip", alternate = {"", "null"})
    private AdditionalPropertyVip additionalPropertiesVip;
    @SerializedName(value = "instanceArrangement", alternate = {"", "null"})
    private List<InstanceArrangement> instanceArrangement;
    @Override
    public String toString() {
        return "RequestBody{" +
                "instanceId=" + instanceId +
                ", productType='" + productType + '\'' +
                ", productCode='" + productCode + '\'' +
                ", registerType='" + registerType + '\'' +
                ", mdmCode='" + mdmCode + '\'' +
                ", contractNumber='" + contractNumber + '\'' +
                ", contractDate='" + contractDate + '\'' +
                ", priority=" + priority +
                ", interestRatePenalty=" + interestRatePenalty +
                ", minimalBalance=" + minimalBalance +
                ", thresholdAmount=" + thresholdAmount +
                ", accountingDetails='" + accountingDetails + '\'' +
                ", rateType='" + rateType + '\'' +
                ", taxPercentageRate=" + taxPercentageRate +
                ", technicalOverdraftLimitAmount=" + technicalOverdraftLimitAmount +
                ", contractId=" + contractId +
                ", branchCode='" + branchCode + '\'' +
                ", isoCurrencyCode='" + isoCurrencyCode + '\'' +
                ", urgencyCode='" + urgencyCode + '\'' +
                ", referenceCode=" + referenceCode +
                ", additionalProperties=" + additionalPropertiesVip +
                ", instanceArrangement=" + instanceArrangement +
                '}';
    }
}
