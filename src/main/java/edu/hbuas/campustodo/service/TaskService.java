package edu.hbuas.campustodo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import edu.hbuas.campustodo.model.Task;
import edu.hbuas.campustodo.model.Priority;

/**
 * 任务服务，负责管理任务的生命周期。
 *
 * 保持实现简单以满足基线测试：按插入顺序存储任务，提供新增、查询、按优先级筛选与标记完成等方法。
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

    /**
     * 返回当前任务视图（不可修改）。
     */
    public List<Task> listAll() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * 按优先级筛选任务。
     * - priority 为 null 时返回空列表（满足测试对 null 的期望）
     * - 优先级使用 enum 比较（==）更直观且高效
     *
     * @param priority 目标优先级
     * @return 匹配优先级的任务列表（按插入顺序）
     */
    public List<Task> filterByPriority(Priority priority) {
        if (priority == null) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .filter(t -> t.getPriority() == priority)
                .collect(Collectors.toList());
    }

    /**
     * 根据 id 查找任务；找不到时抛出 IllegalArgumentException（匹配测试预期）。
     */
    public Task getTaskById(long id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new IllegalArgumentException("找不到该任务");
    }

    /**
     * 根据 id 将任务标记为已完成；如果任务不存在则抛出异常（与 getTaskById 一致）。
     */
    public void completeTask(long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
    }
}
