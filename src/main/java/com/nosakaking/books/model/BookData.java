package com.nosakaking.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
    @JsonAlias("count") Integer count,
    @JsonAlias("next") String next,
    @JsonAlias("previous") String previous,
    @JsonAlias("results")
    List<BookResults> books
        ) {
}
