package com.khamutov.services;

import com.khamutov.dao.UserDao;


public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(String name,String password){
        userDao.saveUser(name,password);
    }
}
