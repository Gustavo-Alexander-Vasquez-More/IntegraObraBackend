package com.integraobra.integraApi.DTO.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserDetailDTO {
    private Long id;
    private String username;
    private String role;
    private LocalDateTime createdAt;
}
