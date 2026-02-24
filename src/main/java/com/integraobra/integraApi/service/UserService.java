package com.integraobra.integraApi.service;

import com.integraobra.integraApi.DTO.users.*;
import com.integraobra.integraApi.Exceptions.BadRequestException;
import com.integraobra.integraApi.Exceptions.UserExistException;
import com.integraobra.integraApi.model.User;
import com.integraobra.integraApi.repository.UserRepository;
import com.integraobra.integraApi.utils.RoleUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public final UserRepository userRepository;
    public final JwtTokenUtilService jwtTokenUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtTokenUtilService tokenService) {
        this.jwtTokenUtil = tokenService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    //SERVICIOS PARA LA CREACION DE USUARIOS

    //Servicio para verificar si un usuario existe por su nombre de usuario
    //Este servicio se usa para verificar si el nombre de usuario ya existe antes de crear un nuevo usuario.
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    //Servicio para hashear contraseña del usuario a crear
    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    //Servicio para crear un usuario con contraseña hasheada
    public String createUser(UserRequestDTO userRequestDTO) {
        //Primero verificamos que no exista el usuario
        boolean userExists= userExists(userRequestDTO.getUsername());
        if(userExists){
            throw new UserExistException("El usuario '"+userRequestDTO.getUsername()+" ya existe.");
        }
        String hashedPassword = hashPassword(userRequestDTO.getPassword()); // Hashear la contraseña antes de guardarla
        //Creamos el usuario y lo guardamos en la base de datos
        User user = new User(
                userRequestDTO.getUsername(),
                hashedPassword,
                RoleUser.valueOf(userRequestDTO.getRole().toUpperCase()))
                ;
        userRepository.save(user);

        //Devolvemos el DTO de respuesta sin la contraseña
        return "El usuario "+user.getUsername()+" ha sido creado exitosamente";
    }

    //Servicio para verificar si existe al menos un usuario
    //Esto se usa para crear el primer superadmin
    public Boolean verifyUserExist(){
        return userRepository.count() > 0;
    }

    //Servicio para crear el primer superadmin si no existe ninguno
    public String createFirstSuperAdmin(FirstUserRequestDTO firstUserRequestDTO) {
        //Verificamos si ya existen usuarios, sino existe ninguno, creamos el primer superadmin
        long userCount = userRepository.count();
        if (userCount > 0) {
            throw new UserExistException("El primer superadmin ya ha sido creado.");
        }
        String hashedPassword = hashPassword(firstUserRequestDTO.getPassword()); // Hashear la contraseña antes de guardarla
        //Creamos el usuario y lo guardamos en la base de datos
        User user = new User(
                firstUserRequestDTO.getUsername(),
                hashedPassword
        , RoleUser.ADMIN);
        userRepository.save(user);
        //Devolvemos el DTO de respuesta sin la contraseña
        return "Primer admin: '"+user.getUsername()+" ha sido creado exitosamente";
    }

    //OTROS SERVICIOS REST DELETE, UPDATE, GET

    //Servicio para eliminar un usuario por su id
    @Transactional
    public String deleteUserById(Long id) {
        //si el usuario no existe, lanzamos una excepcion
        if(!userRepository.existsById(id)){
            throw new UserExistException("EL usuario con id: "+id+" que intentas eliminar no existe.");
        }
        // Luego eliminar el usuario
        userRepository.deleteById(id);
        return "El usuario con id: "+id+" ha sido eliminado exitosamente.";
    }

    //Metodo para obtener usuarios paginados relacionados por un termino de busqueda(por username)
    public Page<UserDetailDTO> getUsersByUsernamePaged(String username, Pageable pageable) {
        return userRepository
                .findByUsernameContainingIgnoreCase(username, pageable)
                .map(user -> new UserDetailDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString(),
                        user.getCreatedAt()
                ));
    }

    //Metodo para obtener todos los usuarios paginados
    public Page<UserDetailDTO> getAllUsersPaged(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .map(user -> new UserDetailDTO(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().toString(),
                        user.getCreatedAt()
                ));
    }

    //Metodo para el filtro de busqueda de usuarios, si tiene termino de busqueda, busca por username, si no, devuelve todos los usuarios paginados
    public Page<UserDetailDTO> searchUsersPaged(String username, Pageable pageable) {
        if (username != null && !username.isEmpty()) {
            return getUsersByUsernamePaged(username, pageable);
        } else {
            return getAllUsersPaged(pageable);
        }
    }

    //Servicio para actualizar un usuario con PATCH ya que queremos que actualizar solo ciertos campos o todos de acorde a lo que envie el cliente
    @Transactional
    public String updateUserPatch(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserExistException("El usuario con id: " + id + " no existe."));
        // 1. Username (Validamos que no sea vacío y que no esté duplicado)
        if (userUpdateRequest.getUsername() != null && !userUpdateRequest.getUsername().isBlank()) {
            String newUsername = userUpdateRequest.getUsername().trim();
            if (newUsername.length() < 3) {
                throw new BadRequestException("El nombre de usuario debe tener al menos 3 caracteres.");
            }
            if (!user.getUsername().equals(newUsername) && userRepository.existsByUsername(newUsername)) {
                throw new UserExistException("El nombre de usuario '" + newUsername + "' ya está en uso por otro usuario.");
            }
            user.setUsername(newUsername);
        }
        // 2. Password (Solo si trae contenido)
        if (userUpdateRequest.getPassword() != null && !userUpdateRequest.getPassword().isBlank()) {
            user.setPassword(hashPassword(userUpdateRequest.getPassword()));
        }
        // 3. Role (Con tu manejo de Enum actual)
        if (userUpdateRequest.getRole() != null && !userUpdateRequest.getRole().isBlank()) {
            try {
                String roleStr = userUpdateRequest.getRole().trim().toUpperCase();
                user.setRole(RoleUser.valueOf(roleStr));
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Rol inválido. Los roles permitidos son: SUPERADMIN y EMPLOYEE.");
            }
        }
        try {
            userRepository.save(user);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            // Si la causa es duplicado de username, lanzamos UserExistException para mapear 409
            throw new UserExistException("El nombre de usuario '" + user.getUsername() + "' ya está en uso por otro usuario.");
        }
        return "El usuario con  id: " + id + " ha sido actualizado exitosamente.";
    }

    //Servicio para obtener mi perfil de usuario a partir del token JWT, este servicio se usará para mostrar la información del usuario logueado en el frontend
    public UserDetailDTO getMe(String token) {
        // 1. Extraer el username para validar el token
        String username = jwtTokenUtil.extractUsername(token);

        // 2. Validar (si falla, lanzará excepción según tu implementación)
        jwtTokenUtil.validateToken(token, username);

        // 3. Extraer el ID del token (El JWT incluye el ID del usuario como claim)
        Long userId = jwtTokenUtil.extractId(token);

        // 4. Buscar directamente por ID usando el repositorio
        // Aprovechamos el Optional para manejar el error
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserExistException("El usuario con id: " + userId + " no existe."));

        // 5. Mapear al DTO
        return new UserDetailDTO(
                user.getId(),
                user.getUsername(),
                user.getRole().toString(),
                user.getCreatedAt()
        );
    }

}
