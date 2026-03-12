package com.secretaria.secretaria.service.mail;

import com.secretaria.secretaria.model.Recovery;
import com.secretaria.secretaria.model.RecoveryStatuses;
import com.secretaria.secretaria.repository.RecoveryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ValidateMailService {
    private final RecoveryRepository recoveryRepository;

    public boolean ValidateCode(String code){
        //check if the recovery request exists in the database
        Recovery recoverRequest;
        try {
            recoverRequest = recoveryRepository.getRecoveryByCode(code);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        if(recoverRequest == null){
            //this is the only case that the code is invalid
            return false;
        }

        //chef if the recovery request is expired
        if (recoverRequest.getExpiration().isBefore(LocalDateTime.now())){
            //the request is expired
            recoverRequest.setStatus(RecoveryStatuses.EXPIRED);
            try {
                recoveryRepository.save(recoverRequest);
            } catch (DataAccessException e) {
                e.printStackTrace();
                return false;
            }
            return false;
        }

        //check if the request is already used
        if(!recoverRequest.getStatus().equals(RecoveryStatuses.PROGRESS)){
            //the request is already used or was never been in progress
            return false;
        }

        //since the code is valid, we need to change the status
        //and we need to return a success status to the user
        recoverRequest.setStatus(RecoveryStatuses.COMPLETED);

        try {
            recoveryRepository.save(recoverRequest);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
