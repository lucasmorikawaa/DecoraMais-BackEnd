package com.decoramais.decoramais_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    @Column(nullable = false)
    private String tipo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (this.tipo == null) {
            return List.of();
        }

        if (this.tipo.equalsIgnoreCase("PROFESSOR")) {

            return List.of(
                    new SimpleGrantedAuthority("ROLE_PROFESSOR"));
        }

        if (this.tipo.equalsIgnoreCase("ALUNO")) {

            return List.of(
                    new SimpleGrantedAuthority("ROLE_ALUNO"));
        }

        return List.of();
    }

    @Override
    public String getPassword() {
        return senha;
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
        return true;
    }
}