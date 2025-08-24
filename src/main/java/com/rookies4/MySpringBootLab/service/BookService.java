package com.rookies4.MySpringBootLab.service;

import com.rookies4.MySpringBootLab.dto.BookDTO;
import java.util.List;

public interface BookService {
    BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request);
    List<BookDTO.BookResponse> findAllBooks();
    BookDTO.BookResponse findBookById(Long id);
    BookDTO.BookResponse findBookByIsbn(String isbn);
    BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request);
    void deleteBook(Long id);
}