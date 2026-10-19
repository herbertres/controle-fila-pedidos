package com.filapedidos.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.filapedidos.model.Pedido;
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
}