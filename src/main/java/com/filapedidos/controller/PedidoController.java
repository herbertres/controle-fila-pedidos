package com.filapedidos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filapedidos.model.Pedido;
import com.filapedidos.model.PlataformaPedido;
import com.filapedidos.model.StatusPedido;
import com.filapedidos.service.PedidoService;

import jakarta.validation.Valid;

@Controller
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Exibe o formulário de cadastro
    @GetMapping("/pedido/novo")
    public String novoPedido(Model model) {
        Pedido novoPedido = new Pedido();
        novoPedido.setStatus(StatusPedido.RECEBIDO);
        model.addAttribute("pedido", novoPedido);
        model.addAttribute("Plataformas", PlataformaPedido.values());
        return "pedido/formulario";
    }

    // Recebe e salva o pedido
    @PostMapping("/pedido/salvar")
    public String salvarPedido(
            @Valid Pedido pedido,
            BindingResult resultado,
            RedirectAttributes redirectAttributes) {

        if (resultado.hasErrors()) {
            return "pedido/formulario";
        }

        pedidoService.salvar(pedido);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Pedido cadastrado com sucesso!");

        return "redirect:/pedido/novo";
    }
}