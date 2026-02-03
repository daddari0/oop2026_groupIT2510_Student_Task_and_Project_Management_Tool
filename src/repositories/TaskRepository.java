package repositories;

import Entities.Task;

import java.util.List;

public interface TaskRepository extends Repository<Task, Long> {
    List<Task> findByProjectId(Long projectId);
    void updateStatus(Long taskId, String newStatus);
}