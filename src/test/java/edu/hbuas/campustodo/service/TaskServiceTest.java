package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskServiceTest {

    @Test
    void shouldUseMediumPriorityByDefault() {
        TaskService service = new TaskService();

        Task task = service.addTask("普通任务");

        assertEquals(Priority.MEDIUM, task.getPriority());
    }

    @Test
    void shouldCreateTaskWithSpecifiedPriority() {
        TaskService service = new TaskService();

        Task highTask = service.addTask("紧急任务", Priority.HIGH);
        Task lowTask = service.addTask("低优先级任务", Priority.LOW);

        assertEquals(Priority.HIGH, highTask.getPriority());
        assertEquals(Priority.LOW, lowTask.getPriority());
    }

    @Test
    void shouldFilterTasksByPriority() {
        TaskService service = new TaskService();

        service.addTask("高优先级任务1", Priority.HIGH);
        service.addTask("中优先级任务", Priority.MEDIUM);
        service.addTask("低优先级任务", Priority.LOW);
        service.addTask("高优先级任务2", Priority.HIGH);

        List<Task> result = service.filterByPriority(Priority.HIGH);

        assertEquals(2, result.size());
        assertEquals("高优先级任务1", result.get(0).getTitle());
        assertEquals("高优先级任务2", result.get(1).getTitle());
    }

    @Test
    void shouldReturnEmptyListWhenNoTaskMatchesPriority() {
        TaskService service = new TaskService();

        service.addTask("高优先级任务", Priority.HIGH);

        List<Task> result = service.filterByPriority(Priority.LOW);

        assertTrue(result.isEmpty());
    }
    @Test
    void shouldRejectNullPriority() {
        TaskService service = new TaskService();

        assertThrows(NullPointerException.class,
            () -> service.filterByPriority(null));
    }
}
