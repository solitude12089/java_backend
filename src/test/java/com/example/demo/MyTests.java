package com.example.demo;

import com.example.demo.service.TwitterService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
@AutoConfigureMockMvc
public class MyTests {
    @MockBean
    private TwitterService _twitterService;
    MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    public void callbackSuccess() throws Exception {
        AccessToken token;
        token = new AccessToken("123","456");
        Mockito.when(_twitterService.AuthToken("123", "456"))
                .thenReturn(token);
        String uri = "/twitter/callback";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .param("oauth_token", String.valueOf(123))
                .param("oauth_verifier", String.valueOf(456))
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        System.out.println(status);
        Assert.assertEquals("成功",302,status);

    }
    @Test
    public void callbackFail() throws Exception {

        String uri = "/twitter/callback";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .param("oauth_token", String.valueOf(123))
                .param("oauth_verifier", String.valueOf(456))
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = result.getResponse().getStatus();
        System.out.println(status);
        Assert.assertEquals("成功",401,status);

    }
}
