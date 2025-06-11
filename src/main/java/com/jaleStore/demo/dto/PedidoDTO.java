package com.jaleStore.demo.dto;

import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.util.EstadoPedido;
import com.jaleStore.demo.util.TipoVenta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {

        private Long id;
        private String numeroPedido;

        // Información del usuario (solo datos necesarios)
        private Usuario usuario;

        // Fechas importantes
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime fechaPedido;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime fechaPago;

        @JsonFormat(pattern = "dd/MM/yyyy")
        private LocalDateTime fechaEntregaEstimada;

        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        private LocalDateTime fechaEntrega;

        // Estado y tipo
        private EstadoPedido estado;
        private TipoVenta tipoVenta;

        // Información financiera
        private BigDecimal total;
        private BigDecimal subTotal;
        private String transaccionId;

        // Datos de entrega
        private String direccionEntrega;
        private String telefonoEntrega;
        private String notasEntrega;

        // Items del pedido
        private List<ItemPedidoDTO> items;
        private BigDecimal descuentoMayorista;

        // QR de pago (Base64)
        private String qrPago;

        // Información adicional para el cliente
        private Integer cantidadTotalItems;
        private String estadoDescripcion;
        private boolean puedeSerCancelado;

        // Constructores
        public PedidoDTO() {
        }

        public PedidoDTO(Long id, String numeroPedido, EstadoPedido estado, BigDecimal total) {
            this.id = id;
            this.numeroPedido = numeroPedido;
            this.estado = estado;
            this.total = total;
        }

        // Getters y Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNumeroPedido() {
            return numeroPedido;
        }

        public void setNumeroPedido(String numeroPedido) {
            this.numeroPedido = numeroPedido;
        }

        public Usuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
        }

        public LocalDateTime getFechaPedido() {
            return fechaPedido;
        }

        public void setFechaPedido(LocalDateTime fechaPedido) {
            this.fechaPedido = fechaPedido;
        }

        public LocalDateTime getFechaPago() {
            return fechaPago;
        }

        public void setFechaPago(LocalDateTime fechaPago) {
            this.fechaPago = fechaPago;
        }

        public LocalDateTime getFechaEntregaEstimada() {
            return fechaEntregaEstimada;
        }

        public void setFechaEntregaEstimada(LocalDateTime fechaEntregaEstimada) {
            this.fechaEntregaEstimada = fechaEntregaEstimada;
        }

        public LocalDateTime getFechaEntrega() {
            return fechaEntrega;
        }

        public void setFechaEntrega(LocalDateTime fechaEntrega) {
            this.fechaEntrega = fechaEntrega;
        }

        public EstadoPedido getEstado() {
            return estado;
        }

        public void setEstado(EstadoPedido estado) {
            this.estado = estado;
            this.estadoDescripcion = obtenerDescripcionEstado(estado);
            this.puedeSerCancelado = estado == EstadoPedido.PENDIENTE;
        }

        public TipoVenta getTipoVenta() {
            return tipoVenta;
        }

        public void setTipoVenta(TipoVenta tipoVenta) {
            this.tipoVenta = tipoVenta;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getSubTotal() {
            return total;
        }

        public void setSubTotal(BigDecimal total) {
            this.total = total;
        }

        public String getTransaccionId() {
            return transaccionId;
        }

        public void setTransaccionId(String transaccionId) {
            this.transaccionId = transaccionId;
        }

        public String getDireccionEntrega() {
            return direccionEntrega;
        }

        public void setDireccionEntrega(String direccionEntrega) {
            this.direccionEntrega = direccionEntrega;
        }

        public String getTelefonoEntrega() {
            return telefonoEntrega;
        }

        public void setTelefonoEntrega(String telefonoEntrega) {
            this.telefonoEntrega = telefonoEntrega;
        }

        public String getNotasEntrega() {
            return notasEntrega;
        }

        public void setNotasEntrega(String notasEntrega) {
            this.notasEntrega = notasEntrega;
        }

        public List<ItemPedidoDTO> getItems() {
            return items;
        }

        public void setItems(List<ItemPedidoDTO> items) {
            this.items = items;
            // Calcular cantidad total de items
            this.cantidadTotalItems = items != null ?
                    items.stream().mapToInt(ItemPedidoDTO::getCantidad).sum() : 0;
        }

        public String getQrPago() {
            return qrPago;
        }

        public void setQrPago(String qrPago) {
            this.qrPago = qrPago;
        }

        public Integer getCantidadTotalItems() {
            return cantidadTotalItems;
        }

        public void setCantidadTotalItems(Integer cantidadTotalItems) {
            this.cantidadTotalItems = cantidadTotalItems;
        }

        public String getEstadoDescripcion() {
            return estadoDescripcion;
        }

        public void setEstadoDescripcion(String estadoDescripcion) {
            this.estadoDescripcion = estadoDescripcion;
        }

        public boolean isPuedeSerCancelado() {
            return puedeSerCancelado;
        }

        public void setPuedeSerCancelado(boolean puedeSerCancelado) {
            this.puedeSerCancelado = puedeSerCancelado;
        }

        // Método auxiliar para obtener descripción del estado
        private String obtenerDescripcionEstado(EstadoPedido estado) {
            switch (estado) {
                case PENDIENTE:
                    return "Pendiente de pago";
                case PAGADO:
                    return "Pagado - En preparación";
                case PREPARANDO:
                    return "Preparando pedido";
                case ENVIADO:
                    return "Enviado";
                case ENTREGADO:
                    return "Entregado";
                case CANCELADO:
                    return "Cancelado";
                default:
                    return estado.toString();
            }
        }

    public void setDescuentoMayorista(BigDecimal descuentoMayorista) {
            this.descuentoMayorista = descuentoMayorista;
    }
}
