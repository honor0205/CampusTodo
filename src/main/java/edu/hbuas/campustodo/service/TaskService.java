package edu.hbuas.campustodo.service;
import edu.hbuas.campustodo.model.Priority;

import edu.hbuas.campustodo.model.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 任务服务，负责管理任务的生命周期。
 *
 * @author CampusTodo Lab
 */
public class TaskService {

    /** 内部任务存储，按插入顺序保留。 */
    private final List<Task> tasks = new ArrayList<>();

    /** 下一个可分配的任务 id，从 1 开始自增。 */
    private long nextId = 1;

    public Task addTask(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title must not be null or blank.");
        }
        Task task = new Task(nextId++, title.trim());
        tasks.add(task);
        return task;
    }

    public List<Task> listAll() {
        return Collections.unmodifiableList(tasks);
    }

    public List<Task> filterByPriority(Priority priority) {
        // 新增：判空，如果priority是null，直接返回空列表，防止空指针
        if (priority == null) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .filter( task -> task.getPriority() == priority)
                .toList();
    }
}

    public Task getTaskById(long id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public void completeTask(long taskId) {
        for (Task t : tasks) {
            if (t.getId() == taskId) {
                t.setCompleted(true);
                return;
            }
        }
    }

}