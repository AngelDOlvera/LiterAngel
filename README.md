# LiteraturAngel

## Descripción

**LiteraturAngel** es una aplicación de gestión de autores y libros desarrollada con **Spring Boot** y **PostgreSQL**. La aplicación permite buscar libros y autores desde una API externa, almacenar y listar libros y autores, y realizar consultas sobre autores vivos en un determinado año.

## Características

- Buscar libros por título y almacenar los resultados en la base de datos.
- Listar libros y autores almacenados en la base de datos.
- Buscar autores que estaban vivos en un año determinado.
- Consultar libros por idioma.

## Requisitos

- **Java 11** o superior
- **Spring Boot 2.6** o superior
- **PostgreSQL 13** o superior
- **Maven 3.6** o superior

## Configuración

### Clonar el Repositorio

```bash
git clone https://github.com/tu-usuario/literaturangel.git
cd literaturangel
```

Configuración de la Base de Datos
Asegúrate de tener PostgreSQL instalado y ejecutándose. Crea una base de datos para la aplicación.
```
CREATE DATABASE literaturangel;
```
Configuración de application.properties
Actualiza el archivo src/main/resources/application.properties con las credenciales de tu base de datos.
```
spring.datasource.url=jdbc:postgresql://localhost:5432/literaturangel
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
## Construir y Ejecutar la Aplicación

Menú Principal
Cuando la aplicación se ejecuta, presenta un menú con las siguientes opciones:

Buscar tu libro favorito: Permite buscar un libro por título y almacenarlo en la base de datos.

Revisar tus libros buscados: Lista todos los libros almacenados en la base de datos.

Mira los autores que has buscado: Lista todos los autores almacenados en la base de datos.

Buscar autores vivos en un año: Busca autores que estaban vivos en un año específico.

Buscar libros por idioma: Permite listar libros almacenados por idioma.

Ejemplo de Uso

Selecciona la opción 1 para buscar un libro por título.

Introduce el nombre del libro y espera a que la aplicación realice la búsqueda.

Si el libro no está registrado, se almacenará en la base de datos y se mostrará en la consola.

Puedes usar la opción 2 para revisar los libros almacenados, la opción 3 para listar los autores, y la opción 4 para buscar autores vivos en un año específico.


Contribuciones

Si deseas contribuir a este proyecto, por favor realiza un fork del repositorio y crea una nueva rama para tus modificaciones. Luego, realiza un pull request con una descripción detallada de tus cambios.
