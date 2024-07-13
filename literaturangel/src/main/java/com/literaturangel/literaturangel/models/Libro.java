package com.literaturangel.literaturangel.models;


import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "libros")
@Data
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private double numeroDescargas;

    @ManyToOne(fetch = FetchType.EAGER)
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idioma = Idioma.fromString(datosLibro.idiomas().get(0));
        this.numeroDescargas = datosLibro.numeroDescargas();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Libro: ").append(titulo)
                .append(" | Id: ").append(id)
                .append(" | Idioma: ").append(idioma)
                .append(" | Numero de Descargas: ").append(numeroDescargas)
                .append("\n")
                .append(autor);
        return sb.toString();
    }
}