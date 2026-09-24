package edu.hbuas.campustodo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    @Test
    void shouldAddTask() {
        TaskService service = new TaskService();

        var task = service.addTask("完成需求评审");

        assertEquals(1L, task.getId());
        assertEquals("完成需求评审", task.getTitle());
        assertFalse(task.isCompleted());
        assertEquals(1, service.listAll().size());
    }

    @Test
    void shouldRejectBlankTitle() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
                () -> service.addTask("   "));
    }
    @Test
    void shouldCompleteTaskById() {
        TaskService service = new TaskService();
        var task = service.addTask("完成实验报告");

        service.completeTask(task.getId());

        assertTrue(task.isCompleted());
    }

    @Test
    void shouldRejectCompletingTaskTwice() {
        TaskService service = new TaskService();
        var task = service.addTask("完成实验报告");
        service.completeTask(task.getId());

        assertThrows(IllegalArgumentException.class,
            () -> service.completeTask(task.getId()));
    }

    @Test
    void shouldRejectUnknownTaskId() {
        TaskService service = new TaskService();

        assertThrows(IllegalArgumentException.class,
            () -> service.completeTask(999L));
    }
}
