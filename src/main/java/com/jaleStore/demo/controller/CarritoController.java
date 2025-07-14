package com.jaleStore.demo.controller;

//import com.jaleStore.demo.dto.ActualizarCantidadDTO;  // AGREGADO: Import faltante
import com.jaleStore.demo.dto.AgregarVarianteDTO;
import com.jaleStore.demo.dto.Response.ActualizarCantidadDTO;
import com.jaleStore.demo.dto.Response.CarritoDTO;
import com.jaleStore.demo.entity.Carrito;
import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.security.SeguridadUtil;
import com.jaleStore.demo.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrito")
@PreAuthorize("hasRole('CLIENTE')")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    @PostMapping("/agregar")
    public ResponseEntity<CarritoDTO> agregarVariante(@RequestBody AgregarVarianteDTO request) {
        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();

        CarritoDTO carrito = carritoService.agregarVariante(
                usuarioId,
                request.getVarianteId(),
                request.getCantidad()
        );

        return ResponseEntity.ok(carrito);
    }

    @GetMapping
    public ResponseEntity<Carrito> obtenerCarrito() {
        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
        Carrito carrito = carritoService.obtenerCarrito(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CarritoDTO> actualizarCantidad(
            @PathVariable Long itemId,
            @RequestBody ActualizarCantidadDTO request) {

        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
        CarritoDTO carrito = carritoService.actualizarCantidadItem(
                usuarioId, itemId, request.getNuevaCantidad());

        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CarritoDTO> eliminarItem(@PathVariable Long itemId) {
        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
        CarritoDTO carrito = carritoService.eliminarItem(usuarioId, itemId);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/aplicar-descuento-mayorista")
    public ResponseEntity<CarritoDTO> aplicarDescuentoMayorista() {
        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
        CarritoDTO carrito = carritoService.recalcularPreciosMayoristas(usuarioId);
        return ResponseEntity.ok(carrito);
    }

    @DeleteMapping("/vaciar")
    public ResponseEntity<Void> vaciarCarrito() {
        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok().build();
    }


}
