package com.rookies4.MySpringBootLab.dto;


import com.rookies4.MySpringBootLab.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {
    @Getter
    @Setter
    public static class BookCreateRequest {
        @NotBlank(message = "Title is mandatory")
        private String title;

        @NotBlank(message = "Author is mandatory")
        private String author;

        @NotBlank(message = "ISBN is mandatory")
        private String isbn;

        @NotNull(message = "Price is mandatory")
        @PositiveOrZero(message = "Price must be positive or zero")
        private Integer price;

        private LocalDate publishDate;

        // DTO를 Entity로 변환하는 메서드
        public Book toEntity() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPrice(this.price);
            book.setPublishDate(this.publishDate);
            return book;
        }
    }

    @Getter
    @Setter
    public static class BookUpdateRequest {
        // 수정 가능한 필드만 선언 (ISBN은 보통 수정하지 않음)
        private String title;
        private String author;
        private Integer price;
        private LocalDate publishDate;
    }

    @Getter
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        // Entity를 DTO로 변환하는 생성자
        public BookResponse(Book book) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.isbn = book.getIsbn();
            this.price = book.getPrice();
            this.publishDate = book.getPublishDate();
        }
    }
}
