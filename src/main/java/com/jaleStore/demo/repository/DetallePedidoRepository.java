package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    /**
     * Busca todos los detalles de un pedido específico
     */
    @Query("SELECT d FROM DetallePedido d JOIN FETCH d.producto WHERE d.pedido.id = :pedidoId")
    List<DetallePedido> findByPedidoId(@Param("pedidoId") Long pedidoId);

    /**
     * Busca todos los detalles de pedidos de un usuario específico
     */
    @Query("SELECT d FROM DetallePedido d JOIN FETCH d.producto WHERE d.pedido.usuario.id = :usuarioId")
    List<DetallePedido> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca detalles por producto específico
     */
    @Query("SELECT d FROM DetallePedido d WHERE d.producto.id = :productoId")
    List<DetallePedido> findByProductoId(@Param("productoId") Long productoId);

    /**
     * Calcula el total de un pedido sumando todos sus detalles
     */
    @Query("SELECT SUM(d.subtotal) FROM DetallePedido d WHERE d.pedido.id = :pedidoId")
    BigDecimal calcularTotalPedido(@Param("pedidoId") Long pedidoId);

    /**
     * Cuenta la cantidad total de productos en un pedido
     */
    @Query("SELECT SUM(d.cantidad) FROM DetallePedido d WHERE d.pedido.id = :pedidoId")
    Integer contarProductosPedido(@Param("pedidoId") Long pedidoId);

    /**
     * Busca los productos más vendidos
     */
    @Query("SELECT d.producto.id, d.nombreProducto, SUM(d.cantidad) as totalVendido " +
            "FROM DetallePedido d " +
            "GROUP BY d.producto.id, d.nombreProducto " +
            "ORDER BY totalVendido DESC")
    List<Object[]> findProductosMasVendidos();

    /**
     * Busca ventas por tipo (mayorista o minorista)
     */
    @Query("SELECT d FROM DetallePedido d WHERE d.esVentaMayorista = :esVentaMayorista")
    List<DetallePedido> findByTipoVenta(@Param("esVentaMayorista") Boolean esVentaMayorista);
}
