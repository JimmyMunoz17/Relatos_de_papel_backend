package relatos_de_papel.api_rest_operador.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraDto {
    
    private Long idCompra;
    
    private Long libroId;
    
    private Long cantidad;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaCompra;
}