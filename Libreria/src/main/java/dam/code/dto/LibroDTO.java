package dam.code.dto;

public class LibroDTO {

    private String titulo;
    private String autor;
    private Double precio;
    private int stock;

    public LibroDTO(String titulo, String autor, Double precio, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
        this.stock = stock;
    }

    public String getTitulo() {return titulo;}

    public String getAutor() {
        return autor;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }
}
