package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.hmcts.reform.dev.config.TaskProperties;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import java.time.LocalDateTime;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskProperties taskProperties;

    public TaskController(TaskRepository taskRepository, TaskProperties taskProperties) {
        this.taskRepository = taskRepository;
        this.taskProperties = taskProperties;
    }

    @PostMapping(value = "/create-task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {

        task.setDueDate(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset()));
        task.setStatus(taskProperties.getDefaultStatus());

        Task saved = taskRepository.save(task);

        return ok(saved);
    }
}
