package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Recovery;
import com.secretaria.secretaria.model.RecoveryStatuses;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Integer> {

    Recovery getRecoveryByCode(String code);

    List<Recovery> findAllByStatusAndUser_Email(RecoveryStatuses status, String userEmail);
}
