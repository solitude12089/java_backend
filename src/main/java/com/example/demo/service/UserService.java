package com.example.demo.service;

import com.example.demo.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    User findByTwitterId(Integer twitter_id);



}
