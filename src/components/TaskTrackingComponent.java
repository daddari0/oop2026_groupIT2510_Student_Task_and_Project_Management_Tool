package components;

import Entities.Task;
import Services.TaskFilter;
import Entities.TaskType;

import java.time.LocalDate;
import java.util.List;

public interface TaskTrackingComponent {
    Task createTask(TaskType type,
                    Long projectId,
                    Long assigneeId,
                    String title,
                    String description,
                    String status,
                    LocalDate deadline);

    void changeStatus(Long taskId, String newStatus);

    Task getTaskById(Long id);

    List<Task> getTasksByProject(Long projectId);

    List<Task> filterTasks(TaskFilter filter);
}
