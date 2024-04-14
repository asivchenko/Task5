package org.example.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tpp_ref_product_class", schema = "public", catalog = "postgres")
@Data
public class TppRefProductClassEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "internal_id")
    private int internalId;
    @Basic
    @Column(name = "value")
    private String value;
    @Basic
    @Column(name = "gbi_code")
    private String gbiCode;
    @Basic
    @Column(name = "gbi_name")
    private String gbiName;
    @Basic
    @Column(name = "product_row_code")
    private String productRowCode;
    @Basic
    @Column(name = "product_row_name")
    private String productRowName;
    @Basic
    @Column(name = "subclass_code")
    private String subclassCode;
    @Basic
    @Column(name = "subclass_name")
    private String subclassName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TppRefProductClassEntity that = (TppRefProductClassEntity) o;
        return getInternalId() == that.getInternalId() && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInternalId(), getValue());
    }
}
