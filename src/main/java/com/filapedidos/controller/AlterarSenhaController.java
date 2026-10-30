
package com.filapedidos.controller;

import com.filapedidos.model.Usuario;
import com.filapedidos.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlterarSenhaController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AlterarSenhaController(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/usuario/alterar-senha")
    public String exibirFormulario() {
        return "usuario/alterar-senha";
    }

    @PostMapping("/usuario/alterar-senha")
    public String alterarSenha(
            @RequestParam String senhaAtual,
            @RequestParam String novaSenha,
            @RequestParam String confirmarNovaSenha,
            Authentication authentication,
            HttpSession session,
            Model model) {

        Usuario usuario = usuarioRepository
                .findByUsername(authentication.getName())
                .orElseThrow(() ->
                    new IllegalStateException(
                        "Usuário autenticado não encontrado."));

        if (!passwordEncoder.matches(
                senhaAtual, usuario.getPassword())) {
            model.addAttribute(
                "erro", "A senha atual está incorreta.");
            return "usuario/alterar-senha";
        }

        if (novaSenha.length() < 6) {
            model.addAttribute(
                "erro", "A nova senha deve ter pelo menos 6 caracteres.");
            return "usuario/alterar-senha";
        }

        if (!novaSenha.equals(confirmarNovaSenha)) {
            model.addAttribute(
                "erro", "A confirmação não corresponde à nova senha.");
            return "usuario/alterar-senha";
        }

        if (passwordEncoder.matches(
                novaSenha, usuario.getPassword())) {
            model.addAttribute(
                "erro", "A nova senha deve ser diferente da atual.");
            return "usuario/alterar-senha";
        }

        usuario.setPassword(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);

        // Encerra a sessão atual.
        session.invalidate();

        return "redirect:/login?senhaAlterada";
    }
}
