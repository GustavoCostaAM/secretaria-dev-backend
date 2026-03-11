package com.secretaria.secretaria.repository;

import com.secretaria.secretaria.model.Recovery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryRepository extends JpaRepository<Recovery, Integer> {

}
