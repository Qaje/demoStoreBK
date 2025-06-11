package com.jaleStore.demo.repository;

import com.jaleStore.demo.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    //busca productos activos y disponibles para venta
    List<Producto> findByAactivoTrue();

//    busca productos activos con paginacion
    Page<Producto> findByActivoTrue(Pageable pageable);

    /**
     * Busca productos con stock disponible
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock > 0")
    List<Producto> findProductosDisponibles();

    /**
     * Busca productos con stock disponible con paginación
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock > 0")
    Page<Producto> findProductosDisponibles(Pageable pageable);

    // ===== BÚSQUEDAS POR FILTROS =====

    /**
     * Busca productos por nombre (case insensitive)
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Producto> findByNombreContainingIgnoreCase(@Param("nombre") String nombre);

    /**
     * Busca productos por código exacto
     */
    Optional<Producto> findByCodigoAndActivoTrue(@Param("codigo") String codigo);

    /**
     * Busca productos por marca
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND LOWER(p.marca) LIKE LOWER(CONCAT('%', :marca, '%'))")
    List<Producto> findByMarcaContainingIgnoreCase(@Param("marca") String marca);

    /**
     * Busca productos por categoría
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    List<Producto> findByCategoriaContainingIgnoreCase(@Param("categoria") String categoria);

    /**
     * Busca productos por talla
     */
    List<Producto> findByTallaAndActivoTrue(@Param("talla") String talla);

    /**
     * Busca productos por color
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND LOWER(p.color) LIKE LOWER(CONCAT('%', :color, '%'))")
    List<Producto> findByColorContainingIgnoreCase(@Param("color") String color);

    // ===== BÚSQUEDAS POR RANGOS DE PRECIO =====

    /**
     * Busca productos por rango de precio unitario
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.precioUnitario BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioUnitarioBetween(@Param("precioMin") BigDecimal precioMin, @Param("precioMax") BigDecimal precioMax);

    /**
     * Busca productos por rango de precio mayorista
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.precioMayorista BETWEEN :precioMin AND :precioMax")
    List<Producto> findByPrecioMayoristaBetween(@Param("precioMin") BigDecimal precioMin, @Param("precioMax") BigDecimal precioMax);

    // ===== BÚSQUEDAS POR STOCK =====

    /**
     * Busca productos con stock bajo (menos de cantidad mínima)
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock <= :stockMinimo")
    List<Producto> findProductosConStockBajo(@Param("stockMinimo") Integer stockMinimo);

    /**
     * Busca productos sin stock
     */
    @Query("SELECT p FROM Producto p WHERE p.stock = 0")
    List<Producto> findProductosSinStock();

    /**
     * Busca productos con stock mayor a cantidad específica
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true AND p.stock >= :cantidadMinima")
    List<Producto> findProductosConStock(@Param("cantidadMinima") Integer cantidadMinima);

    // ===== BÚSQUEDA COMBINADA =====

    /**
     * Búsqueda avanzada con múltiples filtros
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true " +
            "AND (:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:marca IS NULL OR LOWER(p.marca) LIKE LOWER(CONCAT('%', :marca, '%'))) " +
            "AND (:categoria IS NULL OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))) " +
            "AND (:talla IS NULL OR p.talla = :talla) " +
            "AND (:color IS NULL OR LOWER(p.color) LIKE LOWER(CONCAT('%', :color, '%'))) " +
            "AND (:precioMin IS NULL OR p.precioUnitario >= :precioMin) " +
            "AND (:precioMax IS NULL OR p.precioUnitario <= :precioMax) " +
            "AND p.stock > 0")
    Page<Producto> busquedaAvanzada(
            @Param("nombre") String nombre,
            @Param("marca") String marca,
            @Param("categoria") String categoria,
            @Param("talla") String talla,
            @Param("color") String color,
            @Param("precioMin") BigDecimal precioMin,
            @Param("precioMax") BigDecimal precioMax,
            Pageable pageable);

    // ===== ACTUALIZACIONES DE STOCK =====

    /**
     * Actualiza stock de un producto
     */
    @Modifying
    @Query("UPDATE Producto p SET p.stock = :nuevoStock WHERE p.id = :productoId")
    int actualizarStock(@Param("productoId") Long productoId, @Param("nuevoStock") Integer nuevoStock);

    /**
     * Reduce stock de un producto
     */
    @Modifying
    @Query("UPDATE Producto p SET p.stock = p.stock - :cantidad WHERE p.id = :productoId AND p.stock >= :cantidad")
    int reducirStock(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    /**
     * Aumenta stock de un producto
     */
    @Modifying
    @Query("UPDATE Producto p SET p.stock = p.stock + :cantidad WHERE p.id = :productoId")
    int aumentarStock(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);

    /**
     * Desactiva productos sin stock
     */
    @Modifying
    @Query("UPDATE Producto p SET p.activo = false WHERE p.stock <= 0")
    int desactivarProductosSinStock();

    // ===== ESTADÍSTICAS Y REPORTES =====

    /**
     * Cuenta productos activos
     */
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.activo = true")
    Long contarProductosActivos();

    /**
     * Cuenta productos por marca
     */
    @Query("SELECT p.marca, COUNT(p) FROM Producto p WHERE p.activo = true GROUP BY p.marca")
    List<Object[]> contarProductosPorMarca();

    /**
     * Cuenta productos por categoría
     */
    @Query("SELECT p.categoria, COUNT(p) FROM Producto p WHERE p.activo = true GROUP BY p.categoria")
    List<Object[]> contarProductosPorCategoria();

    /**
     * Obtiene todas las marcas disponibles
     */
    @Query("SELECT DISTINCT p.marca FROM Producto p WHERE p.activo = true ORDER BY p.marca")
    List<String> obtenerMarcasDisponibles();

    /**
     * Obtiene todas las categorías disponibles
     */
    @Query("SELECT DISTINCT p.categoria FROM Producto p WHERE p.activo = true ORDER BY p.categoria")
    List<String> obtenerCategoriasDisponibles();

    /**
     * Obtiene todas las tallas disponibles
     */
    @Query("SELECT DISTINCT p.talla FROM Producto p WHERE p.activo = true ORDER BY p.talla")
    List<String> obtenerTallasDisponibles();

    /**
     * Obtiene todos los colores disponibles
     */
    @Query("SELECT DISTINCT p.color FROM Producto p WHERE p.activo = true ORDER BY p.color")
    List<String> obtenerColoresDisponibles();

    /**
     * Valor total del inventario
     */
    @Query("SELECT SUM(p.precioUnitario * p.stock) FROM Producto p WHERE p.activo = true")
    BigDecimal calcularValorTotalInventario();

    /**
     * Productos más caros
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true ORDER BY p.precioUnitario DESC")
    List<Producto> findProductosMasCaros(Pageable pageable);

    /**
     * Productos más baratos
     */
    @Query("SELECT p FROM Producto p WHERE p.activo = true ORDER BY p.precioUnitario ASC")
    List<Producto> findProductosMasBaratos(Pageable pageable);

    // ===== VALIDACIONES =====

    /**
     * Verifica si existe un producto con el mismo código
     */
    boolean existsByCodigoAndActivoTrue(@Param("codigo") String codigo);

    /**
     * Verifica si un producto tiene stock suficiente
     */
    @Query("SELECT CASE WHEN p.stock >= :cantidad THEN true ELSE false END FROM Producto p WHERE p.id = :productoId")
    Boolean tieneStockSuficiente(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);
}
