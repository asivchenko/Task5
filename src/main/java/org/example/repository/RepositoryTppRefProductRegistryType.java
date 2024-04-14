package org.example.repository;

import org.example.entity.TppRefProductRegisterTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Repository
public interface RepositoryTppRefProductRegistryType
        extends  JpaRepository <TppRefProductRegisterTypeEntity,Long>
{
   @Query("select u  from TppRefProductRegisterTypeEntity u  where u.value=:value")
   Optional<TppRefProductRegisterTypeEntity> findByvalue (@Param("value")String value);

    @Query("select u  from TppRefProductRegisterTypeEntity u " +
            " inner join TppRefProductClassEntity  c  on c.value=u.productClassCode " +
            " where    c.value=:value   and u.accountType=:accountType ")
    List<TppRefProductRegisterTypeEntity> findByValueAndAccountType(@Param("value")String value,
                                                                    @Param("accountType") String accountType);
}
//select u  from Tpp_Ref_Product_Register_Type u
//inner join Tpp_Ref_Product_Class  c  on c.value=u.product_class_code
//where   u.account_type ='Клиентский' and c.value='hhhh'