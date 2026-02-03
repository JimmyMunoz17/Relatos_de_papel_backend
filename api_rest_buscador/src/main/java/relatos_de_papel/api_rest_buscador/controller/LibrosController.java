package relatos_de_papel.api_rest_buscador.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import relatos_de_papel.api_rest_buscador.controller.model.LibroDto;
import relatos_de_papel.api_rest_buscador.data.model.Libro;
import relatos_de_papel.api_rest_buscador.service.LibrosService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j

public class LibrosController {

    private final LibrosService libroService;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> getLibros(
            @RequestHeader Map<String, String> headers,
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaPublicacion,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) Integer valoracion,
            @RequestParam(required = false) Boolean visibilidad
    ){
        log.info("headers: {}", headers);

        List<Libro> libros = libroService.getLibros(
                titulo,
                autor,
                fechaPublicacion,
                categoria,
                isbn,
                valoracion,
                visibilidad
        );

        return libros != null
                ? ResponseEntity.ok(libros)
                : ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/libros/{libroId}")
    public ResponseEntity<Libro> getLibroById(@PathVariable String libroId) {
        log.info("Request received for libro {}", libroId);
        Libro libro = libroService.getLibro(libroId);
        return libro != null ? ResponseEntity.ok(libro) : ResponseEntity.notFound().build();
    }

    @PostMapping("/libros")
    public ResponseEntity<Libro> addLibro(@RequestBody LibroDto request) {

        Libro createdLibro = libroService.createLibro(request);
        return createdLibro != null ? ResponseEntity.status(HttpStatus.CREATED).body(createdLibro) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/libros/{libroId}")
    public ResponseEntity<Libro> updateLibro(
            @PathVariable String libroId,
            @RequestBody LibroDto body
    ) {
        Libro updated = libroService.updatelibro(libroId, body);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/libros/{libroId}")
    public ResponseEntity<Libro> patchLibro(
            @PathVariable String libroId,
            @RequestBody String patchBody
    ) {
        Libro patched = this.libroService.updateLibro(libroId, patchBody);
        return patched != null ? ResponseEntity.ok(patched) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/libros/{libroId}")
    public ResponseEntity<Void> deleteLibro(@PathVariable String libroId) {

        Boolean removed = libroService.removeLibro(libroId);
        return Boolean.TRUE.equals(removed) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
    
    @PutMapping("/libros/{libroId}/stock")
    public ResponseEntity<Libro> updateStock(
            @RequestHeader Map<String, String> headers,
            @PathVariable String libroId,
            @RequestParam Integer cantidad
    ) {
        log.info("headers: {}, actualizando stock del libro ID: {} con cantidad: {}", headers, libroId, cantidad);
        
        Libro updated = libroService.updateStock(libroId, cantidad);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
}
