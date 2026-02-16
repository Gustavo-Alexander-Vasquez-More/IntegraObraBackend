package com.integraobra.integraApi.repository;

import com.integraobra.integraApi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Buscar si existe un usuario por su nombre de usuario
    boolean existsByUsername(String username);
    //Buscar un usuario por su nombre de usuario
    Optional<User> findByUsername(String username);
    //obtener usuarios paginados relacionados por un termino de busqueda(por username)
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);

}
