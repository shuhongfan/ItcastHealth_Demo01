package com.shf.dao;

import com.shf.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
