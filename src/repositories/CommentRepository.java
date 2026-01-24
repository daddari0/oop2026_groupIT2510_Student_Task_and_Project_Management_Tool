package repositories;

import Entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Comment create(Comment comment);
    Optional<Comment> findById(Long id);
    List<Comment> findAll();
    List<Comment> findByTaskId(Long taskId);
    List<Comment> findByAuthorId(Long authorId);
}
