package dam.code.dto;

import java.time.LocalDate;

public class LibroDTO {

    private String titulo;
    private String autor;
    private Double precio;
    private int stock;
    private LocalDate fechaPublicacion;

    public LibroDTO(String titulo, String autor, Double precio, int stock, LocalDate fechaPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
        this.fechaPublicacion = LocalDate.now();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public LocalDate getFechaPublicacion() {return fechaPublicacion;}

    @Override
    public int hashCode() {
        return titulo != null ? titulo.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if  (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LibroDTO other = (LibroDTO) obj;
        return titulo != null ? titulo.equals(other.titulo) : other.titulo == null;
    }
}
