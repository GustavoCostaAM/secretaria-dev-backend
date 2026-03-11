package com.secretaria.secretaria.controller;

import com.secretaria.secretaria.dto.mail.SendMailDTO;
import com.secretaria.secretaria.service.mail.SendMailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/redefine")
@EnableAsync
public class MailController {
    private final SendMailService sendMailService;

    public MailController(SendMailService sendMailService) {
        this.sendMailService = sendMailService;
    }

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
        return null;
    }
}
