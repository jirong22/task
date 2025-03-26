package com.planmate.planmate.repository;

import com.planmate.planmate.domain.Task;
import com.planmate.planmate.dto.DomainResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class JdbcTaskRepository implements DomainRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTaskRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    //저장
    public Task saveTask(Task task) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("task").usingGeneratedKeyColumns("id");

        // 저장할 데이터를 Map으로 준비
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", task.getTitle());
        parameters.put("comment", task.getComment());
        parameters.put("author", task.getAuthor());
        parameters.put("password", task.getPassword());
        parameters.put("created_at", task.getCreatedAt());
        parameters.put("modified_at", task.getModifiedAt());
        // 실행 후 반환
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        System.out.println("key = " + key);
        return new Task(
                key.longValue(),
                task.getTitle(),
                task.getComment(),
                task.getAuthor(),
                task.getPassword(),
                task.getCreatedAt(),
                task.getModifiedAt()
        );
    }

    @Override
    //전체 일정 리스트로 조회
    public List<DomainResponseDto> findAllTask() {
        return jdbcTemplate.query(
                "SELECT * FROM task ORDER BY modified_at DESC", taskRowMapper()
        );
    }

    //ResponseDto용 RowMapper
    private RowMapper<DomainResponseDto> taskRowMapper() {
        return (rs, rowNum) -> new DomainResponseDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("comment"),
                rs.getString("author"),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }

    @Override
    //단일일정조회
    public Optional<Task> findTaskById(Long id) {
        List<Task> result = jdbcTemplate.query(
                "SELECT * FROM task WHERE id = ?", taskRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Task> findTaskByIdForAuth(Long id) {
        List<Task> result = jdbcTemplate.query(
                "SELECT * FROM task WHERE id = ?", taskRowMapperV2(), id);
        Task task = result.stream().findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        // 비밀번호 비교 (임시 구현)
        if (task.getPassword().equals("요청한 비밀번호")) {
            return Optional.of(task);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "비밀번호 잘못됨!");
        }
    }

    @Override
    //ID 로 할일 제목 내용 수정
    public int updateTask(Long id, String title, String comment) {
        return jdbcTemplate.update(
                "UPDATE task SET title = ?, comment = ?, modified_at = ? WHERE id = ?",
                title, comment, LocalDateTime.now(), id
        );
    }

    @Override
    //ID로 제목만 수정
    public int updateTitle(Long id, String title) {
        return jdbcTemplate.update(
                "UPDATE task SET title = ?, modified_at = ? WHERE id = ?",
                title, LocalDateTime.now(), id
        );
    }

    @Override
    //삭제
    public int delete(Long id) {
        return jdbcTemplate.update(
                "DELETE FROM task WHERE id = ?", id
        );
    }

    private RowMapper<Task> taskRowMapperV2() {
        return (rs, rowNum) -> new Task(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("comment"),
                rs.getString("author"),
                null,
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
    @Override
    //조건부 일정조회
    public List<DomainResponseDto> searchTasks(String author, String modifiedDate) {
        String sql = "SELECT * FROM task WHERE 1=1";
        List<Object> params = new ArrayList<>();

        if (author != null && !author.isEmpty()) {
            sql += " AND author = ?";
            params.add(author);
        }

        if (modifiedDate != null && !modifiedDate.isEmpty()) {
            sql += " AND DATE(modified_at) = ?";
            params.add(modifiedDate);
        }

        sql += " ORDER BY modified_at DESC";

        return jdbcTemplate.query(sql, taskRowMapper(), params.toArray());
    }

}