package com.darkwater.service;

import org.springframework.stereotype.Service;

/**
 * Created by lenovo1 on 2017/1/21.
 */
@Service
public class WendaService {
    public String getMessage(int userId){
        return "Hello Darkwater"+String.valueOf(userId);
    }
}
