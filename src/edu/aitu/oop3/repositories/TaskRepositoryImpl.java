package edu.aitu.oop3.repositories;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.entities.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskRepositoryImpl implements TaskRepository {

    @Override
    public Task create(Task task) {
        String sql = "INSERT INTO tasks " +
                "(project_id, title, description, status, assignee_id, deadline, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

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
            ps.setTimestamp(7, Timestamp.valueOf(task.getCreatedAt()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task.setId(rs.getLong("id"));
            }
            return task;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create task", e);
        }
    }

    @Override
    public Optional<Task> findById(long id) {
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
            throw new RuntimeException("Failed to find task by id", e);
        }
    }

    @Override
    public List<Task> findAll() {
        String sql = "SELECT * FROM tasks";
        List<Task> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to list tasks", e);
        }
    }

    @Override
    public List<Task> findByProjectId(long projectId) {
        String sql = "SELECT * FROM tasks WHERE project_id = ?";
        List<Task> result = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, projectId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to list tasks by project", e);
        }
    }

    @Override
    public void updateStatus(long taskId, String newStatus) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setLong(2, taskId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update task status", e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete task", e);
        }
    }

    private Task mapRow(ResultSet rs) throws SQLException {
        Task task = new Task();
        task.setId(rs.getLong("id"));
        task.setProjectId(rs.getLong("project_id"));
        task.setTitle(rs.getString("title"));
        task.setDescription(rs.getString("description"));
        task.setStatus(rs.getString("status"));

        long assignee = rs.getLong("assignee_id");
        if (!rs.wasNull()) {
            task.setAssigneeId(assignee);
        }

        Date deadlineDate = rs.getDate("deadline");
        if (deadlineDate != null) {
            task.setDeadline(deadlineDate.toLocalDate());
        }

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null) {
            task.setCreatedAt(created.toLocalDateTime());
        }
        return task;
    }
}
// 1