package org.example.repository;

import org.example.entity.AgreementEntity;
import org.example.entity.TppProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositoryAgreement extends JpaRepository<AgreementEntity,Long> {
    Optional<AgreementEntity> findByNumber (@Param("number") String number);

}
