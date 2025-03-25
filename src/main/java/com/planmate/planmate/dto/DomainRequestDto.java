package com.planmate.planmate.dto;

import com.planmate.planmate.domain.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
//요청 DTO (입력한 데이터 담는 DTO)
public class DomainRequestDto {
    private Long id;
    private String title;
    private String comment;
    private String author;
    private String password;

}

