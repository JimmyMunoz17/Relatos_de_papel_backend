package relatos_de_papel.api_rest_operador.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relatos_de_papel.api_rest_operador.controller.model.CompraDto;
import relatos_de_papel.api_rest_operador.controller.model.NuevaCompraRequest;
import relatos_de_papel.api_rest_operador.data.model.Compra;
import relatos_de_papel.api_rest_operador.service.ComprasService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ComprasController {
    
    private final ComprasService comprasService;
    
    @GetMapping("/compras")
    public ResponseEntity<List<Compra>> getCompras(@RequestHeader Map<String, String> headers) {
        log.info("headers: {}", headers);
        
        try {
            List<Compra> compras = comprasService.getCompras();
            return compras != null && !compras.isEmpty()
                ? ResponseEntity.ok(compras)
                : ResponseEntity.ok(Collections.emptyList());
        } catch (Exception e) {
            log.error("Error al obtener compras: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/compras/{id}")
    public ResponseEntity<Compra> getCompra(@RequestHeader Map<String, String> headers,
                                           @PathVariable Long id) {
        log.info("headers: {}, buscando compra con ID: {}", headers, id);
        
        try {
            Compra compra = comprasService.getCompra(id);
            return ResponseEntity.ok(compra);
        } catch (RuntimeException e) {
            log.error("Compra no encontrada: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error al obtener compra: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/compras/libro/{libroId}")
    public ResponseEntity<List<Compra>> getComprasByLibroId(@RequestHeader Map<String, String> headers,
                                                           @PathVariable Long libroId) {
        log.info("headers: {}, buscando compras del libro ID: {}", headers, libroId);
        
        try {
            List<Compra> compras = comprasService.getComprasByLibroId(libroId);
            return ResponseEntity.ok(compras);
        } catch (Exception e) {
            log.error("Error al obtener compras del libro {}: {}", libroId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/compras/fecha/{fecha}")
    public ResponseEntity<List<Compra>> getComprasByFecha(@RequestHeader Map<String, String> headers,
                                                         @PathVariable String fecha) {
        log.info("headers: {}, buscando compras de la fecha: {}", headers, fecha);
        
        try {
            LocalDate fechaParsed = LocalDate.parse(fecha);
            List<Compra> compras = comprasService.getComprasByFecha(fechaParsed);
            return ResponseEntity.ok(compras);
        } catch (DateTimeParseException e) {
            log.error("Formato de fecha inválido: {}. Use formato YYYY-MM-DD", fecha);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error al obtener compras de la fecha {}: {}", fecha, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/compras")
    public ResponseEntity<CompraDto> registrarCompra(@RequestHeader Map<String, String> headers,
                                                    @RequestBody NuevaCompraRequest request) {
        log.info("headers: {}, registrando compra: {}", headers, request);
        
        try {
            CompraDto compraDto = comprasService.registrarCompra(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(compraDto);
        } catch (RuntimeException e) {
            log.error("Error al registrar compra: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error interno al registrar compra: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/compras/{id}")
    public ResponseEntity<Void> deleteCompra(@RequestHeader Map<String, String> headers,
                                            @PathVariable Long id) {
        log.info("headers: {}, eliminando compra con ID: {}", headers, id);
        
        try {
            comprasService.deleteCompra(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error al eliminar compra: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error interno al eliminar compra: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}