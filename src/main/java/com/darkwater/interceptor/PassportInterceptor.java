package com.darkwater.interceptor;

import com.darkwater.dao.LoginTicketDao;
import com.darkwater.dao.UserDao;
import com.darkwater.model.HostHolder;
import com.darkwater.model.LoginTicket;
import com.darkwater.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by lenovo1 on 2017/2/7.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    LoginTicketDao loginTicketDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String ticket = null;
        if (null != request.getCookies()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (null != ticket) {
            LoginTicket loginTicket = loginTicketDao.selectTicket(ticket);
            if (null == loginTicket || loginTicket.getEmpired().before(new Date()) || 0 != loginTicket.getStatus()) {
                return true;
            }
            User user = userDao.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        if (null !=modelAndView ){
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
