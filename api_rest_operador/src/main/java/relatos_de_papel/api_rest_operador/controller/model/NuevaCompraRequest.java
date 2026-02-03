package relatos_de_papel.api_rest_operador.controller.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NuevaCompraRequest {
    
    private Long libroId;
    
    private Long cantidad;
}