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

    @RequestMapping(value = {"/addComment"}, method = RequestMethod.POST)
//    @ResponseBody
    public String addQuestion(@RequestParam("content") String content,
                              @RequestParam("qid") int qid) {
        try {
            Comment comment = new Comment();
            comment.setContent(content);
            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.QUESTION);
            comment.setEntityId(qid);
            comment.setStatus(0);
            if (null == hostHolder.getUser()) {
                comment.setUserId(WendaUtils.ANONYMOUS_USERID);
            } else {
                comment.setUserId(hostHolder.getUser().getId());
            }
            if (commentService.addComment(comment) > 0) {
//                return WendaUtils.getJsonString(0, comment);
                return "redirect:/question/" + qid;
            }
        } catch (Exception e) {
            LOGGER.error("comment增加问题失败" + e.getMessage());
        }
//        return WendaUtils.getJsonString(1, "失败");
        return "redirect:/question/" + qid;
    }

    @RequestMapping(path = {"/commentDel"})
    public String commentDetail(Model model,
                                @RequestParam("cid") int cid,
                                @RequestParam("qid") int qid) {
        try {
        if (!commentService.deleteComment(cid)) {
            model.addAttribute("cErrMsg", "评论删除错误");
        }
        }catch (Exception e){
            LOGGER.error("删除评论失败"+e.getMessage());
        }
        return "redirect:/question/" + qid;
    }

}
