package com.integraobra.integraApi.DTO.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserLoginDetailDTO {
    private Long id;
    private String username;
    private String password;
}
