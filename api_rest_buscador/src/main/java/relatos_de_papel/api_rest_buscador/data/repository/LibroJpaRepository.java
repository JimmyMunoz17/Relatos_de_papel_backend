package relatos_de_papel.api_rest_buscador.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import relatos_de_papel.api_rest_buscador.data.model.Libro;

import java.util.List;

public interface LibroJpaRepository extends JpaRepository<Libro, Long>, JpaSpecificationExecutor<Libro> {

    //Query
    List<Libro> findByTitulo(String titulo);

    List<Libro> findByAutor(String autor);

    List<Libro> findByVisibilidad(Boolean visibilidad);

    List<Libro> findByTituloAndAutor(String titulo, String autor);
}
