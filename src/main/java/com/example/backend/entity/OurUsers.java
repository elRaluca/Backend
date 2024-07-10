package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name="ourusers")
public class OurUsers implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    private String role;
    private boolean active;
    private String otp;
    private LocalDateTime otpGeneratedTime;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("ROLE_USER".equals(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        // Verificăm rolul utilizatorului din baza de date și adăugăm rolul corespunzător
        if ("ROLE_ADMIN".equals(role)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }




}
