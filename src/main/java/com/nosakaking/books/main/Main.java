package com.nosakaking.books.main;

import com.nosakaking.books.dto.AuthorDTO;
import com.nosakaking.books.dto.BookDTO;
import com.nosakaking.books.model.*;
import com.nosakaking.books.repository.AuthorRepository;
import com.nosakaking.books.repository.BookRepository;
import com.nosakaking.books.service.APIConsumer;
import com.nosakaking.books.service.BookService;
import com.nosakaking.books.service.DataConverter;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private final APIConsumer apiConsumer = new APIConsumer();
    private final DataConverter dataConverter = new DataConverter();
    Dotenv dotenv = Dotenv.load();
    private String json;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookService service;
    Scanner scanner = new Scanner(System.in);
    private final String urlBase = dotenv.get("GUTENDEX_API_BASE_URL");

    public Main(BookRepository bookRepository, AuthorRepository authorRepository, BookService service) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.service = service;
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            String menu = """
                    1. Mostrar Json de la API consumida
                    2. Convertir API en un objeto
                    3. Buscar libro
                    4. Almacenar Libro en base de datos
                    5. Listar libros
                    6. Buscar libro por Autor
                    7, Listar Autores
                    8. Buscar Autor por fecha
                    0. Salir del sistema
                    """;
            System.out.println(menu);
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    showAPIConsumer();
                    break;
                case 2:
                    showConverterJson();
                    break;
                case 3:
                    getDataBook();
                    break;
                case 4:
                    saveBooks();
                    break;
                case 5:
                    showBooks();
                    break;
                case 6:
                    showBookByAuthor();
                    break;
                case 7:
                    showAuthors();
                    break;
                case 8:
                    showAuthorByBirthYear();
                case 9:
                    searchBookByLanguages();
                    break;
            }
        }
    }

    private void showAPIConsumer() {
        json = apiConsumer.gettingData(urlBase);
        System.out.println(json);
    }

    private void showConverterJson() {
        showAPIConsumer();
        var data = dataConverter.gettingData(json, BookData.class);
        data.books().stream().limit(5).forEach(System.out::println);
        System.out.println(data);
    }


    private Optional<BookResults> getDataBook() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nameBook = scanner.nextLine();
        var json = apiConsumer.gettingData(urlBase + "?search=" + nameBook.replace(" ", "+") + "&&languages=es");
        BookData data = dataConverter.gettingData(json, BookData.class);
        Optional<BookResults> booksearched = data.books().stream().findFirst();
        if (booksearched.isPresent()) {
            System.out.println("Libro Encontrado ");
            System.out.println(booksearched.get());

        } else {
            System.out.println("Libro no encontrado");
        }

        return booksearched;
    }

    private void saveBooks() {
        Optional<BookResults> data = getDataBook();
        if (data.isEmpty()) {
            return;
        }
        BookResults bookResults = data.get();
        Book book = new Book(bookResults);
        List<Author> bookAuthors = new ArrayList<>();
        for (AuthorData a : bookResults.authors()) {
            Optional<Author> existingAuthor = authorRepository.findByNameAndBirthYear(a.name(), a.birthYear());
            Author author = existingAuthor.orElseGet(() -> {
                Author newAuthor = new Author(a);
                return authorRepository.save(newAuthor);
            });
            bookAuthors.add(author);
        }
        book.setAuthors(bookAuthors);
        bookRepository.save(book);
        System.out.println("Libro y relaciones guardadas correctamente.");
    }


    private void showBooks() {
        var books = service.findAllBooks();
        if (books.isEmpty()) {
            System.out.println("No hay libros en la base de datos.");
        } else {
            System.out.println("Libros almacenados:");
            books.forEach(System.out::println);
        }
    }

    private void showAuthors() {
        service.findAllAuthors().forEach(System.out::println);
    }

    private void showAuthorByBirthYear() {
        System.out.println("Ingrese un año para buscar al autor");
        int year = scanner.nextInt();
        List<AuthorDTO>  authorList = service.findAliveInYear(year);
        if (authorList.isEmpty()) {
            System.out.println("No hay autores en la base de datos.");
        }else {
            authorList.forEach(System.out::println);
        }
    }

    private void showBookByAuthor() {
        System.out.println("Escribe el author que deseas buscar");
        var author = scanner.nextLine();
        List<BookDTO> books = service.findBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No hay libros en la base de datos.");
        }else {
            books.forEach(System.out::println);
        }
    }

    private void searchBookByLanguages() {
        System.out.println("Escribe el language que deseas buscar");
        var languages = scanner.nextLine();
        service.findBookByLanguage(languages).forEach(System.out::println);
    }

}
