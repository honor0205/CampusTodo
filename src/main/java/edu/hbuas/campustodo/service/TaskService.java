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
 * @author CampusTodo Lab
 */
public class TaskService {
    /** 内部任务存储，按插入顺序保留。 */
    private final List<Task> tasks = new ArrayList<>();

    /** 下一个可分配的任务id，从1开始自增。 */
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

    /**
     * 按优先级筛选任务。
     *
     * @param priority 目标优先级，若为 null 则返回空列表（避免 NPE 并匹配测试预期）
     * @return 匹配优先级的任务列表（按插入顺序）
     */
    public List<Task> filterByPriority(Priority priority) {
        if (priority == null) {
            return Collections.emptyList();
        }
        return tasks.stream()
                .filter(t -> priority.equals(t.getPriority()))
                .collect(Collectors.toList());
    }

    public Task getTaskById(long id){
        for(Task task : tasks){
            if(task.getId() == id){
                return task;
            }
        }
        throw new IllegalArgumentException("找不到该任务");
    }

    public void completeTask(long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
    }

}
