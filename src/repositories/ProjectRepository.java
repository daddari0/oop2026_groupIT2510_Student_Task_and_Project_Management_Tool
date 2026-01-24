package repositories;

import Entities.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    Project create(Project project);
    Optional<Project> findById(Long id);
    List<Project> findAll();
    List<Project> findByOwnerId(Long ownerId);
}