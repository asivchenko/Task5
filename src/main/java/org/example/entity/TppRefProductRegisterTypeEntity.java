package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_register_type", schema = "public", catalog = "postgres")
@Data
public class TppRefProductRegisterTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private int internalId;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "register_type_name")
    private String registerTypeName;
    @Basic
    @Column(name = "product_class_code")
    private String productClassCode;
    @Basic
    @Column(name = "register_type_start_date")
    private Timestamp registerTypeStartDate;
    @Basic
    @Column(name = "register_type_end_date")
    private Timestamp registerTypeEndDate;
    @Basic
    @Column(name = "account_type")
    private String accountType;


    public String toString()
    {
        return "TppRefProductRegisterType{" +
                "value=" + value +
                ", registerTypeName'" + registerTypeName + '\'' +
                ", productClassCode='" + productClassCode + '\'' +
                ", registerTypeStartDate='" + registerTypeStartDate + '\'' +
                ", accountType='" + accountType + '\'' + '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefProductRegisterTypeEntity that = (TppRefProductRegisterTypeEntity) o;
        return getInternalId() == that.getInternalId() && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInternalId(), getValue());
    }
}
