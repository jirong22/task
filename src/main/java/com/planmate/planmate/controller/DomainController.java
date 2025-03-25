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
    public ResponseEntity<DomainResponseDto> createTask(@RequestBody DomainRequestDto requestDto) {
        DomainResponseDto savedTask = domainService.saveTasks(requestDto);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DomainResponseDto>> getAllTasks() {
        List<DomainResponseDto> tasksList = domainService.findAllTasks();
        return ResponseEntity.ok(tasksList);
    }
    @GetMapping ("/{id}")
    public ResponseEntity<DomainResponseDto> findTaskById(@PathVariable Long id) {
        DomainResponseDto task = domainService.findTaskById(id);
        return ResponseEntity.ok(task);
    }

}

