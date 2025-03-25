package com.planmate.planmate.service;

import com.planmate.planmate.domain.Task;
import com.planmate.planmate.dto.DomainRequestDto;
import com.planmate.planmate.dto.DomainResponseDto;
import com.planmate.planmate.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // 생성자 자동생성
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    //의존성주입
//    public DomainServiceImpl(DomainRepository domainRepository) {
//        this.domainRepository = domainRepository;
//    }

    @Override
    // 일정저장
    public DomainResponseDto saveTasks(DomainRequestDto requestDto) {
        Task task = new Task(
                requestDto.getTitle(),
                requestDto.getComment(),
                requestDto.getAuthor(),
                requestDto.getPassword()
        );

        Task saveTask = domainRepository.saveTask(task);
        return new DomainResponseDto(saveTask);
    }

    @Override
    //전체 일정 조회 리스트 반환
    public List<DomainResponseDto> findAllTasks() {
        return domainRepository.findAllTask();
    }

    @Override
    //단일 조회
    //클라이언트 응답
    public DomainResponseDto findTaskById(Long id) {
        Task task = findTaskByIdOrElseThrow(id);
        return new DomainResponseDto(task);
    }

    //예외처리
    public Task findTaskByIdOrElseThrow(Long id) {
        return domainRepository.findTaskById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID의 일정이 존재하지 않습니다: " + id));
    }

    @Override
    public DomainResponseDto updateTask(Long id, DomainRequestDto requestDto) {
        Task task = findTaskByIdOrElseThrow(id);
        task.update(requestDto.getTitle(), requestDto.getComment());
        domainRepository.saveTask(task);
        return new DomainResponseDto(task);
    }

}
