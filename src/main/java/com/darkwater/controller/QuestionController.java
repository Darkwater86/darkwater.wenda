package com.darkwater.controller;

import com.darkwater.dao.QuestionDao;
import com.darkwater.model.HostHolder;
import com.darkwater.model.Question;
import com.darkwater.service.QuestionService;
import com.darkwater.utils.WendaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by lenovo1 on 2017/2/9.
 */
@Controller
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    QuestionDao questionDao;

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;
    @RequestMapping(value = {"question/add"},method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam("title")String title,
                              @RequestParam("content")String content){
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());
            if (null == hostHolder.getUser()){
                question.setUserId(WendaUtils.ANONYMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }
            if(questionService.addQuestion(question)>0){
                return WendaUtils.getJsonString(0);
            }
        }catch (Exception e){
            LOGGER.error("增加问题失败"+e.getMessage());
        }
        return WendaUtils.getJsonString(1,"失败");
    }
}
