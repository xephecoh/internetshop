package com.khamutov.services;

import com.khamutov.jdbc.dao.UserDao;


public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(String name,String password){
        userDao.saveUser(name,password);
    }

}
