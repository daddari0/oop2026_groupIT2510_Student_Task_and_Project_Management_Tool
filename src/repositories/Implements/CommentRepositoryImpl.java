package repositories;

import Entities.Comment;
import Entities.Task;
import Entities.User;
import edu.aitu.oop3.db.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepositoryImpl implements CommentRepository {

    @Override
    public Comment create(Comment comment) {
        String sql = """
            INSERT INTO comments (task_id, author_id, text)
            VALUES (?, ?, ?)
            """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, comment.getTask().getId());
            ps.setLong(2, comment.getAuthor().getId());
            ps.setString(3, comment.getContent());

            ps.executeUpdate();
            return comment;
        } catch (SQLException e) {
            throw new RuntimeException("Error creating comment", e);
        }
    }

    @Override
    public Optional<Comment> findById(Long id) {
        String sql = "SELECT * FROM comments WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding comment by id", e);
        }
    }

    @Override
    public List<Comment> findAll() {
        String sql = "SELECT * FROM comments ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            List<Comment> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all comments", e);
        }
    }

    @Override
    public List<Comment> findByTaskId(Long taskId) {
        String sql = "SELECT * FROM comments WHERE task_id = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, taskId);
            ResultSet rs = ps.executeQuery();

            List<Comment> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding comments by task_id", e);
        }
    }

    @Override
    public List<Comment> findByAuthorId(Long authorId) {
        String sql = "SELECT * FROM comments WHERE author_id = ? ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, authorId);
            ResultSet rs = ps.executeQuery();

            List<Comment> result = new ArrayList<>();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding comments by author_id", e);
        }
    }

    private Comment mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String text = rs.getString("text");


        int authorId = rs.getInt("author_id");
        int taskId = rs.getInt("task_id");

        User author = null;
        Task task = null;


        return new Comment(id, text, author, task);
    }
}
