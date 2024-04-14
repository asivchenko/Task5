package org.example.repository;

import org.example.entity.AgreementEntity;
import org.example.entity.TppRefProductClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryProductClass  extends JpaRepository<TppRefProductClassEntity, Long>{
    @Query("Select p from TppRefProductClassEntity  p  where p.value = :value ")
    Optional<TppRefProductClassEntity> findByValue (@Param("value") String number);

}
