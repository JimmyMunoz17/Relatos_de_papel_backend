package relatos_de_papel.api_rest_buscador.service;

import relatos_de_papel.api_rest_buscador.controller.model.LibroDto;
import relatos_de_papel.api_rest_buscador.data.model.Libro;

import java.time.LocalDate;
import java.util.List;

public interface LibrosService {
    List<Libro> getLibros(
            String titulo,
            String autor,
            LocalDate fechaPublicacion,
            String categoria,
            String isbn,
            Integer valoracion,
            Boolean visibilidad

    );

    Libro getLibro(String libroId);

    Boolean removeLibro(String libroId);

    Libro createLibro(LibroDto request);

    Libro updateLibro(String libroId, String updateRequest);

    Libro updatelibro(String libroId, LibroDto updateRequest);
    
    Libro updateStock(String libroId, Integer cantidad);
}

