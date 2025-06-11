package com.jaleStore.demo.entity;

import com.jaleStore.demo.util.EstadoPedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroPedido; // Código único

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> items = new ArrayList<>();

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column(precision = 10, scale = 2)
    private BigDecimal subTotal;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado; // PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO

    private LocalDateTime fechaPedido;
    private LocalDateTime fechaPago;

    private BigDecimal descuentoMayorista;

    // Datos de entrega
    private String direccionEntrega;
    private String telefonoEntrega;
    private String notasEntrega;

    // Datos de pago QR
    private String codigoQR;
    private String urlPagoQR;
    private String transaccionId;

    public void setDescuentoMayorista(BigDecimal descuentoMayorista) {
        this.descuentoMayorista = descuentoMayorista;
    }
}
