package com.rookies4.MySpringBootLab.repository;

import com.rookies4.MySpringBootLab.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    // JPQL을 사용하여 Fetch Join으로 Book과 BookDetail을 함께 조회
    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.id = :id")
    Optional<Book> findByIdWithBookDetail(@Param("id") Long id);

    @Query("SELECT b FROM Book b JOIN FETCH b.bookDetail WHERE b.isbn = :isbn")
    Optional<Book> findByIsbnWithBookDetail(@Param("isbn") String isbn);

    // 저자 이름으로 검색 (Containing: 부분 일치)
    List<Book> findByAuthorContaining(String author);

    // 제목으로 검색
    List<Book> findByTitleContaining(String title);

    // ISBN 중복 확인
    boolean existsByIsbn(String isbn);
}