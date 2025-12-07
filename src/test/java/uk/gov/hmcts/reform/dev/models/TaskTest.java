package uk.gov.hmcts.reform.dev.models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testNoArgsConstructor() {
        Task task = new Task();
        assertNotNull(task);
        assertEquals(0, task.getId());
        assertNull(task.getTitle());
        assertNull(task.getDescription());
        assertNull(task.getStatus());
        assertNull(task.getDueDate());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime dueDate = LocalDateTime.of(2025, 12, 7, 12, 0);
        Task task = new Task(1, "Test Title", "Test Description", "Pending", dueDate);

        assertEquals(1, task.getId());
        assertEquals("Test Title", task.getTitle());
        assertEquals("Test Description", task.getDescription());
        assertEquals("Pending", task.getStatus());
        assertEquals(dueDate, task.getDueDate());
    }

    @Test
    void testSettersAndGetters() {
        Task task = new Task();

        task.setId(2);
        task.setTitle("New Title");
        task.setDescription("New Description");
        task.setStatus("Completed");
        LocalDateTime dueDate = LocalDateTime.of(2025, 12, 8, 10, 30);
        task.setDueDate(dueDate);

        assertEquals(2, task.getId());
        assertEquals("New Title", task.getTitle());
        assertEquals("New Description", task.getDescription());
        assertEquals("Completed", task.getStatus());
        assertEquals(dueDate, task.getDueDate());
    }
}
