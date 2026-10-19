package com.filapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.filapedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}