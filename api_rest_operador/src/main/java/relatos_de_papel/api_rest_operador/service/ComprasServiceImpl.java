package relatos_de_papel.api_rest_operador.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import relatos_de_papel.api_rest_operador.controller.model.CompraDto;
import relatos_de_papel.api_rest_operador.controller.model.NuevaCompraRequest;
import relatos_de_papel.api_rest_operador.data.model.Compra;
import relatos_de_papel.api_rest_operador.data.repository.CompraRepository;
import relatos_de_papel.api_rest_operador.external.model.LibroDto;
import relatos_de_papel.api_rest_operador.external.service.BuscadorService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComprasServiceImpl implements ComprasService {
    
    private final CompraRepository compraRepository;
    private final BuscadorService buscadorService;
    
    @Override
    public List<Compra> getCompras() {
        return compraRepository.getCompras();
    }
    
    @Override
    public Compra getCompra(Long id) {
        return compraRepository.getCompra(id)
            .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + id));
    }
    
    @Override
    public List<Compra> getComprasByLibroId(Long libroId) {
        return compraRepository.getComprasByLibroId(libroId);
    }
    
    @Override
    public List<Compra> getComprasByFecha(LocalDate fecha) {
        return compraRepository.getComprasByFecha(fecha);
    }
    
    @Override
    public CompraDto registrarCompra(NuevaCompraRequest request) {
        log.info("Intentando registrar compra para libro ID: {} con cantidad: {}", 
                request.getLibroId(), request.getCantidad());
        
        // Validar cantidad
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new RuntimeException("La cantidad debe ser mayor a 0");
        }
        
        // Validar libro en el microservicio buscador
        LibroDto libro = buscadorService.validarLibro(request.getLibroId());
        if (libro == null) {
            throw new RuntimeException("El libro con ID " + request.getLibroId() + 
                " no existe, no está visible o no tiene stock disponible");
        }
        
        log.info("Libro validado: {} - Stock disponible: {}", 
                libro.getTitulo(), libro.getStock());
        
        // Verificar que hay suficiente stock
        if (libro.getStock() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente. Stock disponible: " + libro.getStock() + 
                ", cantidad solicitada: " + request.getCantidad());
        }
        
        // Crear y guardar la compra
        Compra compra = Compra.builder()
            .libroId(request.getLibroId())
            .cantidad(request.getCantidad())
            .fechaCompra(LocalDateTime.now())
            .build();
            
        Compra compraGuardada = compraRepository.saveCompra(compra);
        
        log.info("Compra registrada exitosamente con ID: {}", compraGuardada.getIdCompra());
        
        // Convertir a DTO para respuesta
        return CompraDto.builder()
            .idCompra(compraGuardada.getIdCompra())
            .libroId(compraGuardada.getLibroId())
            .cantidad(compraGuardada.getCantidad())
            .fechaCompra(compraGuardada.getFechaCompra())
            .build();
    }
    
    @Override
    public void deleteCompra(Long id) {
        if (!compraRepository.getCompra(id).isPresent()) {
            throw new RuntimeException("Compra no encontrada con ID: " + id);
        }
        compraRepository.deleteCompra(id);
    }
}