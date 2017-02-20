package com.darkwater.service;

import com.darkwater.dao.QuestionDao;
import com.darkwater.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by lenovo1 on 2017/2/1.
 */
@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;

    @Autowired
    SensitiveService sensitiveService;

    @Autowired
    KeyWordService keyWordService;
    /**
     * @return 问题id(成功)
     * @return 0（失败）
     * */
    public int addQuestion(Question question){
        // TODO: 2017/2/9 敏感词过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        return questionDao.addQuestion(question)>0?question.getId():0;
    }

    public int updateCommentCount(int id, int count){
        return questionDao.updateCommentCount(id, count);
    }

    public List<Question> getLatestQuestions(int userId, int offdset, int limit){
        return questionDao.selectLatestQuestions(userId,offdset,limit);
    }

    public Set<String> getKeyWords(Question question){
        return keyWordService.filter(question.getContent());
    }

    public Question getQuestionById(int id){
        return  questionDao.selectById(id);
    }
}
