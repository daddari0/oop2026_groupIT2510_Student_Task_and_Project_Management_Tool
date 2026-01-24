package repositories;

import Entities.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);
    Optional<Task> findById(Long id);
    List<Task> findAll();
    List<Task> findByProjectId(Long projectId);
    void updateStatus(Long taskId, String newStatus);
}
