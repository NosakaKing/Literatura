package com.nosakaking.books.service;

import com.nosakaking.books.dto.AuthorDTO;
import com.nosakaking.books.dto.BookDTO;
import com.nosakaking.books.model.Author;
import com.nosakaking.books.model.Book;
import com.nosakaking.books.model.Languages;
import com.nosakaking.books.repository.AuthorRepository;
import com.nosakaking.books.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDTO> converterData (List<Book> book) {
        return book.stream().map(s -> new BookDTO(s.getId(), s.getTitle(), s.getLanguages(), s.isCopyright(), s.getMediaType(), s.getDownloadCount())).collect(Collectors.toList());
    }

    public List<AuthorDTO> converterAuthors (List<Author> authors) {
        return authors.stream().map(a -> new AuthorDTO(a.getName(), a.getBirthYear(), a.getDeathYear())).collect(Collectors.toList());
    }
    public List<BookDTO> findAllBooks() {
        return converterData(bookRepository.findAll());
    }

    public List<BookDTO> findBooksByAuthor (String author) {
        return converterData(bookRepository.findByAuthor(author));
    }

    public List<AuthorDTO> findAllAuthors() {
        return converterAuthors(authorRepository.findAll());
    }

    public List<AuthorDTO> findAliveInYear(int year) {
        return converterAuthors(authorRepository.findAliveInYear(year));
    }

    public List<BookDTO> findBookByLanguage(String languageText) {
        Languages language = Languages.fromLanguageSpanish(languageText);
        return converterData(bookRepository.findByLanguages(language));
    }
}
