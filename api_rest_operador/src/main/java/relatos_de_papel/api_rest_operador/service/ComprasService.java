package relatos_de_papel.api_rest_operador.service;

import relatos_de_papel.api_rest_operador.controller.model.CompraDto;
import relatos_de_papel.api_rest_operador.controller.model.NuevaCompraRequest;
import relatos_de_papel.api_rest_operador.data.model.Compra;

import java.time.LocalDate;
import java.util.List;

public interface ComprasService {
    
    List<Compra> getCompras();
    
    Compra getCompra(Long id);
    
    List<Compra> getComprasByLibroId(Long libroId);
    
    List<Compra> getComprasByFecha(LocalDate fecha);
    
    CompraDto registrarCompra(NuevaCompraRequest request);
    
    void deleteCompra(Long id);
}