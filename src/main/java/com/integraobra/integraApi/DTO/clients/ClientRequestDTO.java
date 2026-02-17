package com.integraobra.integraApi.DTO.clients;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClientRequestDTO {
    @NotEmpty( message = "El nombre del cliente no puede estar vacío" )
    private String name;
    private String email; //Este campo es opcional, por lo que no se le agrega la anotación @NotEmpty
    @NotEmpty( message = "El número de teléfono del cliente no puede estar vacío" )
    private String phone;
    @Pattern(regexp = "MOROSO|CUMPLIDOR",
            message = "Los valores permitidos para la reputación del cliente son MOROSO o CUMPLIDOR.")
    @NotEmpty( message = "La reputación del cliente no puede estar vacía" )
    private String reputationClient;
    private String frontPhotoIne; //Este campo es opcional, por lo que no se le agrega la anotación @NotEmpty
    private String backPhotoIne; //Este campo es opcional, por lo que no se le agrega la anotación @NotEmpty
}
