package com.trafilea.coffeeshop.dto;

import java.util.Set;

public record CreateUserDTO (
    String email,
    String username,
    String password,
    Set<String> roles
) {}
