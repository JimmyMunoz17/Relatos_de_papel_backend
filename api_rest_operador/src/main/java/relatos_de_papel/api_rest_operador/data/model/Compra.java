package relatos_de_papel.api_rest_operador.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "compras")
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    private Long idCompra;
    
    @Column(name = "libro_id", nullable = false)
    private Long libroId;
    
    @Column(name = "cantidad", nullable = false)
    private Long cantidad;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;
    
    @PrePersist
    public void prePersist() {
        if (fechaCompra == null) {
            fechaCompra = LocalDateTime.now();
        }
    }
}