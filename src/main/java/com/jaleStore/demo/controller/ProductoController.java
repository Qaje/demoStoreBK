package com.jaleStore.demo.controller;

import com.jaleStore.demo.dto.ProductoDTO;
import com.jaleStore.demo.entity.Producto;
import com.jaleStore.demo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRespository;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRespository.findAll();
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long idProducto) throws Exception {
        Optional<Producto> productoOptional = productoRespository.findById(idProducto);
        if (productoOptional.isPresent()){
            return new ResponseEntity<>(productoOptional.get(), HttpStatus.OK);
        }else {
            throw new Exception("Producto no encontrado");
        }
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoRespository.save(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productodto) {
        Optional<Producto> productoOpt = productoRespository.findById(id);
        if (productoOpt.isEmpty())
            return ResponseEntity.notFound().build();

        Producto producto = productoOpt.get();

        if (productodto.getNombre() != null) producto.setNombre(productodto.getNombre());
        if (productodto.getCodigo() != null) producto.setCodigo(productodto.getCodigo());
        if (productodto.getMarca() != null) producto.setMarca(productodto.getMarca());
        if (productodto.getPrecioUnidad() != null) producto.setPrecioUnidad(productodto.getPrecioUnidad());
        if (productodto.getPrecioMayor() != null) producto.setPrecioMayor(productodto.getPrecioMayor());
        if (productodto.getStock() != null) producto.setStock(productodto.getStock());
        if (productodto.getTalla() != null) producto.setTalla(productodto.getTalla());
        if (productodto.getColor() != null) producto.setColor(productodto.getColor());
        return ResponseEntity.ok(productoRespository.save(producto));
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoRespository.deleteById(id);
    }

}
