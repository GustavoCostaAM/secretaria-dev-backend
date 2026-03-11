package com.secretaria.secretaria.service.mail;

import com.secretaria.secretaria.model.Recovery;
import com.secretaria.secretaria.model.RecoveryStatuses;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.repository.RecoveryRepository;
import com.secretaria.secretaria.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@Service
public class ResetPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RecoveryRepository recoveryRepository;

    public boolean ResetPassword(String senha, String userCode){
        //validate if the user have a recovery request is completed
        //we treat as a list beacuse the user can ask more than one request
        //so we dont validate just one, since we are going to set other status on it
        List<Recovery> recoveries;
        try {
            recoveries = recoveryRepository.findAllByStatusAndUser_Email(RecoveryStatuses.COMPLETED,
                    userCode);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        //fetch the user by email
        User user;
        try {
            user = recoveries.getFirst().getUser();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return false;
        }

        if (user == null){
            return false;
        }

        //now we can update the user's password
        String encryptedPassword = passwordEncoder.encode(senha);
        user.setPassword(encryptedPassword);

        try {
            userRepository.save(user);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        //after we update the password, we need to change status of all the recovery requests of the user
        try {
            recoveries.forEach(recovery -> {
                recovery.setStatus(RecoveryStatuses.USED);
                recoveryRepository.save(recovery);
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
