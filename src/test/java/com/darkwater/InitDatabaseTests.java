package com.darkwater;

import com.darkwater.dao.QuestionDao;
import com.darkwater.dao.UserDao;
import com.darkwater.model.Question;
import com.darkwater.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDao userDao;

	@Autowired
	QuestionDao questionDao;

	@Test
	public void initDatabase() {
		Random random = new Random();
		for(int i = 0;i<11;++i){
			User user = new User();
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
			user.setName(String.format("USER%d",i));
			user.setPassword("");
			user.setSalt("");
			userDao.addUser(user);

			user.setPassword("xxx");
			userDao.updatePassword(user);

			Question question = new Question();
			Date date = new Date();
			date.setTime(1000*3600*i);
			question.setCreatedDate(date);
			question.setCommentCount(i);
			question.setContent(String.format("Balalalala%d",i));
			question.setTitle(String.format("TITLE{%d}",i));
			question.setUserId(i+1);

			questionDao.addQuestion(question);

		}
		Assert.assertEquals("xxx",userDao.selectById(2).getPassword());
		userDao.deleteById(3);
		Assert.assertNull(userDao.selectById(3));

		//System.out.print(questionDao.selectLatestQuestions(0,0,10));

	}

}
