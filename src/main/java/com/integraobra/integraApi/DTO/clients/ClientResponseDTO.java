package com.integraobra.integraApi.DTO.clients;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String reputationClient;
    private String frontPhotoIne;
    private String backPhotoIne;
}
