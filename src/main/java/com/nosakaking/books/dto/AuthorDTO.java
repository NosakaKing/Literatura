package com.nosakaking.books.dto;

public record AuthorDTO(
        String name,
        Integer birthYear,
        Integer deathYear
) {
}
