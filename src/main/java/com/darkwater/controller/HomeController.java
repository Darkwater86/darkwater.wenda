package com.darkwater.controller;

import com.darkwater.model.Question;
import com.darkwater.model.ViewObject;
import com.darkwater.service.QuestionService;
import com.darkwater.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo1 on 2017/1/30.
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(path = "/user/{userId}",method ={RequestMethod.GET} )
    public String User(Model model, @PathVariable("userId")int userId){
        model.addAttribute("vos",getQuestions(userId,0,10));
        return "index";
    }

    @RequestMapping(path = "/",method = {RequestMethod.GET})
    public String index(Model model){
        model.addAttribute("vos",getQuestions(0,0,10));
        return "index";
    }
/**
 * @param userId   userId
 * @param offset  begin
 * @param limit  length
 * @return List<ViewObject>
 * */
    private  List<ViewObject>getQuestions(int userId,int offset,int limit){
        List<ViewObject> vos = new ArrayList<ViewObject>();
        List<Question> questionList = questionService.getLatestQuestions(userId,offset,limit);

        for(Question question : questionList){
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUserById(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }
}
