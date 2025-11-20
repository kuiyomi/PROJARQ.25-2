package entrega.dominio.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "entregas")
public class Entrega {
    public enum Status {
        AGUARDANDO,
        TRANSPORTE,
        ENTREGUE,
        CANCELADO
    }

    @Id
    private Long pedidoId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    private String entregador;
    private LocalDateTime dataHoraAtualizacao;

    public Entrega() {}

    public Entrega(Long pedidoId, Status status) {
        this.pedidoId = pedidoId;
        this.status = status;
        this.dataHoraAtualizacao = LocalDateTime.now();
    }

    public Long getPedidoId() { return pedidoId; }
    public Status getStatus() { return status; }
    public String getEntregador() { return entregador; }
    public LocalDateTime getDataHoraAtualizacao() { return dataHoraAtualizacao; }

    public void setStatus(Status status) {
        this.status = status;
        this.dataHoraAtualizacao = LocalDateTime.now();
    }
    public void setEntregador(String entregador) { this.entregador = entregador; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public void setDataHoraAtualizacao(LocalDateTime dataHoraAtualizacao) { this.dataHoraAtualizacao = dataHoraAtualizacao; }

    @Override
    public String toString() {
        return "Entrega [pedidoId=" + pedidoId + ", status=" + status + ", entregador=" + entregador + ", dataHoraAtualizacao=" + dataHoraAtualizacao + "]";
    }
}