package com.filapedidos.service;

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

    public @Nullable List<Pedido> listarTodos() {
        List<Pedido> listaPedidos = pedidoRepository.findAll(Sort.by(Sort.Direction.ASC, Pedido::getDataCriacao));
        return listaPedidos;
    }

    public @Nullable List<Pedido> listarEmPreparoPlataformas(int quantidade) {
        return pedidoRepository.findByStatusAndPlataformaNotOrderByDataUltimaAlteracaoAsc(StatusPedido.EM_PREPARO, PlataformaPedido.INTERNO, PageRequest.of(0,quantidade));
    }

    public @Nullable List<Pedido> listarProntosPlataformas(int quantidade) {
        return pedidoRepository.findByStatusAndPlataformaNotOrderByDataUltimaAlteracaoAsc(StatusPedido.PRONTO, PlataformaPedido.INTERNO, PageRequest.of(0,quantidade));
    }

    public @Nullable List<Pedido> listarInternosProntos(int quantidade) {
        return pedidoRepository.findByStatusAndPlataformaOrderByDataUltimaAlteracaoDesc(StatusPedido.PRONTO, PlataformaPedido.INTERNO, PageRequest.of(0,quantidade));
    }
}