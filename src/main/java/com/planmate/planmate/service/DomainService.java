package com.planmate.planmate.service;

import com.planmate.planmate.domain.Task;
import com.planmate.planmate.dto.DomainRequestDto;
import com.planmate.planmate.dto.DomainResponseDto;

import java.util.List;

public interface DomainService {

    DomainResponseDto saveTasks(DomainRequestDto requestDto);
    List<DomainResponseDto> findAllTasks();
    DomainResponseDto findTaskById(Long id);
    Task findTaskByIdOrElseThrow(Long id);
    DomainResponseDto updateTask(Long id, DomainRequestDto requestDto);

}


