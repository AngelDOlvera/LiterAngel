package com.literaturangel.literaturangel.principal;

import com.literaturangel.literaturangel.models.*;
import com.literaturangel.literaturangel.repository.AutorRepository;
import com.literaturangel.literaturangel.repository.LibroRepository;
import com.literaturangel.literaturangel.service.ConsumoApi;
import com.literaturangel.literaturangel.service.ConversorDeDatos;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoApi consumoApi = new ConsumoApi();
    private final ConversorDeDatos conversorDeDatos = new ConversorDeDatos();
    private final Scanner scanner = new Scanner(System.in);

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public Principal(AutorRepository autor, LibroRepository libro) {
        this.autorRepository = autor;
        this.libroRepository = libro;
    }

    public void muestraElMenu() {
        int opc = -1;
        while (opc != 0) {
            String menu = """
                    **********************************************
                    1 - Busca tu libro favorito
                    2 - Revisa tus libros buscados
                    3 - Mira los autores que has buscado
                    4 - Buscar autores con vida de un año
                    5 - Busca libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opc = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\nOpción no válida\n");
                scanner.nextLine();  // Clear buffer
                continue;
            }

            switch (opc) {
                case 1 -> buscarLibrosPorTitulo();
                case 2 -> muestraLibrosRegistrados();
                case 3 -> listaAutoresBuscados();
                case 4 -> listaAutoresVivosEnAño();
                case 5 -> listarLibrosPorIdiomas();
                case 0 -> {
                    System.out.println("Saliendo...\n");
                    System.out.println("Gracias por usar LiteraturAngel");
                }
                default -> System.out.println("\nPor favor ingrese una opción válida\n");
            }
        }
    }

    private Datos getDatosLibro() {
        System.out.println("Por favor ingresa el nombre del libro que deseas buscar");
        String nombre = scanner.nextLine();
        String tituloCodificado = URLEncoder.encode(nombre, StandardCharsets.UTF_8);
        String url = URL_BASE + "?search=" + tituloCodificado.replace(" ", "+");
        String json = consumoApi.getData(url);
        return conversorDeDatos.obtenerDatos(json, Datos.class);
    }

    private void buscarLibrosPorTitulo() {
        Datos datos = getDatosLibro();
        if (datos != null && !datos.resultados().isEmpty()) {
            DatosLibro datosLibroEncontrado = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibroEncontrado.autor().get(0);

            Autor autor = autorRepository.findByNombreIgnoreCase(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

            if (libroRepository.findByTituloIgnoreCase(datosLibroEncontrado.titulo()).isPresent()) {
                System.out.println("\nEl libro ya está registrado, pruebe con otro libro\n");
            } else {
                Libro libroEncontrado = new Libro(datosLibroEncontrado);
                libroEncontrado.setAutor(autor);
                libroRepository.save(libroEncontrado);
                System.out.println(libroEncontrado);
                System.out.println("\nLibro registrado exitosamente\n");
            }

        } else {
            System.out.println("\nLibro no encontrado, pruebe con otro libro\n");
        }
    }

    private void muestraLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\nNo hay libros registrados");
        } else {
            libros.forEach(libro -> {
                System.out.println("-----------------");
                System.out.println(libro);
                System.out.println("-----------------");
            });
        }
    }

    private void listaAutoresBuscados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listaAutoresVivosEnAño() {
        System.out.println("Ingrese el año en el cual desea buscar autores vivos");
        try {
            int año = scanner.nextInt();
            scanner.nextLine();
            List<Autor> autoresVivos = autorRepository.autores(año);
            if (autoresVivos.isEmpty()) {
                System.out.println("\nNo hay autores vivos en el año " + año);
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("\nIngresaste un dígito no compatible con un año, inténtalo de nuevo");
            scanner.nextLine();
        }
    }
    private void listarLibrosPorIdiomas() {
        System.out.println("Ingrese el idioma en el cual desea buscar los libros");
        int opc = -1;
        while (opc != 0) {
            String menuIdiomas = """
                    1 - Español
                    2 - Inglés
                    3 - Francés
                    4 - Portugués
                    0 - Salir
                    """;
            System.out.println(menuIdiomas);

            try {
                opc = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Opción no válida");
                scanner.nextLine();  // Clear buffer
                continue;
            }

            switch (opc) {
                case 1 -> listarLibrosPorIdioma(Idioma.ES);
                case 2 -> listarLibrosPorIdioma(Idioma.EN);
                case 3 -> listarLibrosPorIdioma(Idioma.FR);
                case 4 -> listarLibrosPorIdioma(Idioma.PT);
                case 0 -> {}
                default -> System.out.println("\nOpcion no disponible, Por favor eliga una opcion valida\n");
            }
        }
    }

    private void listarLibrosPorIdioma(Idioma idioma) {
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("\nNo hay registro de libros en " + idioma.name());
        } else {
            libros.forEach(System.out::println);
        }
    }
}