package Services;

import Entities.Project;
import Entities.Task;
import Entities.User;
import Exceptions.DeadlineInThePast;
import Exceptions.InvalidStatusTransition;
import Exceptions.TaskWithoutProject;
import repositories.ProjectRepository;
import repositories.TaskRepository;
import repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Task createTask(Long projectId,
                           Long assigneeId,
                           String title,
                           String description,
                           String status,
                           LocalDate deadline) {

        if (projectId == null) {
            throw new TaskWithoutProject();
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(TaskWithoutProject::new);

        User assignee = null;
        if (assigneeId != null) {
            assignee = userRepository.findById(assigneeId)
                    .orElseThrow(() -> new IllegalArgumentException("Assignee not found: " + assigneeId));
        }

        if (deadline != null && deadline.isBefore(LocalDate.now())) {
            throw new DeadlineInThePast();
        }

        Task task = new Task(title, description, status, assignee, project);
        return taskRepository.create(task);
    }

    public void changeStatus(Long taskId, String newStatus) {
        Task existing = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));

        System.out.println("Changing status from " + existing.getStatus() + " to " + newStatus);

        if (!isValidTransition(existing.getStatus(), newStatus)) {
            throw new InvalidStatusTransition();
        }

        taskRepository.updateStatus(taskId, newStatus);
        existing.setStatus(newStatus);
    }


    private boolean isValidTransition(String from, String to) {
        if ("TODO".equals(from) && ("IN_PROGRESS".equals(to) || "CANCELLED".equals(to))) return true;
        if ("IN_PROGRESS".equals(from) && ("DONE".equals(to) || "CANCELLED".equals(to))) return true;
        if ("DONE".equals(from)) return false;
        return false;
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));
    }

    public List<Task> getTasksByProject(Long projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}