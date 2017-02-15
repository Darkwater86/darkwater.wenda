//package com.darkwater.aspect;//package com.darkwater.aspect;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
///**
// * Created by lenovo1 on 2017/1/21.
// */
//@Aspect
//@Component
//public class LogAspect {
//    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
//
//    @Before("execution(* com.darkwater.controller.*Controller.*(..))")
//    public void beforeMethod(JoinPoint joinPoint){
//        StringBuilder sb = new StringBuilder();
//        for (Object args : joinPoint.getArgs()){
//            sb.append("arg:"+args.toString()+"|");
//        }
//        logger.info("before method:"+sb.toString()+"       "+new Date());
//    }
//    @After("execution(* com.darkwater.controller.*Controller.*(..))")
//    public void afterMethod(){
//        logger.info("after method"+new Date());
//    }
//}
