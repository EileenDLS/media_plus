package com.darlingweb.jupiter.service;

import com.darlingweb.jupiter.dao.LoginDao;
import com.darlingweb.jupiter.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class LoginService {
    @Autowired
    private LoginDao loginDao;

    public String verifyLogin(String userId, String password) throws IOException {
        password = Util.encryptPassword(userId, password);
        return loginDao.verifyLogin(userId, password);

    }
}
