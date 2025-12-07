package uk.gov.hmcts.reform.dev.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.gov.hmcts.reform.dev.config.TaskProperties;
import uk.gov.hmcts.reform.dev.models.Task;
import uk.gov.hmcts.reform.dev.repositories.TaskRepository;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskRepository taskRepository;
    private TaskController taskController;
    private TaskProperties taskProperties;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);

        taskProperties = mock(TaskProperties.class);
        when(taskProperties.getDueDaysOffset()).thenReturn(3);
        when(taskProperties.getDefaultStatus()).thenReturn("NEW");

        taskController = new TaskController(taskRepository, taskProperties);
    }

    @Test
    void createTask_shouldSetDueDateAndStatusAndSaveTask() {

        Task inputTask = new Task();
        inputTask.setTitle("Test Task");
        inputTask.setDescription("Test Description");

        LocalDateTime expectedDueDate = LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset());

        Task savedTask = new Task(1, "Test Task", "Test Description", "NEW", expectedDueDate);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        ResponseEntity<Task> response = taskController.createTask(inputTask);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo(1);
        assertThat(response.getBody().getStatus()).isEqualTo(taskProperties.getDefaultStatus());

        assertThat(response.getBody().getDueDate())
            .isAfterOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() - 1))
            .isBeforeOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() + 1));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task capturedTask = taskCaptor.getValue();

        assertThat(capturedTask.getTitle()).isEqualTo("Test Task");
        assertThat(capturedTask.getDescription()).isEqualTo("Test Description");
        assertThat(capturedTask.getStatus()).isEqualTo(taskProperties.getDefaultStatus());
        assertThat(capturedTask.getDueDate())
            .isAfterOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() - 1))
            .isBeforeOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() + 1));
    }

    @Test
    void createTask_shouldWorkWhenDescriptionIsNull() {

        Task inputTask = new Task();
        inputTask.setTitle("Task without description");
        inputTask.setDescription(null);

        LocalDateTime expectedDueDate = LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset());

        Task savedTask = new Task(2, "Task without description", null, "NEW", expectedDueDate);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        ResponseEntity<Task> response = taskController.createTask(inputTask);

        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescription()).isNull();
        assertThat(response.getBody().getStatus()).isEqualTo(taskProperties.getDefaultStatus());
        assertThat(response.getBody().getDueDate())
            .isAfterOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() - 1))
            .isBeforeOrEqualTo(LocalDateTime.now().plusDays(taskProperties.getDueDaysOffset() + 1));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository, times(1)).save(taskCaptor.capture());
        Task capturedTask = taskCaptor.getValue();

        assertThat(capturedTask.getDescription()).isNull();
        assertThat(capturedTask.getTitle()).isEqualTo("Task without description");
        assertThat(capturedTask.getStatus()).isEqualTo(taskProperties.getDefaultStatus());
    }
}
