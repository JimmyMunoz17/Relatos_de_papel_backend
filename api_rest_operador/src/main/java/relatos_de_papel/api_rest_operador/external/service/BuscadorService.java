package relatos_de_papel.api_rest_operador.external.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import relatos_de_papel.api_rest_operador.external.model.LibroDto;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuscadorService {
    
    private final RestTemplate restTemplate;
    
    @Value("${buscador.url}")
    private String buscadorUrl;
    
    public LibroDto validarLibro(Long libroId) {
        try {
            String url = buscadorUrl + "/libros";
            
            ResponseEntity<List<LibroDto>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LibroDto>>() {}
            );
            
            if (response.getBody() != null) {
                return response.getBody().stream()
                    .filter(libro -> libro.getId().equals(libroId))
                    .filter(libro -> libro.getVisibilidad() != null && libro.getVisibilidad())
                    .filter(libro -> libro.getStock() != null && libro.getStock() > 0)
                    .findFirst()
                    .orElse(null);
            }
            
            return null;
        } catch (Exception e) {
            log.error("Error al validar libro con ID {}: {}", libroId, e.getMessage());
            return null;
        }
    }
    
    public boolean existeYEstaDisponible(Long libroId) {
        LibroDto libro = validarLibro(libroId);
        return libro != null;
    }
}