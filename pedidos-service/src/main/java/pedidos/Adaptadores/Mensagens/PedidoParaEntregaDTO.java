package Pedidos.Adaptadores.Mensagens;

import java.io.Serializable;

public class PedidoParaEntregaDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long pedidoId;
    private String clienteEmail;

    public PedidoParaEntregaDTO() {}

    public PedidoParaEntregaDTO(Long pedidoId, String clienteEmail) {
        this.pedidoId = pedidoId;
        this.clienteEmail = clienteEmail;
    }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public String getClienteEmail() { return clienteEmail; }
    public void setClienteEmail(String clienteEmail) { this.clienteEmail = clienteEmail; }

    @Override
    public String toString() {
        return "PedidoParaEntregaDTO [pedidoId=" + pedidoId + ", clienteEmail=" + clienteEmail + "]";
    }
}