package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account", schema = "public", catalog = "postgres")
@Data
public class AccountEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "account_pool_id")
    private Integer accountPoolId;
    @Basic
    @Column(name = "account_number")
    private String accountNumber;
    @Basic
    @Column(name = "bussy")
    private Boolean bussy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return getId() == that.getId() && Objects.equals(getAccountPoolId(), that.getAccountPoolId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountPoolId());
    }
}
