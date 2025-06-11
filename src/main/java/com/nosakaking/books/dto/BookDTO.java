package com.nosakaking.books.dto;

import com.nosakaking.books.model.Languages;

public record BookDTO(
        Long id,
        String title,
        Languages languages,
        boolean copyright,
        String mediaType,
        Integer downloadCount
) {
}
