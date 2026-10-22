package com.filapedidos.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O código do pedido é obrigatório")
    @Size(max = 10, message = "O código do pedido deve ter no máximo 10 caracteres")
    @Column(nullable = false, length = 10)
    private String codigoPedido;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_ultima_alteracao", nullable = false, updatable = true)
    private LocalDateTime dataUltimaAlteracao;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status;

    @NotNull(message = "A plataforma é obrigatória")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PlataformaPedido plataforma;

    public Pedido() {
    }

    public Pedido(
            @NotBlank(message = "O código do pedido é obrigatório") @Size(max = 10, message = "O código do pedido deve ter no máximo 10 caracteres") String codigoPedido,
            @NotNull(message = "O status é obrigatório") StatusPedido status,
            @NotNull(message = "A plataforma é obrigatória") PlataformaPedido plataforma) {
        this.codigoPedido = codigoPedido;
        this.status = status;
        this.plataforma = plataforma;
    }

    @PrePersist
    protected void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = LocalDateTime.now();
        }

        if (dataUltimaAlteracao == null) {
            dataUltimaAlteracao = LocalDateTime.now();
        }

        if (status == null) {
            status = StatusPedido.EM_PREPARO;
        }
    }

    public Long getId() {
        return id;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String descricao) {
        this.codigoPedido = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

        public PlataformaPedido getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(PlataformaPedido plataforma) {
        this.plataforma = plataforma;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }
}