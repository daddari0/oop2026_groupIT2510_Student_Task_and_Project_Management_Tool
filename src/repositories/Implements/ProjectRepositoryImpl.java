package repositories.Implements;

import edu.aitu.oop3.db.DatabaseConnection;
import Entities.Project;
import repositories.ProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRepositoryImpl implements ProjectRepository {

    @Override
    public Project create(Project project) {
        String sql = """
            INSERT INTO projects (name, description, owner_id, deadline, created_at)
            VALUES (?, ?, ?, ?, NOW())
            RETURNING id, created_at
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            if (project.getOwnerId() != null) {
                ps.setLong(3, project.getOwnerId());
            } else {
                ps.setNull(3, Types.BIGINT);
            }
            if (project.getDeadline() != null) {
                ps.setDate(4, Date.valueOf(project.getDeadline()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                project.setId(rs.getLong("id"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) {
                    project.setCreatedAt(ts.toLocalDateTime());
                }
            }
            return project;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating project", e);
        }
    }

    @Override
    public Optional<Project> findById(Long id) {
        String sql = "SELECT * FROM projects WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding project by id", e);
        }
    }

    @Override
    public List<Project> findAll() {
        String sql = "SELECT * FROM projects ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Project> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all projects", e);
        }
    }

    @Override
    public List<Project> findByOwnerId(Long ownerId) {
        String sql = "SELECT * FROM projects WHERE owner_id = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, ownerId);
            ResultSet rs = ps.executeQuery();

            List<Project> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding projects by owner", e);
        }
    }

    private Project mapRow(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));

        long ownerId = rs.getLong("owner_id");
        if (rs.wasNull()) {
            p.setOwnerId(null);
        } else {
            p.setOwnerId(ownerId);
        }

        Date d = rs.getDate("deadline");
        if (d != null) {
            p.setDeadline(d.toLocalDate());
        }

        Timestamp ts = rs.getTimestamp("created_at");
        if (ts != null) {
            p.setCreatedAt(ts.toLocalDateTime());
        }
        return p;
    }
}