package com.planmate.planmate.controller;

import com.planmate.planmate.dto.DomainRequestDto;
import com.planmate.planmate.dto.DomainResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.planmate.planmate.service.DomainService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/tasks")
public class DomainController {

    private final DomainService domainService;

    //생성자 방식 domainService 주입
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }


    @PostMapping
    // 새일정
    public ResponseEntity<DomainResponseDto> createTask(@RequestBody DomainRequestDto requestDto) {
        DomainResponseDto savedTask = domainService.saveTasks(requestDto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping
    //전체 할일 조회
    public ResponseEntity<List<DomainResponseDto>> getAllTasks() {
        List<DomainResponseDto> tasksList = domainService.findAllTasks();
        return ResponseEntity.ok(tasksList);
    }
    @GetMapping ("/{id}")
    // ID로 할일조회
    public ResponseEntity<DomainResponseDto> findTaskById(@PathVariable Long id) {
        DomainResponseDto task = domainService.findTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    // 제목,내용수정
    public ResponseEntity<DomainResponseDto> updateTask(@PathVariable Long id, @RequestBody DomainRequestDto requestDto) {
        DomainResponseDto updatedTask = domainService.updateTask(id, requestDto.getTitle(), requestDto.getComment());
        return ResponseEntity.ok(updatedTask);
    }
    // 메모 제목만 수정 API
    @PatchMapping("/{id}")
    public ResponseEntity<DomainResponseDto> updateTitle(@PathVariable Long id, @RequestBody DomainRequestDto requestDto) {
        DomainResponseDto updateTitle = domainService.updateTitle(id, requestDto.getTitle());
        return ResponseEntity.ok(updateTitle);
    }
    @DeleteMapping("/{id}")
    //삭제
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        domainService.delete(id);
        return ResponseEntity.noContent().build(); // 응답 데이터 없이 204 상태코드만 반환
    }
}

