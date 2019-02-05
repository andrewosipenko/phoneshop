package com.es.core.dao.comments;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    Optional<Comment> get(Long commentId);
    void save(Comment comment);
    List<Comment> findAll(Long phoneId);
    void updateStatus(Long commentId, CommentStatus commentStatus);
}
