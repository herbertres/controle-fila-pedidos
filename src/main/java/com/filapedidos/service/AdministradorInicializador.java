package com.filapedidos.service;

import com.filapedidos.model.Usuario;
import com.filapedidos.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdministradorInicializador implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;
    private final String nome;

    public AdministradorInicializador(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.security.initial-admin.username}") String username,
            @Value("${app.security.initial-admin.password}") String password,
            @Value("${app.security.initial-admin.nome:Administrador}") String nome) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
        this.nome = nome;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (usuarioRepository.existsByUsername(username)) {
            return;
        }

        Usuario administrador = new Usuario();
        administrador.setUsername(username.trim());
        administrador.setPassword(passwordEncoder.encode(password));
        administrador.setNome(nome);
        administrador.setRole("ADMIN");
        administrador.setAtivo(true);

        usuarioRepository.save(administrador);
    }
}
