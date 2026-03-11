package com.secretaria.secretaria.dto.mail;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class resetPasswordDTO {
    private String password;
    private String code;
}
