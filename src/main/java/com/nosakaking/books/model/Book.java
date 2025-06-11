package com.nosakaking.books.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    @Enumerated(EnumType.STRING)
    private Languages languages;
    private boolean copyright;
    private String mediaType;
    private Integer downloadCount;

    public Book(BookResults bookResults) {
        this.title = bookResults.Title();
        this.mediaType = bookResults.mediaType();
        this.copyright = bookResults.copyright();
        this.downloadCount = bookResults.downloadCount();
        this.languages = Languages.fromLanguage(bookResults.languages().getFirst());
    }

}
