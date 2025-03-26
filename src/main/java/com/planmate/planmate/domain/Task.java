package com.planmate.planmate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든필드 포함

public class Task {
    private Long id; // 메모 식별자
    private String title; // 메모 제목
    private String comment; // 메모 내용
    private String author; // 작성자명
    private String password; // 비밀번호
    private LocalDateTime createdAt; //생성시간
    private LocalDateTime modifiedAt; // 수정시간

    // 저장용 생성자(id 없이 생성)
    public Task(String title, String comment, String author, String password) {
        this.title = title;
        this.comment = comment;
        this.author = author;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
}
