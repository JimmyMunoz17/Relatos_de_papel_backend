package relatos_de_papel.api_rest_operador.data.repository;

import relatos_de_papel.api_rest_operador.data.model.Compra;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CompraRepository {
    
    List<Compra> getCompras();
    
    Optional<Compra> getCompra(Long id);
    
    List<Compra> getComprasByLibroId(Long libroId);
    
    List<Compra> getComprasByFecha(LocalDate fecha);
    
    Compra saveCompra(Compra compra);
    
    void deleteCompra(Long id);
}