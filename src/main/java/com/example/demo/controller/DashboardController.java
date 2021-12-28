package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpSession;

@Configuration
@Controller
public class DashboardController {

    @GetMapping("/")
    public String getHome(Model model){
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("name", auth.getScreenName());
        return "index";
    }


}
