import edu.aitu.oop3.db.DatabaseConnection;
import Entities.Project;
import Entities.Task;
import Entities.User;
import Services.ProjectService;
import Services.TaskService;
import Services.UserService;
import repositories.ProjectRepository;
import repositories.TaskRepository;
import repositories.UserRepository;
import repositories.Implements.ProjectRepositoryImpl;
import repositories.Implements.TaskRepositoryImpl;
import repositories.Implements.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Connecting to Supabase...");

        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("Connected successfully!");
            String sql = "SELECT CURRENT_TIMESTAMP";
            try (PreparedStatement stmt = connection.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Database time: " + rs.getTimestamp(1));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error while connecting to database:");
            e.printStackTrace();
        }

        UserRepository userRepo = new UserRepositoryImpl();
        ProjectRepository projectRepo = new ProjectRepositoryImpl();
        TaskRepository taskRepo = new TaskRepositoryImpl();

        UserService userService = new UserService(userRepo);
        ProjectService projectService = new ProjectService(projectRepo);
        TaskService taskService = new TaskService(taskRepo, projectRepo, userRepo);

        User user = userService.createUser("Abay", "abay@example.com");
        System.out.println("User created: " + user.getId());

        Project project = projectService.createProject(
                "New Project",
                "123",
                "ACTIVE",
                user.getId(),
                LocalDate.now().plusDays(7)
        );
        System.out.println("Project created: " + project.getId());

        Task task = taskService.createTask(
                project.getId(),
                user.getId(),
                "task",
                "1233",
                "TODO",
                LocalDate.now().plusDays(3)
        );
        System.out.println("Task created: " + task.getId());

        taskService.changeStatus(task.getId(), "IN_PROGRESS");
        System.out.println("Task status changed.");
    }

}