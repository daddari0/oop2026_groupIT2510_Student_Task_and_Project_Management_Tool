package repositories;

import Entities.Project;

import java.util.List;

public interface ProjectRepository extends Repository<Project, Long> {
    List<Project> findByOwnerId(Long ownerId);
}