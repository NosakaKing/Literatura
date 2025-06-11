package com.nosakaking.books;

import com.nosakaking.books.main.Main;
import com.nosakaking.books.model.Author;
import com.nosakaking.books.repository.AuthorRepository;
import com.nosakaking.books.repository.BookRepository;
import com.nosakaking.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BooksApplication implements CommandLineRunner {
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private BookService bookService;
	public static void main(String[] args) {
		SpringApplication.run(BooksApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main(bookRepository, authorRepository, bookService);
		main.showMenu();
	}
}
