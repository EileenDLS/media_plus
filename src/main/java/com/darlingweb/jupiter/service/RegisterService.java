package com.darlingweb.jupiter.service;

import com.darlingweb.jupiter.dao.RegisterDao;
import com.darlingweb.jupiter.entity.db.User;
import com.darlingweb.jupiter.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RegisterService {
    @Autowired
    private RegisterDao registerDao;

    public boolean register (User user) throws IOException{
        // encrypt password
        user.setPassword(Util.encryptPassword(user.getUserId(), user.getPassword()));
        return registerDao.register(user);
    }
}
