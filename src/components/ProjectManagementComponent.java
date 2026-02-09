package components;

import Entities.Project;

import java.time.LocalDate;
import java.util.List;

public interface ProjectManagementComponent {
    Project createProject(String name,
                          String description,
                          String status,
                          Long ownerId,
                          LocalDate deadline);

    Project getProjectById(Long id);

    List<Project> getAllProjects();
}
