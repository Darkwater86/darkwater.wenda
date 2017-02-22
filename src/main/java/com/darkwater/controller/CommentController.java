package com.darkwater.controller;

import com.darkwater.dao.CommentDao;
import com.darkwater.model.Comment;
import com.darkwater.model.EntityType;
import com.darkwater.model.HostHolder;
import com.darkwater.service.CommentService;
import com.darkwater.utils.WendaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by lenovo1 on 2017/2/9.
 */
@Controller
public class CommentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);
    @Autowired
    CommentDao commentDao;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(value = {"question/{qid}/add"}, method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@PathVariable("qid") int qid,
                              @RequestParam("content") String content,
                              @RequestParam("entityId") int entityId,
                              @RequestParam("entityType") int entityType) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntituType(EntityType.QUESTION);
            comment.setEntityId(qid);
            comment.setStatus(0);
            if (null == hostHolder.getUser()) {
                comment.setUserId(WendaUtils.ANONYMOUS_USERID);
            } else {
                comment.setUserId(hostHolder.getUser().getId());
            }
            if (commentService.addComment(comment) > 0) {
                return WendaUtils.getJsonString(0, comment);
            }
        } catch (Exception e) {
            LOGGER.error("comment增加问题失败" + e.getMessage());
        }
        return WendaUtils.getJsonString(1, "失败");
    }

    @RequestMapping(path = {"/question/{qid}/del"})
    public String questionDetail(Model model,
                                 @PathVariable("qid") int qid) {
        return "redirect:/question/" + qid;
    }
}
