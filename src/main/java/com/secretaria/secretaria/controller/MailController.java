package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.mail.SendMailDTO;
import com.secretaria.secretaria.dto.mail.resetPasswordDTO;
import com.secretaria.secretaria.service.mail.ResetPasswordService;
import com.secretaria.secretaria.service.mail.SendMailService;
import com.secretaria.secretaria.service.mail.ValidateMailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/redefine")
@EnableAsync
@AllArgsConstructor
public class MailController {
    private final SendMailService sendMailService;
    private final ValidateMailService validateMailService;
    private final ResetPasswordService resetPasswordService;

    @PostMapping("/sendRecoveryMail")
    public ResponseEntity<?> sendRecoveryMail(@RequestBody SendMailDTO sendMailDTO, HttpServletRequest request) {
        //send the mail to the user
        //we dont need to validate the email here


        //o service é assincrono para não atrasar o front
        sendMailService.SendRecoverMail(sendMailDTO.getRecipientMail(), request);

        //retorno padrão
        return ResponseEntity.ok(null);
    }

    @GetMapping("/recover/{code}")
    public ResponseEntity<?> RecoverPassword(@PathVariable("code") String code){
        //validate the code and return a status
        boolean isValid = validateMailService.ValidateCode(code);

        if (!isValid){
            //this case means that the code was never saved in database
            return ResponseEntity.badRequest().body("Invalid code");
        }

        //this case means that the code was valid, so we can redirect the user to the password reset page
        return ResponseEntity.ok().build();
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody resetPasswordDTO resetPasswordDTO){
        //this endpoint will be used to change the user password
        String password = resetPasswordDTO.getPassword();
        String userCode = resetPasswordDTO.getCode();
        boolean status = resetPasswordService.ResetPassword(password, userCode);

        if (!status){
            //something unexpected happened, so we return a bad request
            return ResponseEntity.badRequest().body("Invalid code or no recovery request completed for this user");
        }

        //this case means that the password was successfully changed
        return ResponseEntity.ok().build();
    }
}
