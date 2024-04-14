package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tpp_product", schema = "public", catalog = "postgres")
@Data
public class TppProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "product_code_id")
    private Long productCodeId;
    @Basic
    @Column(name = "client_id")
    private Long clientId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "number")
    private String number;
    @Basic
    @Column(name = "priority")
    private Long priority;
    @Basic
    @Column(name = "date_of_conclusion")
    private Timestamp dateOfConclusion;
    @Basic
    @Column(name = "start_date_time")
    private Timestamp startDateTime;
    @Basic
    @Column(name = "end_date_time")
    private Timestamp endDateTime;
    @Basic
    @Column(name = "days")
    private Long days;
    @Basic
    @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;
    @Basic
    @Column(name = "nso")
    private BigDecimal nso;
    @Basic
    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;
    @Basic
    @Column(name = "requisite_type")
    private String requisiteType;
    @Basic
    @Column(name = "interest_rate_type")
    private String interestRateType;
    @Basic
    @Column(name = "tax_rate")
    private BigDecimal taxRate;
    @Basic
    @Column(name = "reasone_close")
    private String reasoneClose;
    @Basic
    @Column(name = "state")
    private String state;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductEntity that = (TppProductEntity) o;
        return id == that.id && Objects.equals(productCodeId, that.productCodeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productCodeId);
    }
}
