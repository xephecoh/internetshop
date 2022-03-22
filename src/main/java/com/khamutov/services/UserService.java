package com.khamutov.services;

import Utills.PasswordEncryptor;
import com.khamutov.dao.UserDao;


public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void saveUser(String name,String password){
        String saltedPass = PasswordEncryptor.encrypt(password);
        userDao.saveUser(name,saltedPass);
    }

    public boolean isUserValid(String name,String password){
        String saltedPass = PasswordEncryptor.encrypt(password);
        return userDao.isUserValid(name,saltedPass);
    }
    public String getUserRole(String name){
        return userDao.getUserRole(name);
    }

}
