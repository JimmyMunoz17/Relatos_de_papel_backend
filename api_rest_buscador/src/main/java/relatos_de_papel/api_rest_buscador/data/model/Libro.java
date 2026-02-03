package relatos_de_papel.api_rest_buscador.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import relatos_de_papel.api_rest_buscador.controller.model.LibroDto;
import relatos_de_papel.api_rest_buscador.data.utils.Consts;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="libro")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = Consts.TITULO)
    private String titulo;

    @Column( name = Consts.AUTOR)
    private String autor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column( name = Consts.FECHA_PUBLICACION)
    private LocalDate fecha_publicacion;

    @Column( name = Consts.CATEGORIA)
    private String categoria;

    @Column( name = Consts.ISBN)
    private String isbn;

    @Column( name = Consts.VALORACION)
    private Integer valoracion;

    @Column( name = Consts.VISIBILIDAD)
    private Boolean visibilidad;
    
    @Column( name = "stock")
    private Integer stock;



    public void updateLibro(LibroDto libroDto){
        this.titulo = libroDto.getTitulo();
        this.autor = libroDto.getAutor();
        this.fecha_publicacion = LocalDate.parse(libroDto.getFecha_publicacion());
        this.categoria = libroDto.getCategoria();
        this.isbn = libroDto.getIsbn();
        this.valoracion = libroDto.getValoracion();
        this.visibilidad = libroDto.getVisibilidad();
        if (libroDto.getStock() != null) {
            this.stock = libroDto.getStock();
        }
    }

}
