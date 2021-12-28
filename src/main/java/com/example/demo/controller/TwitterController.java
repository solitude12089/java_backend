package com.example.demo.controller;


import com.example.demo.models.User;
import com.example.demo.service.TwitterService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/twitter")
@Controller
@RequiredArgsConstructor
public class TwitterController {
    private final TwitterService twitterService;

    @GetMapping("/login")
    public void getLogin(HttpServletRequest request, HttpServletResponse response) throws TwitterException, IOException {
        String url = twitterService.LoginUrl();
        response.sendRedirect(url);
    }

    @GetMapping("/callback")
    public void getCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {

        try {
            String oauthToken = request.getParameter("oauth_token");
            String oauthVerifier = request.getParameter("oauth_verifier");

            AccessToken s = twitterService.AuthToken(oauthToken,oauthVerifier);
            if(s ==null){
                response.setStatus(401);
                return;
            }
            TestingAuthenticationToken authToken = new TestingAuthenticationToken(s,s.getToken());
            authToken.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            response.sendRedirect("/");
        }
        catch (Exception $e){
            response.setStatus(500);
            return;
        }


    }



    


}
