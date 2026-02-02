package relatos_de_papel.api_rest_buscador.data.repository;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import relatos_de_papel.api_rest_buscador.data.model.Libro;
import relatos_de_papel.api_rest_buscador.data.utils.Consts;
import relatos_de_papel.api_rest_buscador.data.utils.SearchCriteria;
import relatos_de_papel.api_rest_buscador.data.utils.SearchOperation;
import relatos_de_papel.api_rest_buscador.data.utils.SearchStatement;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class LibroRepository {

    //CONSTRUCTOR - @RequiredArgsConstructor
    private final LibroJpaRepository repository;

    public List<Libro> getLibros() {
        return this.repository.findAll();
    }

    public Libro getById(Long id) {return repository.findById(id).orElse(null);}

    public Libro save(Libro libro) {
        return this.repository.save(libro);
    }

    public void deleteById(Long id) {repository.deleteById(id);}

    public void delete(Libro libro) {
        this.repository.delete(libro);
    }


    //
//    | Operación            | SQL equivalente |
//            | -------------------- | --------------- |
//            | `GREATER_THAN`       | `>`             |
//            | `LESS_THAN`          | `<`             |
//            | `GREATER_THAN_EQUAL` | `>=`            |
//            | `LESS_THAN_EQUAL`    | `<=`            |
//            | `EQUAL`              | `=`             |
//            | `NOT_EQUAL`          | `<>`            |
//            | `MATCH`              | `LIKE %valor%`  |
//            | `MATCH_END`          | `LIKE valor%`   |


    public List<Libro> search(String titulo, String autor, LocalDate fecha_publicacion, String categoria, String isbn, Integer valoracion, Boolean visibilidad){
        SearchCriteria<Libro> specific = new SearchCriteria();
        if (StringUtils.isNotBlank(titulo)) {
            specific.add(new SearchStatement(Consts.TITULO,titulo, SearchOperation.MATCH));
        }

        if (StringUtils.isNotBlank(autor)) {
            specific.add(new SearchStatement(Consts.AUTOR, autor, SearchOperation.MATCH));
        }

        if (fecha_publicacion != null) {
            specific.add(new SearchStatement(
                    Consts.FECHA_PUBLICACION,
                    fecha_publicacion,
                    SearchOperation.EQUAL
            ));
        }

        if (StringUtils.isNotBlank(categoria)) {
            specific.add(new SearchStatement(Consts.CATEGORIA, categoria, SearchOperation.EQUAL));
        }

        if (StringUtils.isNotBlank(isbn)) {
            specific.add(new SearchStatement(Consts.ISBN, isbn, SearchOperation.EQUAL));
        }

        if (valoracion != null) {
            specific.add(new SearchStatement(Consts.VALORACION, valoracion, SearchOperation.GREATER_THAN_EQUAL));
        }

        //
        if (visibilidad != null) {
            specific.add(new SearchStatement(Consts.VISIBILIDAD, visibilidad, SearchOperation.EQUAL));
        } else {
            // por defecto, nunca devolver libros ocultos
            specific.add(new SearchStatement(Consts.VISIBILIDAD, true, SearchOperation.EQUAL));
        }

        return this.repository.findAll(specific);

    }




}
