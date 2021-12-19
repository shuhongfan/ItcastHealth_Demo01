package com.shf.service;

import com.shf.pojo.User;

public interface UserService {
    public User findByUsername(String username);
}
