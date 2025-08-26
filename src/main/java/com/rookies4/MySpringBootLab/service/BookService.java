package com.rookies4.MySpringBootLab.service;

import com.rookies4.MySpringBootLab.dto.BookDTO;
import java.util.List;

public interface BookService {
    // 생성
    BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request);

    // 조회
    List<BookDTO.BookResponse> findAllBooks();
    BookDTO.BookResponse findBookById(Long id);
    BookDTO.BookResponse findBookByIsbn(String isbn);

    // 검색
    List<BookDTO.BookResponse> findBooksByAuthor(String author);
    List<BookDTO.BookResponse> findBooksByTitle(String title);

    // 수정
    BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request);
    BookDTO.BookResponse patchBook(Long id, BookDTO.BookPatchRequest request);
    BookDTO.BookResponse patchBookDetail(Long id, BookDTO.BookDetailPatchRequest request);

    // 삭제
    void deleteBook(Long id);
}