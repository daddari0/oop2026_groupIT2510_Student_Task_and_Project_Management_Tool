package repositories;

import Entities.Comment;

import java.util.List;

public interface CommentRepository extends Repository<Comment, Long> {
    List<Comment> findByTaskId(Long taskId);
    List<Comment> findByAuthorId(Long authorId);
}