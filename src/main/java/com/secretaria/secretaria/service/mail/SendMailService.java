package com.secretaria.secretaria.service.mail;

import com.secretaria.secretaria.model.Recovery;
import com.secretaria.secretaria.model.User;
import com.secretaria.secretaria.repository.RecoveryRepository;
import com.secretaria.secretaria.repository.UserRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.autoconfigure.ServerProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class SendMailService {
    //load the mail and password from the properties file
    @Value("${spring.mail.sender}")
    private String hostMail;

    @Value("${spring.mail.sender.password}")
    private String hostPassword;

    //load the repositories
    private UserRepository userRepository;
    private RecoveryRepository recoveryRepository;

    //send a email to the user with a url to recover the password
    //the url should contain a code that will be used to identify the user and the recover request
    //this will be the easyler way to the user
    @Async
    public void SendRecoverMail(String recipientMail, HttpServletRequest request){
        //generate a random code and send it to the user
        String code = generateCode();

        String baseUrl = request.getScheme() + "://" +
                request.getServerName() + ":" +
                request.getServerPort();


        String redirectUrl = baseUrl + "/recover/" + code;

        //format a new message
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(hostMail, hostPassword);
            }
        });
        session.setDebug(true);

        Message message = new MimeMessage(session);
        try {
            message.setSubject("Recuperação de senha");
            message.setFrom(new InternetAddress());
            message.setRecipient(Message.RecipientType.CC, new InternetAddress(recipientMail));

            message.setContent(
                    generateContent(redirectUrl),
                    "text/html; charset=utf-8"
            );

            //send the message
            Transport.send(message);
        }catch (Exception e){
            //devemos tratar os erros de forma mais específica, mas por enquanto vamos deixar assim
            //TODO: tratar os erros de forma mais específica

            e.printStackTrace();
        }

        //new we need to load the user data
        User user = userRepository.getUserByEmail(recipientMail);

        //now we save the code with user data
        Recovery recovery = Recovery.builder()
                .code(code)
                .user(user)
                .build();

        recoveryRepository.save(recovery);
        //any errors that happens here is because a user's error
        //so we decided to not treat it, but implement a retry logic on front end
    }

    private String generateCode(){
        //generate a random code with 6 digits and return it
        return String.format("%6d", new Random().nextInt(999999)).replace(' ', '0');
    }

    private String generateContent(String redirectUrl){
        return "<style>body{background-color: #F0F1F3;display: flexbox;place-items: center;text-align: center;" +
                "font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, " +
                "Verdana, sans-serif;}div{background-color: #ffffff;padding: 15px;border-radius: 10px;box-shadow: 0px" +
                " 0px 10px rgba(0, 0, 0, 0.171);} button{background-color: #5D85B4;border: none;padding: 15px 75px;" +
                "border-radius: 7px;color: #ffffff;font-weight: bold;}</style><body><img src=\"https://i.postimg" +
                ".cc/CxR70s9X/logo.png\" alt=\"logo da opaco\"><div><h1>Redefinição de Senha</h1><p>Foi requisitada " +
                "uma redefinição de senha para este email.</p><p>Para redefinir sua senha, clique no botão abaixo" +
                ".</p><a href=\""+ redirectUrl +"\"><button>Redefinir Senha</button></a><p>Caso você não tenha " +
                "solicitado, por " +
                "favor, desconsidere esta mensagem </p></div></body>";
    }
}
