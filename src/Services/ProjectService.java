package Services;

import Entities.Project;
import Exceptions.DeadlineInThePast;
import repositories.ProjectRepository;

import java.time.LocalDate;
import java.util.List;

public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name,
                                 String description,
                                 String status,
                                 Long ownerId,
                                 LocalDate deadline) {

        if (deadline != null && deadline.isBefore(LocalDate.now())) {
            throw new DeadlineInThePast();
        }

        Project p = new Project.Builder()
                .name(name)
                .description(description)
                .status(status)
                .ownerId(ownerId)
                .deadline(deadline)
                .build();

        return projectRepository.create(p);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found: " + id));
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}