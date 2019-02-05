package com.es.core.model.comment;

import com.es.core.model.phone.Phone;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
public class Comment {
    private Long id;
    private Long phoneId;
    @NotNull
    @Size(min = 1, max = 50, message = "Name of author should be between 1 and 50")
    private String authorName;
    @NotNull(message = "Rating should be between 1 and 5")
    @Min(1)
    @Max(5)
    private Integer rating;
    @NotNull
    @Size(min = 1, message = "Comment text should be not empty")
    private String commentText;
    private CommentStatus commentStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Long phoneId) {
        this.phoneId = phoneId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }
}
