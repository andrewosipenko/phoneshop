package com.es.core.service.comment;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;

import java.util.List;

public interface CommentService {
    Comment getComment(Long commentId);
    void saveComment(Comment comment);
    List<Comment> findAll(Long phoneId);
    void updateStatus(Long commentId, CommentStatus commentStatus);
}
