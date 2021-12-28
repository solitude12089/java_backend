package com.example.demo.service.impl;

import com.example.demo.models.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService  {
    private final UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        log.info("Saveing new user {} to database",user.getTwitterId());
        return userRepo.save(user);
    }

    @Override
    public User findByTwitterId(Integer twitter_id) {
        log.info("Fetching  user {}",twitter_id);
        User user = userRepo.findByTwitterId(twitter_id);
        return user;
    }



}
