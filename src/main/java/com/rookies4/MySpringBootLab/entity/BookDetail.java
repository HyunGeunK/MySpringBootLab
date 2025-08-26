package com.rookies4.MySpringBootLab.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String language;
    private Integer pageCount;
    private String publisher;
    private String coverImageUrl;
    private String edition;

    // 1:1 연관관계 설정 (주인, 외래키 관리)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true) // book_id 컬럼으로 외래키 생성
    private Book book;
}