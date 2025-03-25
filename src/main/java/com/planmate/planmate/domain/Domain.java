package com.planmate.planmate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드 포함

public class Domain {
    private Long id; // 메모 식별자 (PK)
    private String title; // 메모 제목
    private String comment; // 메모 내용

}