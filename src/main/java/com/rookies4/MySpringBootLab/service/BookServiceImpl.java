package com.rookies4.MySpringBootLab.service;

import com.rookies4.MySpringBootLab.dto.BookDTO;
import com.rookies4.MySpringBootLab.entity.Book;
import com.rookies4.MySpringBootLab.entity.BookDetail;
import com.rookies4.MySpringBootLab.exception.BusinessException;
import com.rookies4.MySpringBootLab.exception.ErrorCode;
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
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }
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
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ID: " + id));
        return new BookDTO.BookResponse(book);
    }

    @Override
    public BookDTO.BookResponse findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbnWithBookDetail(isbn)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ISBN: " + isbn));
        return new BookDTO.BookResponse(book);
    }

    @Override
    public List<BookDTO.BookResponse> findBooksByAuthor(String author) {
        return bookRepository.findByAuthorContaining(author).stream()
                .map(BookDTO.BookResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDTO.BookResponse> findBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title).stream()
                .map(BookDTO.BookResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO.BookResponse updateBook(Long id, BookDTO.BookUpdateRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ID: " + id));

        // ISBN을 변경하려고 하는데, 그 ISBN이 이미 다른 책에 존재하면 예외 발생
        if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
            throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
        }

        // Book 정보 전체 업데이트
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setPublishDate(request.getPublishDate());

        // BookDetail 정보 전체 업데이트
        BookDetail detail = book.getBookDetail();
        BookDTO.BookDetailUpdateRequest detailRequest = request.getDetailRequest();
        detail.setDescription(detailRequest.getDescription());
        detail.setLanguage(detailRequest.getLanguage());
        detail.setPageCount(detailRequest.getPageCount());
        detail.setPublisher(detailRequest.getPublisher());
        detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
        detail.setEdition(detailRequest.getEdition());

        return new BookDTO.BookResponse(book);
    }

    @Override
    @Transactional
    public BookDTO.BookResponse patchBook(Long id, BookDTO.BookPatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ID: " + id));

        // Book 정보 부분 업데이트
        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getAuthor() != null) book.setAuthor(request.getAuthor());
        if (request.getPrice() != null) book.setPrice(request.getPrice());
        if (request.getPublishDate() != null) book.setPublishDate(request.getPublishDate());
        if (request.getIsbn() != null) {
            if (!book.getIsbn().equals(request.getIsbn()) && bookRepository.existsByIsbn(request.getIsbn())) {
                throw new BusinessException(ErrorCode.ISBN_DUPLICATE, request.getIsbn());
            }
            book.setIsbn(request.getIsbn());
        }

        // BookDetail 정보 부분 업데이트
        if (request.getDetailRequest() != null) {
            BookDTO.BookDetailPatchRequest detailRequest = request.getDetailRequest();
            BookDetail detail = book.getBookDetail();
            if (detailRequest.getDescription() != null) detail.setDescription(detailRequest.getDescription());
            if (detailRequest.getLanguage() != null) detail.setLanguage(detailRequest.getLanguage());
            if (detailRequest.getPageCount() != null) detail.setPageCount(detailRequest.getPageCount());
            if (detailRequest.getPublisher() != null) detail.setPublisher(detailRequest.getPublisher());
            if (detailRequest.getCoverImageUrl() != null) detail.setCoverImageUrl(detailRequest.getCoverImageUrl());
            if (detailRequest.getEdition() != null) detail.setEdition(detailRequest.getEdition());
        }

        return new BookDTO.BookResponse(book);
    }

    @Override
    @Transactional
    public BookDTO.BookResponse patchBookDetail(Long id, BookDTO.BookDetailPatchRequest request) {
        Book book = bookRepository.findByIdWithBookDetail(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ID: " + id));

        BookDetail detail = book.getBookDetail();
        if (request.getDescription() != null) detail.setDescription(request.getDescription());
        if (request.getLanguage() != null) detail.setLanguage(request.getLanguage());
        if (request.getPageCount() != null) detail.setPageCount(request.getPageCount());
        if (request.getPublisher() != null) detail.setPublisher(request.getPublisher());

        if (request.getCoverImageUrl() != null) detail.setCoverImageUrl(request.getCoverImageUrl());

        if (request.getEdition() != null) detail.setEdition(request.getEdition());

        return new BookDTO.BookResponse(book);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.BOOK_NOT_FOUND, "ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}