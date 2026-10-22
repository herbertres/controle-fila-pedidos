package com.filapedidos.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.filapedidos.model.Pedido;
import com.filapedidos.model.PlataformaPedido;
import com.filapedidos.model.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByStatusAndPlataformaNotOrderByDataUltimaAlteracaoAsc(
        StatusPedido status,
        PlataformaPedido plataforma,
        Pageable page
    );

    List<Pedido> findByStatusAndPlataformaOrderByDataUltimaAlteracaoDesc(
        StatusPedido status, PlataformaPedido plataforma, Pageable page
    );
}