package com.es.core.service.comment;

import com.es.core.dao.comments.CommentDao;
import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentDao commentDao;

    @Override
    public Comment getComment(Long commentId) {
        return commentDao.get(commentId).get();
    }

    @Override
    public void saveComment(Comment comment) {
        comment.setCommentStatus(CommentStatus.NEW);
        commentDao.save(comment);
    }

    @Override
    public List<Comment> findAll(Long phoneId) {
        return commentDao.findAll(phoneId);
    }

    @Override
    public void updateStatus(Long commentId, CommentStatus commentStatus) {
        commentDao.updateStatus(commentId, commentStatus);
    }
}
