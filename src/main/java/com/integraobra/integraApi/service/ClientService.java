package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.clients.ClientResponseDTO;
import com.integraobra.integraApi.Exceptions.ClientExistException;
import com.integraobra.integraApi.model.Client;
import com.integraobra.integraApi.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    public final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
     }

     //Servicio para validar si el correo electrónico del cliente ya existe en la base de datos
        public boolean emailExists(String email) {
            return clientRepository.existsByEmail(email);
        }

        //Servicio para validar si el número de teléfono del cliente ya existe en la base de datos
        public boolean phoneExists(String phone) {
            return clientRepository.existsByPhone(phone);
        }

        //Servicio para validar si el nombre del cliente ya existe en la base de datos
        public boolean nameExists(String name) {
            return clientRepository.existsByName(name);
        }

        //Servicio para crear un nuevo cliente en la base de datos
        public ClientResponseDTO createClient(ClientResponseDTO clientResponseDTO) {
            //verificar si el correo electrónico del cliente ya existe en la base de datos
            if (emailExists(clientResponseDTO.getEmail())) {
                throw new ClientExistException("El correo electrónico del cliente ya existe en la base de datos");
            }
            //verificar si el número de teléfono del cliente ya existe en la base de datos
            if (phoneExists(clientResponseDTO.getPhone())) {
                throw new ClientExistException("El número de teléfono del cliente ya existe en la base de datos");
            }
            //verificar si el nombre del cliente ya existe en la base de datos
            if (nameExists(clientResponseDTO.getName())) {
                throw new ClientExistException("El nombre del cliente ya existe en la base de datos");
            }
            //Si el correo electrónico, el número de teléfono y el nombre del cliente no existen en la base de datos, se crea un nuevo cliente

            Client client = new Client(
                    clientResponseDTO.getName(),
                    clientResponseDTO.getEmail(),
                    clientResponseDTO.getPhone(),
                    clientResponseDTO.getReputationClient() != null ? com.integraobra.integraApi.utils.ReputationClient.valueOf(clientResponseDTO.getReputationClient()) : null,
                    clientResponseDTO.getFrontPhotoIne(),
                    clientResponseDTO.getBackPhotoIne()
            );
            clientRepository.save(client);
            return new ClientResponseDTO(
                    client.getId(),
                    client.getName(),
                    client.getEmail(),
                    client.getPhone(),
                    client.getReputation() != null ? client.getReputation().name() : null,
                    client.getFrontPhotoIne(),
                    client.getBackPhotoIne()
            );
        }
}
