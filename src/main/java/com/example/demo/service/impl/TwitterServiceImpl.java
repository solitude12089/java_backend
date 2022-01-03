package com.example.demo.service.impl;

import com.example.demo.service.TwitterService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class TwitterServiceImpl implements TwitterService {

    @Value("${twitter.consumer.key}")
    private String twitter_consumer_key;
    @Value("${twitter.consumer.secret}")
    private String twitter_consumer_secret;
    @Value("${twitter.consumer.callback}")
    private String twitter_consumer_callback;

    private Twitter _twitter;
    private final Map<String, RequestToken> tokenSecrets;
    private String _accessToken;
    private String _accessTokenSecret;


    @Override
    public void setAccessTokenAndSecret(String $token, String $secret){
        _accessToken = $token;
        _accessTokenSecret = $secret;
    }

    @Override
    public Twitter GetTwitterInstance(AccessToken auth){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitter_consumer_key)
                .setOAuthConsumerSecret(twitter_consumer_secret)
                .setOAuthAccessToken(auth.getToken())
                .setOAuthAccessTokenSecret(auth.getTokenSecret());
        _twitter = new TwitterFactory(cb.build()).getInstance();
        return _twitter;
    }

    @Override
    public String LoginUrl() throws TwitterException {
        _twitter = new TwitterFactory().getInstance();
        _twitter.setOAuthConsumer(twitter_consumer_key,twitter_consumer_secret);
        RequestToken requestToken = _twitter.getOAuthRequestToken(twitter_consumer_callback);
        tokenSecrets.put(requestToken.getToken(), requestToken);
        return  requestToken.getAuthorizationURL();
    }

    @Override
    public AccessToken AuthToken(String oauth_token,String oauth_verifier) throws TwitterException {
        _twitter = new TwitterFactory().getInstance();
        _twitter.setOAuthConsumer(twitter_consumer_key,twitter_consumer_secret);
        AccessToken accessToken = _twitter.getOAuthAccessToken(tokenSecrets.get(oauth_token), oauth_verifier);
        return  accessToken;
    }

    @Override
    public Map<String, Status> getTimeLine(AccessToken auth) throws TwitterException {
        Twitter _twitter = GetTwitterInstance(auth);
        var $r = _twitter.getHomeTimeline();

        Map<String, Status> rtMap =  new HashMap<String, Status>();

        for (Status status : $r) {
            rtMap.put(String.valueOf(status.getId()), status);
        }
        return rtMap;
    }

    @Override
    public Status postTweet(AccessToken auth, String content) throws  TwitterException {
        Twitter _twitter = GetTwitterInstance(auth);
        StatusUpdate statusUpdate = new StatusUpdate(content);
        Status status = _twitter.updateStatus(statusUpdate);
        return status;
    }
    @Override
    public Status postReply(AccessToken auth, String content, long tweet_id) throws TwitterException {
        Twitter _twitter = GetTwitterInstance(auth);
        return _twitter.updateStatus(new StatusUpdate(content).inReplyToStatusId(tweet_id));
    }

    @Override
    public Status postRetweet(AccessToken auth, long tweet_id) throws  TwitterException {
        Twitter _twitter = GetTwitterInstance(auth);
        return _twitter.retweetStatus(tweet_id);
    }

}
