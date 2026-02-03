package relatos_de_papel.api_rest_operador.data.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import relatos_de_papel.api_rest_operador.data.model.Compra;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompraRepositoryImpl implements CompraRepository {
    
    private final CompraJpaRepository compraJpaRepository;
    
    @Override
    public List<Compra> getCompras() {
        return compraJpaRepository.findAll();
    }
    
    @Override
    public Optional<Compra> getCompra(Long id) {
        return compraJpaRepository.findById(id);
    }
    
    @Override
    public List<Compra> getComprasByLibroId(Long libroId) {
        return compraJpaRepository.findByLibroId(libroId);
    }
    
    @Override
    public List<Compra> getComprasByFecha(LocalDate fecha) {
        LocalDateTime fechaHora = fecha.atStartOfDay();
        return compraJpaRepository.findByFechaCompra(fechaHora);
    }
    
    @Override
    public Compra saveCompra(Compra compra) {
        return compraJpaRepository.save(compra);
    }
    
    @Override
    public void deleteCompra(Long id) {
        compraJpaRepository.deleteById(id);
    }
}