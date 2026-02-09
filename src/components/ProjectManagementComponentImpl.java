package components;

import Entities.Project;
import Services.ProjectService;

import java.time.LocalDate;
import java.util.List;

public class ProjectManagementComponentImpl implements ProjectManagementComponent {

    private final ProjectService projectService;

    public ProjectManagementComponentImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
    public Project createProject(String name,
                                 String description,
                                 String status,
                                 Long ownerId,
                                 LocalDate deadline) {
        return projectService.createProject(name, description, status, ownerId, deadline);
    }

    @Override
    public Project getProjectById(Long id) {
        return projectService.getProjectById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
}
