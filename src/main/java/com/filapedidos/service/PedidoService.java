package com.filapedidos.service;

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
}