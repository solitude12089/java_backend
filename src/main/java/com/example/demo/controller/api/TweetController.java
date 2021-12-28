package com.example.demo.controller.api;


import com.example.demo.service.TwitterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import twitter4j.*;
import twitter4j.auth.AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;

@RequestMapping("/api/tweet")
@RequiredArgsConstructor
@RestController
public class TweetController {
    private final TwitterService twitterService;

    @GetMapping("/timeline")
    public Object getTimeLine() throws TwitterException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ResponseList<Status> $r = twitterService.getTimeLine(auth);
        return $r;
    }

    @PostMapping("/tweet")
    public Status postTweet(HttpServletRequest request,@RequestBody LinkedHashMap jsonObject) throws TwitterException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String content = (String) jsonObject.get("content");
        return twitterService.postTweet(auth,content);
    }

    @PostMapping(value = "/replay",consumes = "application/json")
    public Status postReplay(HttpServletRequest request, @RequestBody LinkedHashMap jsonObject) throws TwitterException, JSONException {
        AccessToken auth = (AccessToken) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String content = (String) jsonObject.get("content");
        long replayId = Long.parseLong(jsonObject.get("replayId").toString());
        return twitterService.postReplay(auth,content,replayId);
    }
}
