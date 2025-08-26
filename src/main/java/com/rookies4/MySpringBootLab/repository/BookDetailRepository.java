package com.rookies4.MySpringBootLab.repository;

import com.rookies4.MySpringBootLab.entity.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}