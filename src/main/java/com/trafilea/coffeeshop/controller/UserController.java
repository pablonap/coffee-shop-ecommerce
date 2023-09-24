package com.trafilea.coffeeshop.controller;

import com.trafilea.coffeeshop.model.ERole;
import com.trafilea.coffeeshop.model.RoleEntity;
import com.trafilea.coffeeshop.model.UserEntity;
import com.trafilea.coffeeshop.repository.UserRepository;
import com.trafilea.coffeeshop.dto.CreateUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CreateUserDTO createUserDTO){
        Set<RoleEntity> roles = createUserDTO.roles().stream()
                .map(role -> new RoleEntity(ERole.valueOf(role)))
                .collect(Collectors.toSet());

        UserEntity userEntity = new UserEntity(
                createUserDTO.username(),
                createUserDTO.email(),
                passwordEncoder.encode(createUserDTO.password()),
                roles);

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
    }

}
