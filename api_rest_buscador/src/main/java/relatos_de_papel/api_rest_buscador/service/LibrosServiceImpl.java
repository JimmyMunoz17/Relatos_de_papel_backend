package relatos_de_papel.api_rest_buscador.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import relatos_de_papel.api_rest_buscador.controller.model.LibroDto;
import relatos_de_papel.api_rest_buscador.data.model.Libro;
import relatos_de_papel.api_rest_buscador.data.repository.LibroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class LibrosServiceImpl implements LibrosService {

    @Autowired
    private LibroRepository repository;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Libro> getLibros(
            String titulo,
            String autor,
            LocalDate fechaPublicacion,
            String categoria,
            String isbn,
            Integer valoracion,
            Boolean visibilidad
    ){
        if(StringUtils.hasLength(titulo)
                || StringUtils.hasLength(autor)
                || fechaPublicacion != null
                || StringUtils.hasLength(categoria)
                || StringUtils.hasLength(isbn)
                || valoracion != null
                || visibilidad != null){
            return repository.search(titulo, autor, fechaPublicacion, categoria, isbn, valoracion, visibilidad);
        }
        List<Libro> libros = repository.getLibros();
        return libros.isEmpty() ? null: libros;
    }

    @Override
    public Libro getLibro(String libroId){
        return repository.getById(Long.valueOf(libroId));
    }

    @Override
    public Boolean removeLibro(String libroId){
        Libro libro = repository.getById(Long.valueOf(libroId));
        if (libro != null) {
            repository.delete(libro);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Libro createLibro(LibroDto request) {
        if (request == null
                || !StringUtils.hasLength(request.getTitulo())
                || !StringUtils.hasLength(request.getAutor())
                || request.getFecha_publicacion() == null
                || !StringUtils.hasLength(request.getCategoria())
                || !StringUtils.hasLength(request.getIsbn()))
        {
            return null;
        }

        LocalDate fechaPublicacion;
        try {
            fechaPublicacion = LocalDate.parse(request.getFecha_publicacion());
        } catch (Exception e) {
            log.error("Fecha inválida: {}", request.getFecha_publicacion());
            return null;
        }


        Libro libro = Libro.builder().
                titulo(request.getTitulo()).
                autor(request.getAutor()).
                fecha_publicacion(fechaPublicacion).
                categoria(request.getCategoria()).
                isbn(request.getIsbn()).
                valoracion(request.getValoracion()).
                visibilidad(request.getVisibilidad() != null
                        ?request.getVisibilidad()
                        : Boolean.TRUE).
                stock(request.getStock() != null
                        ? request.getStock()
                        : 0).
                build();
        return repository.save(libro);

    }

    @Override
    public Libro updateLibro(String libroId, String request) {
        Libro libro = repository.getById(Long.valueOf(libroId));
        if (libro != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(request));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(libro)));
                Libro patched =  objectMapper.treeToValue(target, Libro.class);
                repository.save(patched);
                return patched;
            } catch (JsonPatchException | JsonProcessingException e) {
                log.error("Error updating Libro {}", libroId, e);
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Libro updatelibro(String libroId, LibroDto updateRequest) {
        Libro libro = repository.getById(Long.valueOf(libroId));
        if (libro != null) {
            libro.updateLibro(updateRequest);
            repository.save(libro);
            return libro;
        } else {
            return null;
        }
    }
    
    @Override
    public Libro updateStock(String libroId, Integer cantidad) {
        Libro libro = repository.getById(Long.valueOf(libroId));
        if (libro != null) {
            Integer stockActual = libro.getStock() != null ? libro.getStock() : 0;
            Integer nuevoStock = Math.max(0, stockActual - cantidad);
            libro.setStock(nuevoStock);
            repository.save(libro);
            log.info("Stock actualizado para libro ID {}: {} -> {}", libroId, stockActual, nuevoStock);
            return libro;
        } else {
            log.error("Libro no encontrado con ID: {}", libroId);
            return null;
        }
    }

}
