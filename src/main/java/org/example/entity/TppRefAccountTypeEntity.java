package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tpp_ref_account_type", schema = "public", catalog = "postgres")
@Data
public class TppRefAccountTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private int internalId;
    @Basic
    @Column(name = "value")
    private String value;

    public int getInternalId() {
        return internalId;
    }

    public void setInternalId(int internalId) {
        this.internalId = internalId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefAccountTypeEntity that = (TppRefAccountTypeEntity) o;
        return internalId == that.internalId && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalId, value);
    }
}
