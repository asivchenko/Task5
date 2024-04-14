package org.example.repository;

import org.example.entity.TppProductEntity;
import org.example.entity.TppProductRegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface RepositoryProductRegistr  extends JpaRepository<TppProductRegisterEntity,Long>{
        //так тоже можно
        //int countByProductIdAndType (long product_id, String type);
        //метод jpa  в запрос Sql для подсчета записей по ProductId и Type
        @Query("Select count(p) from TppProductRegisterEntity  p " +
                " where p.productId = :productId " +
                "   and  p.type = :type")
        int countByProductIdAndType (@Param("productId") long productId,
                                     @Param("type") String type);
}
