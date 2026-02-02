package relatos_de_papel.api_rest_buscador.controller.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LibroDto {
    private String titulo;
    private String autor;
    private String fecha_publicacion;
    private String categoria;
    private String isbn;
    private Integer valoracion;
    private Boolean visibilidad;

    public LibroDto(Boolean visibilidad, Integer valoracion, String isbn, String categoria, String fecha_publicacion, String autor, String titulo) {
        this.visibilidad = visibilidad;
        this.valoracion = valoracion;
        this.isbn = isbn;
        this.categoria = categoria;
        this.fecha_publicacion = fecha_publicacion;
        this.autor = autor;
        this.titulo = titulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Boolean getVisibilidad() {
        return visibilidad;
    }

    public Integer getValoracion() {
        return valoracion;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public String getAutor() {
        return autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setVisibilidad(Boolean visibilidad) {
        this.visibilidad = visibilidad;
    }

    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
