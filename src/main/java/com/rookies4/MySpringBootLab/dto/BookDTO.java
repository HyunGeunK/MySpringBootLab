package com.rookies4.MySpringBootLab.dto;

import com.rookies4.MySpringBootLab.entity.Book;
import com.rookies4.MySpringBootLab.entity.BookDetail;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class BookDTO {

    // ================================================================
    // A. 요청(Request) DTOs
    // ================================================================

    /**
     * 1. 책 생성을 위한 DTO (POST /api/books)
     */
    @Getter
    @Setter
    public static class BookCreateRequest {
        @NotBlank(message = "Title is mandatory")
        private String title;

        @NotBlank(message = "Author is mandatory")
        private String author;

        @NotBlank(message = "ISBN is mandatory")
        @Pattern(regexp = "^(?=(?:\\D*\\d){13}$)[0-9-]+$", message = "Invalid ISBN format")
        private String isbn;

        @NotNull(message = "Price is mandatory")
        @PositiveOrZero(message = "Price must be positive or zero")
        private Integer price;

        @NotNull(message = "Publish date is mandatory")
        @FutureOrPresent(message = "Publish date must be today or in the future")
        private LocalDate publishDate;

        @Valid // 중첩된 객체도 유효성 검사를 진행
        @NotNull(message = "Book detail is mandatory")
        private BookDetailCreateRequest detailRequest;

        // DTO를 Book Entity로 변환
        public Book toEntity() {
            Book book = new Book();
            book.setTitle(this.title);
            book.setAuthor(this.author);
            book.setIsbn(this.isbn);
            book.setPrice(this.price);
            book.setPublishDate(this.publishDate);
            book.setBookDetail(this.detailRequest.toEntity());
            return book;
        }
    }

    /**
     * 1-1. 책 상세 정보 생성용 DTO
     */
    @Getter
    @Setter
    public static class BookDetailCreateRequest {
        private String description;
        private String language;
        @PositiveOrZero(message = "Page count must be positive or zero")
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        // DTO를 BookDetail Entity로 변환
        public BookDetail toEntity() {
            BookDetail detail = new BookDetail();
            detail.setDescription(this.description);
            detail.setLanguage(this.language);
            detail.setPageCount(this.pageCount);
            detail.setPublisher(this.publisher);
            detail.setCoverImageUrl(this.coverImageUrl);
            detail.setEdition(this.edition);
            return detail;
        }
    }

    /**
     * 2. 책 전체 수정(PUT)용 DTO (PUT /api/books/{id})
     */
    @Getter
    @Setter
    public static class BookUpdateRequest {
        @NotBlank
        private String title;
        @NotBlank
        private String author;
        @NotBlank @Pattern(regexp = "^(?=(?:\\D*\\d){13}$)[0-9-]+$", message = "Invalid ISBN format")
        private String isbn;
        @NotNull @PositiveOrZero
        private Integer price;
        @NotNull @FutureOrPresent
        private LocalDate publishDate;
        @Valid @NotNull
        private BookDetailUpdateRequest detailRequest;
    }

    /**
     * 2-1. 책 상세 정보 전체 수정(PUT)용 DTO
     */
    @Getter
    @Setter
    public static class BookDetailUpdateRequest {
        private String description;
        private String language;
        @PositiveOrZero
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }

    /**
     * 3. 책 부분 수정(PATCH)용 DTO (PATCH /api/books/{id})
     */
    @Getter
    @Setter
    public static class BookPatchRequest {
        private String title;
        private String author;
        @Pattern(regexp = "^(?=(?:\\D*\\d){13}$)[0-9-]+$", message = "Invalid ISBN format")
        private String isbn;
        @PositiveOrZero
        private Integer price;
        @FutureOrPresent
        private LocalDate publishDate;
        @Valid
        private BookDetailPatchRequest detailRequest;
    }

    /**
     * 4. 책 상세 정보 부분 수정(PATCH)용 DTO (PATCH /api/books/{id}/detail)
     */
    @Getter
    @Setter
    public static class BookDetailPatchRequest {
        private String description;
        private String language;
        @PositiveOrZero
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;
    }


    // ================================================================
    // B. 응답(Response) DTOs
    // ================================================================

    /**
     * 5. API 응답용 DTO
     */
    @Getter
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;
        private BookDetailResponse detailResponse;

        // Entity를 Response DTO로 변환하는 생성자
        public BookResponse(Book book) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.author = book.getAuthor();
            this.isbn = book.getIsbn();
            this.price = book.getPrice();
            this.publishDate = book.getPublishDate();
            if (book.getBookDetail() != null) {
                this.detailResponse = new BookDetailResponse(book.getBookDetail());
            }
        }
    }

    /**
     * 5-1. 책 상세 정보 응답용 DTO
     */
    @Getter
    public static class BookDetailResponse {
        private Long id;
        private String description;
        private String language;
        private Integer pageCount;
        private String publisher;
        private String coverImageUrl;
        private String edition;

        public BookDetailResponse(BookDetail bookDetail) {
            this.id = bookDetail.getId();
            this.description = bookDetail.getDescription();
            this.language = bookDetail.getLanguage();
            this.pageCount = bookDetail.getPageCount();
            this.publisher = bookDetail.getPublisher();
            this.coverImageUrl = bookDetail.getCoverImageUrl();
            this.edition = bookDetail.getEdition();
        }
    }
}