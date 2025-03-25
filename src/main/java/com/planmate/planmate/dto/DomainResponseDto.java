package com.planmate.planmate.dto;

import com.planmate.planmate.domain.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainResponseDto {
    private Long id;       // 메모 식별자
    private String title;  // 메모 제목
    private String comment; // 메모 내용


    public DomainResponseDto(Domain domain) {
        this.id = domain.getId();
        this.title = domain.getTitle();
        this.comment = domain.getComment();
    }

}
