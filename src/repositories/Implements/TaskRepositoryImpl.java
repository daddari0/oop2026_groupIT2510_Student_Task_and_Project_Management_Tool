package repositories.Implements;

import edu.aitu.oop3.db.DatabaseConnection;
import Entities.Task;
import repositories.TaskRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    @Override
    public Task create(Task task) {
        String sql = """
            INSERT INTO tasks (project_id, title, description, status, assignee_id, deadline, created_at)
            VALUES (?, ?, ?, ?, ?, ?, NOW())
            RETURNING id, created_at
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, task.getProjectId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setString(4, task.getStatus());

            if (task.getAssigneeId() != null) {
                ps.setLong(5, task.getAssigneeId());
            } else {
                ps.setNull(5, Types.BIGINT);
            }
            if (task.getDeadline() != null) {
                ps.setDate(6, Date.valueOf(task.getDeadline()));
            } else {
                ps.setNull(6, Types.DATE);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task.setId(rs.getLong("id"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    task.setCreatedAt(ts.toLocalDateTime());
                }
            }
            return task;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating task", e);
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding task by id", e);
        }
    }

    @Override
    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Task> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all tasks", e);
        }
    }

    @Override
    public List<Task> findByProjectId(Long projectId) {
        String sql = "SELECT * FROM tasks WHERE project_id = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, projectId);
            ResultSet rs = ps.executeQuery();

            List<Task> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding tasks by project", e);
        }
    }

    @Override
    public void updateStatus(Long taskId, String newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setLong(2, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating task status", e);
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        Task t = new Task();
        t.setId(rs.getLong("id"));
        t.setProjectId(rs.getLong("project_id"));
        t.setTitle(rs.getString("title"));
        t.setDescription(rs.getString("description"));
        t.setStatus(rs.getString("status"));

        long assigneeId = rs.getLong("assignee_id");
        if (rs.wasNull()) {
            t.setAssigneeId(null);
        } else {
            t.setAssigneeId(assigneeId);
        }

        Date d = rs.getDate("deadline");
        if (d != null) {
            t.setDeadline(d.toLocalDate());
        }

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            t.setCreatedAt(ts.toLocalDateTime());
        }
        return t;
    }
}