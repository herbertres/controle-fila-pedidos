package com.filapedidos.controller;

import java.util.List;

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
        novoPedido.setStatus(StatusPedido.EM_PREPARO);
        novoPedido.setPlataforma(PlataformaPedido.NINETY_NINE);
        model.addAttribute("pedido", novoPedido);
        model.addAttribute("plataformas", PlataformaPedido.values());
        model.addAttribute("statusDisp", StatusPedido.values());
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

    @GetMapping("/pedido/lista")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        return "pedido/lista";
    }

    @GetMapping("/pedido/painel")
    public String exibirPainel(Model model) {
        List<Pedido> pedidosEmPreparo = pedidoService.listarEmPreparoPlataformas(12);
        List<Pedido> pedidosProntos = pedidoService.listarProntosPlataformas(12);
        List<Pedido> ultimosInternosProntos = pedidoService.listarInternosProntos(1);

        model.addAttribute("pedidosEmPreparo", pedidosEmPreparo);
        model.addAttribute("pedidosProntos", pedidosProntos);
        model.addAttribute("ultimosInternosProntos", ultimosInternosProntos);
        return "pedido/painel";
    }
}