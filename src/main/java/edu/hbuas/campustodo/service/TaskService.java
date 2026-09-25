package edu.hbuas.campustodo.service;

import edu.hbuas.campustodo.model.Priority;
import edu.hbuas.campustodo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 任务应用服务。学生将在功能分支中逐步扩展该类。
 */
public class TaskService {
    private final List<Task> tasks = new ArrayList<>();
    private long nextId = 1;

    public Task addTask(String title) {
        return addTask(title, Priority.MEDIUM);
    }

    public Task addTask(String title, Priority priority) {
        Task task = new Task(nextId++, title, priority);
        tasks.add(task);
        return task;
    }
    public void completeTask(long id) {
        Task task = tasks.stream()
            .filter(item -> item.getId() == id)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("任务不存在：" + id));

        if (task.isCompleted()) {
            throw new IllegalArgumentException("任务已经完成：" + id);
        }

        task.complete();
    }

    public List<Task> listAll() {
        return List.copyOf(tasks);
    }

    public List<Task> filterByPriority(Priority priority) {
        Objects.requireNonNull(priority, "优先级不能为空");

        return tasks.stream()
            .filter(task -> task.getPriority() == priority)
            .toList();
    }
}
