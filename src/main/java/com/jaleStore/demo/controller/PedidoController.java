package com.jaleStore.demo.controller;

import com.jaleStore.demo.dto.Response.DatosEntregaDTO;
import com.jaleStore.demo.dto.Response.PedidoConQRDTO;
import com.jaleStore.demo.dto.Response.PedidoDetalleDTO;
import com.jaleStore.demo.entity.Usuario;
import com.jaleStore.demo.security.SeguridadUtil;
import com.jaleStore.demo.service.PedidoService;
import com.jaleStore.demo.util.EstadoPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.time.LocalDate;

//@RestController
//@RequestMapping("/api/pedidos")
public class PedidoController {
//
//    @Autowired
//    private PedidoService pedidoService;
//
//    @PreAuthorize("hasRole('CLIENTE')")
//    @PostMapping("/crear")
//    public ResponseEntity<PedidoConQRDTO> crearPedido(@RequestBody DatosEntregaDTO datosEntrega) {
//        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
//        PedidoConQRDTO pedido = pedidoService.crearPedidoDesdeCarrito(usuarioId, datosEntrega);
//        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
//    }
//
//    @GetMapping("/{numeroPedido}/qr")
//    public ResponseEntity<byte[]> obtenerQRPago(@PathVariable String numeroPedido) {
//        byte[] qrImage = pedidoService.obtenerImagenQR(numeroPedido);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        headers.setContentLength(qrImage.length);
//
//        return new ResponseEntity<>(qrImage, headers, HttpStatus.OK);
//    }
//
//    @GetMapping("/{numeroPedido}")
//    public ResponseEntity<PedidoDetalleDTO> obtenerDetallePedido(@PathVariable String numeroPedido) {
//        PedidoDetalleDTO pedido = pedidoService.obtenerDetallePedido(numeroPedido);
//        return ResponseEntity.ok(pedido);
//    }
//
//    @PreAuthorize("hasRole('CLIENTE')")
//    @GetMapping("/mis-pedidos")
//    public ResponseEntity<Page<PedidoResumenDTO>> listarMisPedidos(Pageable pageable) {
//        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
//        Page<PedidoResumenDTO> pedidos = pedidoService.listarPedidosUsuario(usuarioId, pageable);
//        return ResponseEntity.ok(pedidos);
//    }
//
//    // Webhook para confirmación de pago (público para servicios externos)
//    @PostMapping("/confirmar-pago")
//    public ResponseEntity<Void> confirmarPago(@RequestBody ConfirmarPagoDTO datos) {
//        pedidoService.confirmarPago(datos.getTransaccionId(), datos.getNumeroPedido());
//        return ResponseEntity.ok().build();
//    }
//
//    // Endpoint para que el cliente confirme pago manual (Yape, transferencia, etc.)
//    @PreAuthorize("hasRole('CLIENTE')")
//    @PostMapping("/{numeroPedido}/confirmar-pago-manual")
//    public ResponseEntity<Void> confirmarPagoManual(
//            @PathVariable String numeroPedido,
//            @RequestParam("comprobante") MultipartFile comprobante) {
//
//        Long usuarioId = SeguridadUtil.obtenerUsuarioAutenticado().getId();
//        pedidoService.subirComprobantePago(numeroPedido, usuarioId, comprobante);
//        return ResponseEntity.ok().build();
//    }
//
//    // ========== ENDPOINTS PARA ADMINISTRADORES ==========
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @GetMapping("/admin/todos")
//    public ResponseEntity<Page<PedidoAdminDTO>> listarTodosPedidos(
//            @RequestParam(required = false) EstadoPedido estado,
//            @RequestParam(required = false) LocalDate fechaDesde,
//            @RequestParam(required = false) LocalDate fechaHasta,
//            Pageable pageable) {
//
//        Page<PedidoAdminDTO> pedidos = pedidoService.listarPedidosAdmin(estado, fechaDesde, fechaHasta, pageable);
//        return ResponseEntity.ok(pedidos);
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{id}/estado")
//    public ResponseEntity<Void> actualizarEstado(
//            @PathVariable Long id,
//            @RequestBody ActualizarEstadoDTO nuevoEstado) {
//
//        pedidoService.actualizarEstado(id, nuevoEstado.getEstado(), nuevoEstado.getObservaciones());
//        return ResponseEntity.ok().build();
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{id}/aprobar-pago")
//    public ResponseEntity<Void> aprobarPago(@PathVariable Long id) {
//        pedidoService.aprobarPagoManual(id);
//        return ResponseEntity.ok().build();
//    }
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/{id}/rechazar-pago")
//    public ResponseEntity<Void> rechazarPago(
//            @PathVariable Long id,
//            @RequestBody String motivo) {
//
//        pedidoService.rechazarPagoManual(id, motivo);
//        return ResponseEntity.ok().build();
//    }

}
