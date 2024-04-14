package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "agreement", schema = "public", catalog = "postgres")
@Data
public class AgreementEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "product_id")
    private Integer productId;
    @Basic
    @Column(name = "general_agreement_id")
    private String generalAgreementId;
    @Basic
    @Column(name = "supplementary_agreement_id")
    private String supplementaryAgreementId;
    @Basic
    @Column(name = "arrangement_type")
    private String arrangementType;
    @Basic
    @Column(name = "sheduler_job_id")
    private Long shedulerJobId;
    @Basic
    @Column(name = "number")
    private String number;
    @Basic
    @Column(name = "opening_date")
    private Timestamp openingDate;
    @Basic
    @Column(name = "closing_date")
    private Timestamp closingDate;
    @Basic
    @Column(name = "cancel_date")
    private Timestamp cancelDate;
    @Basic
    @Column(name = "validity_duration")
    private Long validityDuration;
    @Basic
    @Column(name = "cancellation_reason")
    private String cancellationReason;
    @Basic
    @Column(name = "status")
    private String status;
    @Basic
    @Column(name = "interest_calculation_date")
    private Timestamp interestCalculationDate;
    @Basic
    @Column(name = "interest_rate")
    private BigDecimal interestRate;
    @Basic
    @Column(name = "coefficient")
    private BigDecimal coefficient;
    @Basic
    @Column(name = "coefficient_action")
    private String coefficientAction;
    @Basic
    @Column(name = "minimum_interest_rate")
    private BigDecimal minimumInterestRate;
    @Basic
    @Column(name = "minimum_interest_rate_coefficient")
    private BigDecimal minimumInterestRateCoefficient;
    @Basic
    @Column(name = "minimum_interest_rate_coefficient_action")
    private String minimumInterestRateCoefficientAction;
    @Basic
    @Column(name = "maximal_interest_rate")
    private BigDecimal maximalInterestRate;
    @Basic
    @Column(name = "maximal_interest_rate_coefficient")
    private BigDecimal maximalInterestRateCoefficient;
    @Basic
    @Column(name = "maximal_interest_rate_coefficient_action")
    private String maximalInterestRateCoefficientAction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgreementEntity agreement = (AgreementEntity) o;
        return getId() == agreement.getId() && Objects.equals(getGeneralAgreementId(), agreement.getGeneralAgreementId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getGeneralAgreementId());
    }
}
