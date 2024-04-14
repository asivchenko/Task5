package org.example.repository;
import org.example.entity.TppProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryProduct extends JpaRepository<TppProductEntity,Long>{
    @Query("Select p from TppProductEntity  p  where p.number = :number ")
    Optional<TppProductEntity> findByNumber (@Param("number") String number);
    ////////////////////////////
    Optional<TppProductEntity> findById (@Param("id") Integer instanceId);


}
