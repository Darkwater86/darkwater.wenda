package com.darkwater.service;

import com.darkwater.dao.LoginTicketDao;
import com.darkwater.dao.UserDao;
import com.darkwater.model.LoginTicket;
import com.darkwater.model.User;
import com.darkwater.utils.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by lenovo1 on 2017/2/1.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;

    public User getUserById(int id) {
        return userDao.selectById(id);
    }

    public Map<String, String> login(String name, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "请输入用户名");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "请输入密码");
            return map;
        }
        if (null == userDao.selectByName(name)) {
            map.put("msg", "/用户名/或密码错误");
            return map;
        }
        User user = userDao.selectByName(name);
        String pwd = MD5Util.MD5(password + user.getSalt());
        //boolean b = (pwd == user.getPassword());
        if (!(pwd.equals(user.getPassword()))) {
            map.put("msg", "用户名或/密码/错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public Map<String, String> register(String name, String password) {
        Map<String, String> map = new HashMap<>();
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码名不能为空！");
            return map;
        }
        if (null != userDao.selectByName(name)) {
            map.put("msg", "用户名已被注册！");
            return map;
        }

        User user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(MD5Util.MD5(password + user.getSalt()));
        userDao.addUser(user);

        String ticket = addLoginTicket(userDao.selectByName(name).getId());
        map.put("ticket", ticket);
        return map;
    }

    public String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        loginTicket.setEmpired(setData(10));
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public static Date setData(int day) {
        Date now = new Date();
        now.setTime((3600*24*day + now.getTime()/1000)*1000);
        return now;
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(1, ticket);
    }
}
