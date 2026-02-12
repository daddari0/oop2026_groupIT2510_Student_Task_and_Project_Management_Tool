import Config.NotificationCenter;
import Config.SettingsManager;
import Entities.*;
import Services.*;
import components.*;
import edu.aitu.oop3.db.DatabaseConnection;
import repositories.*;
import repositories.CommentRepositoryImpl;
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
        CommentRepository commentRepo = new CommentRepositoryImpl();

        UserService userService = new UserService(userRepo);
        ProjectService projectService = new ProjectService(projectRepo);
        TaskService taskService = new TaskService(taskRepo, projectRepo, userRepo);
        CommentService commentService = new CommentService(commentRepo, taskRepo, userRepo);

        ProjectManagementComponent projectComponent =
                new ProjectManagementComponentImpl(projectService);

        TaskTrackingComponent taskComponent =
                new TaskTrackingComponentImpl(taskService);

        NotificationComponent notificationComponent =
                new NotificationComponentImpl(NotificationCenter.getInstance());

        SettingsManager settings = SettingsManager.getInstance();
        settings.setDefaultTaskStatus("TODO");

        User user = userService.createUser("Abay", "abay@example.com");
        System.out.println("User created: " + user.getId());

        Project project = projectComponent.createProject(
                "New Project",
                "123",
                "ACTIVE",
                user.getId(),
                LocalDate.now().plusDays(7)
        );
        System.out.println("Project created: " + project.getId());

        Task task = taskComponent.createTask(
                TaskType.BUG,
                project.getId(),
                user.getId(),
                "Fix login bug",
                "User cannot log in",
                null,
                LocalDate.now().plusDays(3)
        );
        System.out.println("Task created: " + task.getId());

        notificationComponent.subscribe(msg -> System.out.println("NOTIFICATION: " + msg));
        notificationComponent.notify("Task created: " + task.getTitle());

        taskComponent.changeStatus(task.getId(), "IN_PROGRESS");
        System.out.println("Task status changed to IN_PROGRESS.");
        taskComponent.changeStatus(task.getId(), "DONE");
        System.out.println("Task status changed to DONE.");

        Comment comment = commentService.createComment(
                task.getId(),
                user.getId(),
                "Looks good!"
        );
        System.out.println("Comment created: " + comment.getId());

        var overdueTasks = taskComponent.filterTasks(
                t -> t.getDeadline() != null
                        && t.getDeadline().isBefore(LocalDate.now())
                        && !"DONE".equals(t.getStatus())
        );
        System.out.println("Overdue tasks count: " + overdueTasks.size());

        var inProgress = taskComponent.filterTasks(
                t -> "IN_PROGRESS".equals(t.getStatus())
        );
        System.out.println("In progress tasks: " + inProgress.size());
    }
}
