package com.example.demo.service;


import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;


public interface TwitterService {
    void setAccessTokenAndSecret(String $token, String $secret);

    Twitter GetTwitterInstance(AccessToken $auth) throws TwitterException;
    String LoginUrl() throws TwitterException;
    AccessToken AuthToken(String oauth_token,String oauth_verifier) throws TwitterException;
    ResponseList<Status> getTimeLine(AccessToken $auth) throws TwitterException;
    Status postTweet(AccessToken auth, String content) throws TwitterException;
    Status postReplay (AccessToken auth, String content,long replayId) throws TwitterException;
}
