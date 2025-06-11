package com.nosakaking.books.repository;

import com.nosakaking.books.model.Book;
import com.nosakaking.books.model.Languages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name ILIKE %:nameAuthor")
    List<Book> findByAuthor(@Param("nameAuthor") String nameAuthor);

    List<Book> findByLanguages(Languages languages);
}
