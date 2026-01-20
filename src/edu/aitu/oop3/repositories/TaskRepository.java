package edu.aitu.oop3.repositories;

import edu.aitu.oop3.entities.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task create(Task task);
    Optional<Task> findById(long id);
    List<Task> findAll();
    List<Task> findByProjectId(long projectId);
    void updateStatus(long taskId, String newStatus);
    void delete(long id);
}
