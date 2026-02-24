package com.integraobra.integraApi.controller;

import com.integraobra.integraApi.DTO.users.*;
import com.integraobra.integraApi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/user")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //no tiene proteccion porque es el primer superadmin que se crea
    @PostMapping("/first-superadmin")
    public ResponseEntity<UserMessageDTO> createFirstSuperAdmin(@RequestBody @Valid FirstUserRequestDTO firstUserRequestDTO) {
        String response = userService.createFirstSuperAdmin(firstUserRequestDTO);
        return ResponseEntity.ok(new UserMessageDTO(200, response, java.time.LocalDateTime.now()));
    }

    //Crear un nuevo usuario (solo SUPERADMIN)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserMessageDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        String response = userService.createUser(userRequestDTO);
        return ResponseEntity.ok(new UserMessageDTO(200, response, java.time.LocalDateTime.now()));
    }

    //Eliminar un usuario por su id (solo SUPERADMIN)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserMessageDTO> deleteUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new UserMessageDTO(200, userService.deleteUserById(id), java.time.LocalDateTime.now()));
    }

    //Buscar usuarios paginados por un termino de busqueda en el username solo SUPERADMIN
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDetailDTO>> search(@RequestParam(name = "username", required = false) String username, @PageableDefault(size = 10, sort = "username") Pageable pageable) {

        return ResponseEntity.ok(userService.searchUsersPaged(username, pageable));
    }

    //Actualizar un usuario por su id (solo SUPERADMIN)
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserMessageDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        String response = userService.updateUserPatch(id, userUpdateRequest);
        return ResponseEntity.ok(new UserMessageDTO(200, response, java.time.LocalDateTime.now()));
    }

    //Verificar si existe al menos un usuario en la base de datos
    @GetMapping("/verify-existence")
    public ResponseEntity<Boolean> verifyUserExistence() {
        boolean exist = userService.verifyUserExist();
        return ResponseEntity.ok(exist);
    }

    @GetMapping("/me/{token}")
    public ResponseEntity<UserDetailDTO> getMyUserDetails(@PathVariable("token") String token) {
        UserDetailDTO userDetailDTO = userService.getMe(token);
        return ResponseEntity.ok(userDetailDTO);
    }
}
