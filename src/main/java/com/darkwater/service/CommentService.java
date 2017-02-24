package com.darkwater.service;

import com.darkwater.dao.CommentDao;
import com.darkwater.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by lenovo1 on 2017/2/1.
 */
@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;

    @Autowired
    SensitiveService sensitiveService;

    //add a comment if success return its id else return 0;
    public int addComment(Comment comment) {
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        return commentDao.addComment(comment) > 0 ? comment.getId() : 0;
    }

    //get comment from offset to offset+limit ordered by data desc
    public List<Comment> getLatestComments(int entityId, int entityType, int offset, int limit) {
        return commentDao.selectLatestCommentsByEntityId(entityId, entityType, offset, limit);
    }

    //get the commentcount of an entuty
    public int getCommentCount(int entityId, int entityType) {
        return commentDao.getCommentCount(entityId, entityType);
    }

    //delete comment
    public boolean deleteComment(int id) {
        return commentDao.updateStatus(id, 1) > 0;
    }

    //get a comment by id
    public Comment getCommentById(int id) {
        return commentDao.selectById(id);
    }

    //get comments by userId
    public List<Comment> getCommentsByUserId(int userId) {
        return commentDao.selectByUserId(userId);
    }

}
