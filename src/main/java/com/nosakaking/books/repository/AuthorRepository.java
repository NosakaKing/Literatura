package com.nosakaking.books.repository;

import com.nosakaking.books.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameAndBirthYear(String name, Integer birthYear);


    @Query("""
        SELECT a FROM Author a
        WHERE a.birthYear <= :year
        AND (a.deathYear IS NULL OR a.deathYear > :year)
    """)
    List<Author> findAliveInYear(@Param("year") int year);

}
