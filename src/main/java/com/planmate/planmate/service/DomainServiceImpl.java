package com.planmate.planmate.service;

import com.planmate.planmate.domain.Task;
import com.planmate.planmate.dto.DomainRequestDto;
import com.planmate.planmate.dto.DomainResponseDto;
import com.planmate.planmate.repository.DomainRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional
    @Override
    // 메모
    public DomainResponseDto updateTask(Long id, String title, String comment) {
        // 필수값 확인
        if (title == null || comment == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목과 내용은 필수입니다.");
        }
        int updatedRow = domainRepository.updateTask(id, title, comment);

        // 수정된 행이 없다면 에러
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정 대상이 없습니다. ID: " + id);
        }

        // 수정된 결과 다시 조회하여 반환
        Task updatedTask = domainRepository.findTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회 실패. ID: " + id));

        return new DomainResponseDto(updatedTask);

    }
    @Transactional
    @Override
    public DomainResponseDto updateTitle(Long id, String title) {
        // 제목만 있어야 하고, 내용은 null이어야 함
        if (title == null ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목은 필수입니다!");
        }

        int updatedRow = domainRepository.updateTitle(id, title);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정 대상이 없습니다. ID: " + id);
        }

        Task updatedTask = domainRepository.findTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회 실패. ID: " + id));

        return new DomainResponseDto(updatedTask);
    }
    @Override
    public void delete(Long id) {
        int deletedRow = domainRepository.delete(id);
        // 삭제된 행이 없으면 에러
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제 대상이 없습니다. ID: " + id);
        }
    }
}

