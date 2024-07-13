package com.literaturangel.literaturangel.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
@Data
@NoArgsConstructor
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer fechaNacimiento;
    private Integer fechaMuerte;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaMuerte = datosAutor.fechaMuerte();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
                sb.append("Autor: ").append(nombre).append("\n")
                .append("Fecha de nacimiento: ").append(fechaNacimiento).append("\n")
                .append("Fecha de muerte: ").append(fechaMuerte);
        return sb.toString();
    }

    public String toStringWithBooks() {
        StringBuilder sb = new StringBuilder(toString());
        sb.append("Libros: ").append("\n");
        for (Libro libro : libros) {
            sb.append("- ").append(libro.getTitulo()).append("\n");
        }
        return sb.toString();
    }
}
