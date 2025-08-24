package com.rookies4.MySpringBootLab.service;

import com.rookies4.MySpringBootLab.dto.BookDTO;
import com.rookies4.MySpringBootLab.entity.Book;
import com.rookies4.MySpringBootLab.exception.BusinessException;
import com.rookies4.MySpringBootLab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public BookDTO.BookResponse createBook(BookDTO.BookCreateRequest request) {
        Book book = request.toEntity();
        Book savedBook = bookRepository.save(book);
        return new BookDTO.BookResponse(savedBook);
    }

    @Override
    public List<BookDTO.BookResponse> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO.BookResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO.BookResponse findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));
        return new BookDTO.BookResponse(book);
    }

    @Override
    public BookDTO.BookResponse findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new BusinessException("Book not found with isbn: " + isbn));
        return new BookDTO.BookResponse(book);
    }

    @Override
    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));

        // [요구사항] 변경이 필요한 필드만 업데이트
        if (request.getTitle() != null) {
            existBook.setTitle(request.getTitle());
        }
        if (request.getAuthor() != null) {
            existBook.setAuthor(request.getAuthor());
        }
        if (request.getPrice() != null) {
            existBook.setPrice(request.getPrice());
        }
        if (request.getPublishDate() != null) {
            existBook.setPublishDate(request.getPublishDate());
        }
        // `save`는 별도로 호출하지 않아도 @Transactional에 의해 변경 감지(Dirty Checking)되어 DB에 반영됩니다.
        return new BookDTO.BookResponse(existBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Book not found with id: " + id));
        bookRepository.delete(book);
    }
}