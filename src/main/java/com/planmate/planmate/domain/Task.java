package com.planmate.planmate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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


    // 저장용 생성자(id 없이 생성)
    public Task(String title, String comment, String author, String password) {
        this.title = title;
        this.comment = comment;
        this.author = author;
        this.password = password;
    }

    public void update(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    // 제목만 수정
    public void update(String title) {
        this.title = title;
    }
}
