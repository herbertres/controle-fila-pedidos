package com.filapedidos.service;

import com.filapedidos.model.Pedido;
import com.filapedidos.repository.PedidoRepository;

public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }
}