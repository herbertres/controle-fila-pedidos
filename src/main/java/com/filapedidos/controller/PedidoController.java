package com.filapedidos.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.filapedidos.model.Pedido;
import com.filapedidos.model.PlataformaPedido;
import com.filapedidos.model.StatusPedido;
import com.filapedidos.service.NotificacaoMotoboyService;
import com.filapedidos.service.PedidoService;

import jakarta.validation.Valid;

@Controller
public class PedidoController {
    
    private final PedidoService pedidoService;
    private final NotificacaoMotoboyService notificacaoMotoboyService;

    public PedidoController(PedidoService pedidoService, NotificacaoMotoboyService notificacaoMotoboyService) {
        this.pedidoService = pedidoService;
        this.notificacaoMotoboyService = notificacaoMotoboyService;
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
        model.addAttribute("titulo", "Novo pedido");
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

        if (!pedidoService.existeCodigoHoje(pedido.getCodigoPedido())) {
            pedidoService.salvar(pedido);

            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Pedido " + pedido.getCodigoPedido() + " cadastrado com sucesso!");

            return "redirect:/pedido/novo";
        } else {
            redirectAttributes.addFlashAttribute(
                    "mensagem",
                    "Pedido " + pedido.getCodigoPedido() + " já cadastrado.");
            return "redirect:/pedido/novo";
        }
    }

    @GetMapping("/pedido/lista")
    public String listarPedidos(Model model) {
        model.addAttribute("pedidos", pedidoService.listarTodos());
        model.addAttribute("notificacaoMotoboyAtiva", notificacaoMotoboyService.isNotificacaoAtiva());
        model.addAttribute("titulo", "Lista de pedidos");
        return "pedido/lista";
    }

    @GetMapping("/pedido/painel")
    public String exibirPainel(Model model) {
        List<Pedido> pedidosEmPreparo = pedidoService.listarEmPreparoPlataformas(18);
        List<Pedido> pedidosProntos = pedidoService.listarProntosPlataformas(18);

        model.addAttribute("pedidosEmPreparo", pedidosEmPreparo);
        model.addAttribute("pedidosProntos", pedidosProntos);
        model.addAttribute("notificacaoMotoboyAtiva", notificacaoMotoboyService.isNotificacaoAtiva());
        model.addAttribute("titulo", "Painel");
        return "pedido/painel";
    }

    @PostMapping("/pedido/{id}/pronto")
    public String marcarPronto(@PathVariable Long id) {
        pedidoService.marcarComoPronto(id);
        return "redirect:/pedido/lista";
    }

    @PostMapping("/pedido/{id}/retirado")
    public String marcarRetirado(@PathVariable Long id) {
        pedidoService.marcarComoRetirado(id);
        return "redirect:/pedido/lista";
    }

    @PostMapping("/pedido/notificacao-motoboy/iniciar")
    public String iniciarNotificacaoMotoboy() {
        notificacaoMotoboyService.iniciarNotificacao();
        return "redirect:/pedido/lista";
    }

    @PostMapping("/pedido/notificacao-motoboy/parar")
    public String pararNotificacaoMotoboy() {
        notificacaoMotoboyService.pararNotificacao();
        return "redirect:/pedido/lista";
    }

}