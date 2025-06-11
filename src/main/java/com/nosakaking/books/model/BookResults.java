package com.nosakaking.books.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookResults(
    @JsonAlias("title") String Title,
    @JsonAlias("authors")
    List<AuthorData> authors,
    @JsonAlias("languages") List<String> languages,
    @JsonAlias("copyright") Boolean copyright,
    @JsonAlias("media_type") String mediaType,
    @JsonAlias("download_count") Integer downloadCount
        ) {
}
