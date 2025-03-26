package com.planmate.planmate.repository;

import com.planmate.planmate.domain.Task;
import com.planmate.planmate.dto.DomainResponseDto;

import java.util.List;
import java.util.Optional;

// 인터페이스
public interface DomainRepository {


    Task saveTask(Task task); // 메모 저장

    List<DomainResponseDto> findAllTask();   // 전체 조회

    Optional<Task> findTaskById(Long id);  // 단일 일정 조회

    Optional<Task> findTaskByIdForAuth(Long id); // 비밀번호 확인

    int updateTask(Long id, String title, String comment); // 전체 수정

    int updateTitle(Long id, String title); //ID로 제목만 수정

    int delete(Long id); // 삭제
}


