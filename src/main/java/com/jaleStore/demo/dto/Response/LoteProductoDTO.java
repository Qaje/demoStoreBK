package com.jaleStore.demo.dto.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class LoteProductoDTO {
    private Long id;
    private String numeroLote;
    private String descripcion;
    private ProductoResumeDTO producto;
    private Integer totalPares;
    private Double tallaMinima;
    private Double tallaMaxima;
    private BigDecimal costoTotal;
    private BigDecimal costoPorPar;
    private LocalDateTime fechaIngreso;
    private String proveedor;
    private List<DetalleLoteDTO> detalles;

    // getters y setters
}
