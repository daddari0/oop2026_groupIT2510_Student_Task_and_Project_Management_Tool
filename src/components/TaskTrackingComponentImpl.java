package components;

import Entities.Task;
import Entities.TaskType;
import Services.TaskFilter;
import Services.TaskService;

import java.time.LocalDate;
import java.util.List;

public class TaskTrackingComponentImpl implements TaskTrackingComponent {

    private final TaskService taskService;

    public TaskTrackingComponentImpl(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public Task createTask(TaskType type,
                           Long projectId,
                           Long assigneeId,
                           String title,
                           String description,
                           String status,
                           LocalDate deadline) {
        return taskService.createTask(type, projectId, assigneeId, title, description, status, deadline);
    }

    @Override
    public void changeStatus(Long taskId, String newStatus) {
        taskService.changeStatus(taskId, newStatus);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskService.getTaskById(id);
    }

    @Override
    public List<Task> getTasksByProject(Long projectId) {
        return taskService.getTasksByProject(projectId);
    }

    @Override
    public List<Task> filterTasks(TaskFilter filter) {
        return taskService.filterTasks(filter);
    }
}
