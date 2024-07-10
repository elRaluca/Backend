package com.example.backend.test;

import com.example.backend.entity.OurUsers;
import com.example.backend.service.JWTUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JWTUtilsTest {

    private JWTUtils jwtUtils;
    private UserDetails userDetails;

    @BeforeEach
    public void setUp() {
        jwtUtils = new JWTUtils();
        OurUsers user = new OurUsers();
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");
        user.setActive(true);
        userDetails = user;
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtils.generateToken(userDetails);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

}
