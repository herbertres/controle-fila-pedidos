package com.filapedidos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.filapedidos.model.Pedido;
import com.filapedidos.model.PlataformaPedido;
import com.filapedidos.model.StatusPedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
        List<Pedido> findByStatusOrderByDataUltimaAlteracaoAsc(
                        StatusPedido status,
                        Pageable page);

        List<Pedido> findByStatusAndPlataformaOrderByDataUltimaAlteracaoDesc(
                        StatusPedido status, PlataformaPedido plataforma, Pageable page);

        java.util.Optional<Pedido> findByCodigoPedidoAndDataCriacaoGreaterThanEqualAndDataCriacaoLessThan(
                        String codigoPedido, LocalDateTime inicio, LocalDateTime fim);

        boolean existsByCodigoPedidoAndDataCriacaoGreaterThanEqualAndDataCriacaoLessThan(
                        String codigoPedido,
                        LocalDateTime inicio,
                        LocalDateTime fim);

        @Modifying
        @Query("DELETE FROM Pedido p WHERE p.dataCriacao < :limite")
        int expurgarPedidosAntigos(
                        @Param("limite") LocalDateTime limite);
}