package relatos_de_papel.api_rest_operador.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import relatos_de_papel.api_rest_operador.data.model.Compra;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraJpaRepository extends JpaRepository<Compra, Long> {
    
    List<Compra> findByLibroId(Long libroId);
    
    @Query("SELECT c FROM Compra c WHERE DATE(c.fechaCompra) = DATE(:fecha)")
    List<Compra> findByFechaCompra(@Param("fecha") LocalDateTime fecha);
}