
package com.filapedidos.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.filapedidos.repository.PedidoRepository;

@Service
public class ExpurgoPedidosService {

    private static final Logger log = LoggerFactory.getLogger(ExpurgoPedidosService.class);

    private static final ZoneId FUSO_HORARIO = ZoneId.of("America/Sao_Paulo");

    private final PedidoRepository pedidoRepository;

    public ExpurgoPedidosService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 3 * * *", zone = "America/Sao_Paulo")
    @Transactional
    public void expurgarPedidosAntigos() {

        LocalDate hoje = LocalDate.now(FUSO_HORARIO);
        LocalDateTime inicioDoDia = hoje.atStartOfDay();

        int quantidade = pedidoRepository
                .expurgarPedidosAntigos(inicioDoDia);

        log.info(
                "Expurgo de pedidos concluído. Data de referência: {}. "
                        + "Pedidos removidos: {}.",
                hoje,
                quantidade);
    }
}
