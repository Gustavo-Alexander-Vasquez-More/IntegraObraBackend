package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.auth.LoginRequest;
import com.integraobra.integraApi.DTO.jwt.TokenResponseDTO;
import com.integraobra.integraApi.DTO.users.UserLoginDetailDTO;
import com.integraobra.integraApi.Exceptions.ErrorCredentialsException;
import com.integraobra.integraApi.model.User;
import com.integraobra.integraApi.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    public final JwtTokenUtilService jwtTokenUtilService; // Inyección de dependencia del JwtTokenUtil
    public final UserService userService;
    public final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(JwtTokenUtilService jwtTokenUtilService, UserService userService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtTokenUtilService = jwtTokenUtilService;
        this.userService = userService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    //SERVICIOS DE LOGIN

    public TokenResponseDTO login(LoginRequest loginRequest){
        //Primero verificamos que las credenciales de usuario sean correctas
        //1. Verificar que el usuario existe en la base de datos
        UserLoginDetailDTO user=verifyCredentials(loginRequest);
        //2. Si las credenciales son correctas, generamos el token JWT
        String token = jwtTokenUtilService.generateToken(user);
        return new TokenResponseDTO(token);
    }

    //Servicio para buscar un usuario por su username y devolver un DTO con su id, username y contraseña(hasheada)
    //Esto solo se usa para el login, no debe usarse para otros fines ya que devuelve la contraseña
    public UserLoginDetailDTO getUserLoginDetailByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return new UserLoginDetailDTO(user.getId(), user.getUsername(), user.getPassword());
        }
        return null;
    }

    //Servicio para verificar si la contraseña de un usuario es correcta usando Bcrypt
    public boolean isPasswordCorrect(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }

    //Servicio para verificar las credenciales de un usuario y devuelve el usuario
    public UserLoginDetailDTO verifyCredentials(LoginRequest loginRequest) {
        //usamos el metodo de getUserLoginDetailByUsername para verificar si el usuario existe
        UserLoginDetailDTO userLoginDetailDTO = getUserLoginDetailByUsername(loginRequest.getUsername());
        if (userLoginDetailDTO == null) {
            throw new ErrorCredentialsException("Invalid username or password.");
        }
        //usamos el metodo isPasswordCorrect para verificar la contraseña
        boolean isPasswordCorrect= isPasswordCorrect(loginRequest.getPassword(), userLoginDetailDTO.getPassword());
        if(!isPasswordCorrect){
            throw new ErrorCredentialsException("Invalid username or password.");
        }
        return userLoginDetailDTO;
    }
}
