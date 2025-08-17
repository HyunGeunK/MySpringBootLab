package com.rookies4.MySpringBootLab.repository;

import com.rookies4.MySpringBootLab.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        // given
        Book newBook = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();
        // when
        Book savedBook = bookRepository.save(newBook);
        // then
        assertNotNull(savedBook.getId());
        assertEquals("스프링 부트 입문", savedBook.getTitle());
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        // given
        String isbn = "9788956746425";
        bookRepository.save(Book.builder().title("스프링 부트 입문").author("홍길동").isbn(isbn).build());
        // when
        Book foundBook = bookRepository.findByIsbn(isbn).orElse(null);
        // then
        assertNotNull(foundBook);
        assertEquals(isbn, foundBook.getIsbn());
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        // given
        String author = "홍길동";
        bookRepository.save(Book.builder().title("스프링 부트 입문").author(author).isbn("111").build());
        bookRepository.save(Book.builder().title("자바 기초").author(author).isbn("222").build());
        // when
        List<Book> books = bookRepository.findByAuthor(author);
        // then
        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        // given
        Book savedBook = bookRepository.save(Book.builder().title("JPA 프로그래밍").author("박둘리").isbn("9788956746432").build());
        // when
        savedBook.setTitle("JPA 프로그래밍 (개정판)");
        Book updatedBook = bookRepository.save(savedBook);
        // then
        assertEquals("JPA 프로그래밍 (개정판)", updatedBook.getTitle());
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        // given
        Book savedBook = bookRepository.save(Book.builder().title("JPA 프로그래밍").author("박둘리").isbn("9788956746432").build());
        Long bookId = savedBook.getId();
        // when
        bookRepository.deleteById(bookId);
        // then
        assertFalse(bookRepository.findById(bookId).isPresent());
    }
}