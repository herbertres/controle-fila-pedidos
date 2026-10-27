package com.filapedidos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.filapedidos.model.Pedido;
import com.filapedidos.model.PlataformaPedido;
import com.filapedidos.model.StatusPedido;
import com.filapedidos.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public boolean existeCodigoHoje(String codigoPedido) {
        LocalDate hoje = LocalDate.now(ZoneId.of("America/Sao_Paulo"));

        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.plusDays(1).atStartOfDay();

        return pedidoRepository
                .existsByCodigoPedidoAndDataCriacaoGreaterThanEqualAndDataCriacaoLessThan(
                        codigoPedido,
                        inicio,
                        fim);
    }

    public @Nullable List<Pedido> listarTodos() {
        List<Pedido> listaPedidos = pedidoRepository
                .findAll(Sort.by(Sort.Direction.DESC, Pedido::getDataCriacao));
        return listaPedidos;
    }

    public @Nullable List<Pedido> listarEmPreparoPlataformas(int quantidade) {
        return pedidoRepository.findByStatusOrderByDataUltimaAlteracaoAsc(StatusPedido.EM_PREPARO, PageRequest.of(0, quantidade));
    }

    public @Nullable List<Pedido> listarProntosPlataformas(int quantidade) {
        return pedidoRepository.findByStatusOrderByDataUltimaAlteracaoAsc(StatusPedido.PRONTO, PageRequest.of(0, quantidade));
    }

    public void marcarComoPronto(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
        if (pedido.getStatus().equals(StatusPedido.EM_PREPARO)) {
            pedido.setStatus(StatusPedido.PRONTO);
            pedido.setDataUltimaAlteracao(LocalDateTime.now());
            pedidoRepository.save(pedido);
        }
    }

    public void marcarComoRetirado(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: " + id));
        if (pedido.getStatus().equals(StatusPedido.PRONTO)) {
            pedido.setStatus(StatusPedido.RETIRADO);
            pedido.setDataUltimaAlteracao(LocalDateTime.now());
            pedidoRepository.save(pedido);
        }
    }
}