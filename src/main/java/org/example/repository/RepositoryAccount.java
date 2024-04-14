package org.example.repository;

import org.example.entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.Optional;

@Repository
public interface RepositoryAccount extends JpaRepository <AccountEntity, Long> {

    @Query (value="SELECT  a FROM  AccountEntity a " +
            " INNER JOIN AccountPoolEntity  b on a.accountPoolId=b.id " +
            " WHERE b.branchCode=:branchCode " +
            " and  b.currencyCode = :currencyCode" +
            " and b.mdmCode =:mdmCode" +
            " and b.priorityCode =:priorityCode " +
            " and b.registryTypeCode = :registryTypeCode  " +
            " and   a.bussy =false" )  //jpa нельзя limit //native тоже не хочется
    Page<AccountEntity> findAccountByPoolAttributes (@Param("branchCode") String branchCode,
                                                     @Param("currencyCode") String currencyCode,
                                                     @Param("mdmCode") String mdmCode,
                                                     @Param("priorityCode") String priorityCode,
                                                     @Param("registryTypeCode") String registryTypeCode,
                                                     Pageable pageable);
}
