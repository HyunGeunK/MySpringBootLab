package com.rookies4.MySpringBootLab.service;

import com.rookies4.MySpringBootLab.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Book createBook(Book book);
    List<Book> findAllBooks();
    Optional<Book> findBookById(Long id);
    Optional<Book> findBookByIsbn(String isbn);
    Book updateBook(Long id, Book bookDetails);
    void deleteBook(Long id);
}