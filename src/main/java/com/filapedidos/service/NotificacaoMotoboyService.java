package com.filapedidos.service;

import org.springframework.stereotype.Service;

@Service
public class NotificacaoMotoboyService {

    private volatile boolean notificacaoAtiva = false;

    public boolean isNotificacaoAtiva() {
        return notificacaoAtiva;
    }

    public void iniciarNotificacao() {
        notificacaoAtiva = true;
    }

    public void pararNotificacao() {
        notificacaoAtiva = false;
    }
}