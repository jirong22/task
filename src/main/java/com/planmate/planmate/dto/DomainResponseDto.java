package com.planmate.planmate.dto;

import com.planmate.planmate.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor

// 응답 DTO (클라이언트 보여줄데이터 DTO)
public class DomainResponseDto {
    private Long id;       // 메모 식별자
    private String title;  // 메모 제목
    private String comment; // 메모 내용
    private String author; //작성자
    private LocalDateTime modifiedAt;


    public DomainResponseDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.comment = task.getComment();
        this.author = task.getAuthor();
    }
}

