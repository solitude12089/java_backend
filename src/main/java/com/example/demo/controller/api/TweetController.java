package com.example.demo.controller.api;


import com.example.demo.service.TwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import twitter4j.*;
import twitter4j.auth.AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RequestMapping("/api/tweet")
@RequiredArgsConstructor
@RestController
public class TweetController {
    private final TwitterService twitterService;

    @GetMapping("/timeline")
    public Object getTimeLine() throws TwitterException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Status> $r = twitterService.getTimeLine(auth);
        return new ResponseEntity<Object>($r, HttpStatus.OK);
//        return $r;
    }

    @PostMapping("/tweet")
    public Status postTweet(HttpServletRequest request,@RequestBody LinkedHashMap jsonObject) throws TwitterException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String content = (String) jsonObject.get("content");
        return twitterService.postTweet(auth,content);
    }

    @PostMapping(value = "/reply",consumes = "application/json")
    public Status postReply(HttpServletRequest request, @RequestBody LinkedHashMap jsonObject) throws TwitterException, JSONException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String content = (String) jsonObject.get("content");
        long tweetId = Long.parseLong(jsonObject.get("tweetId").toString());
        return twitterService.postReply(auth,content,tweetId);
    }

    @PostMapping(value = "/retweet",consumes = "application/json")
    public Status postRetweet(HttpServletRequest request, @RequestBody LinkedHashMap jsonObject) throws TwitterException, JSONException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long tweetId = Long.parseLong(jsonObject.get("tweetId").toString());
        return twitterService.postRetweet(auth,tweetId);
    }
}
