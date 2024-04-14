package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tpp_product_register", schema = "public", catalog = "postgres")
@Data
public class TppProductRegisterEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "product_id")
    private Long productId;
    @Basic
    @Column(name = "type")
    private String type;
    @Basic
    @Column(name = "account")
    private Long account;
    @Basic
    @Column(name = "currency_code")
    private String currencyCode;
    @Basic
    @Column(name = "state")
    private String state;
    @Basic
    @Column(name = "account_number")
    private String accountNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppProductRegisterEntity that = (TppProductRegisterEntity) o;
        return id == that.id && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId);
    }
}
