package relatos_de_papel.api_rest_operador.external.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LibroDto {
    
    private Long id;
    
    private String titulo;
    
    private String autor;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaPublicacion;
    
    private String categoria;
    
    private String isbn;
    
    private Integer valoracion;
    
    private Boolean visibilidad;
    
    private Integer stock;
}