package com.es.core.dao.comments;

import com.es.core.model.comment.Comment;
import com.es.core.model.comment.CommentStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcCommentDao implements CommentDao {
    private final String SQL_SELECT_COMMENT_BY_ID = "select * from comments where id = :id and status = approved";
    private final String SQL_SELECT_ALL_COMMENTS_BY_PHONE_ID = "select * from comments where phoneId = :phoneId";
    private final String SQL_UPDATE_STATUS_BY_ID = "update comments set status = :status where id = :id";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SqlParameterSource sqlParameterSource;
    private BeanPropertyRowMapper<Comment> commentBeanPropertyRowMapper = BeanPropertyRowMapper.newInstance(Comment.class);


    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Comment> get(Long commentId) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("id", commentId);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            Comment comment = this.namedParameterJdbcTemplate.queryForObject(SQL_SELECT_COMMENT_BY_ID, sqlParameterSource, commentBeanPropertyRowMapper);
            return Optional.of(comment);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Comment comment) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("comments").usingGeneratedKeyColumns("id");
        sqlParameterSource = new BeanPropertySqlParameterSource(comment);
        Long commentId = simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        comment.setId(commentId);
    }

    @Override
    public List<Comment> findAll(Long phoneId) {
        try {
            this.sqlParameterSource = new MapSqlParameterSource("phoneId", phoneId);
            this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
            return this.namedParameterJdbcTemplate.query(SQL_SELECT_ALL_COMMENTS_BY_PHONE_ID, commentBeanPropertyRowMapper);
        } catch (Exception ex){
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Long commentId, CommentStatus commentStatus) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        this.namedParameterJdbcTemplate.update(SQL_UPDATE_STATUS_BY_ID, getAllValuesCommentStatus(commentStatus.toString(), commentId));
    }

    private Map<String, Object> getAllValuesCommentStatus(String commentStatus, Long commentId) {
        Map<String, Object> getAllValuesCommentStatus = new HashMap<>();
        getAllValuesCommentStatus.put("status", commentStatus);
        getAllValuesCommentStatus.put("id", commentId);
        return getAllValuesCommentStatus;
    }
}
