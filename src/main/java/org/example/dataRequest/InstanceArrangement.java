package org.example.dataRequest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InstanceArrangement {
    private String generalAgreementId;
    private String supplementaryAgreementId;
    private String arrangementType;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer shedulerJobId;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String number;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date openingDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date closingDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date cancelDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer validityDuration;
    private String cancellationReason;
    private String status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date interestCalculationDate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float interestRate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float coefficient;
    private String coefficientAction;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float minimumInterestRate;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Float minimumInterestRateCoefficient;
    private String minimumInterestRateCoefficientAction;
    private Double maximalnterestRate;
    private Double maximalnterestRateCoefficient;
    private String maximalnterestRateCoefficientAction;


    @Override
    public String toString() {
        return "InstanceArrangement{" +
                "generalAgreementId='" + generalAgreementId + '\'' +
                ", supplementaryAgreementId='" + supplementaryAgreementId + '\'' +
                ", shedulerJobId ='" +shedulerJobId+'\''+
                ", number ='" +number+'\''+
                ", openingDate ='" +openingDate+'\''+
                ", closingDate='" +closingDate+'\''+
                ", cancelDate ='" +cancelDate+'\''+
                ", validityDuration ='" +validityDuration +
                //  остальные поля
                '}';
    }


}