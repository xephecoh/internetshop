package com.khamutov.dao;

public interface UserDao {
    boolean isUserValid(String name, String password);
    void saveUser(String name,String password);
}
