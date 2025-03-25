package com.planmate.planmate.repository;

import com.planmate.planmate.domain.Domain;
import com.planmate.planmate.dto.DomainResponseDto;
import java.util.List;
import java.util.Optional;

public interface DomainRepository {

    Domain saveDomain(Domain domain); //  저장
    List<Domain> findAllDomains(); // 전체 일정 조회
    Optional<Domain> findDomainById(Long id); // 특정일정조회

    int updateComment(Long id, String title, String comment); // 전체 수정
    int updateTitle(Long id, String title); // 제목만 수정
    int deleteTask(Long id); // 메모 삭제
}
