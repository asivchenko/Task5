package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account_pool", schema = "public", catalog = "postgres")
@Data
public class AccountPoolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "branch_code")
    private String branchCode;
    @Basic
    @Column(name = "currency_code")
    private String currencyCode;
    @Basic
    @Column(name = "mdm_code")
    private String mdmCode;
    @Basic
    @Column(name = "priority_code")
    private String priorityCode;
    @Basic
    @Column(name = "registry_type_code")
    private String registryTypeCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountPoolEntity that = (AccountPoolEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
