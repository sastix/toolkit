package com.sastix.toolkit.demoapp.server.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sastix.toolkit.demoapp.server.DemoApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
        properties = {
                "spring.datasource.url:jdbc:h2:mem:demoapp_db",
                "server.port: 8585",
                "demoapp.server.protocol: http",
                "demoapp.server.host: localhost",
                "demoapp.server.port: 8585",
                "api.version: 1",
                "demoapp.retry.backOffPeriod:10",
                "demoapp.retry.maxAttempts:1",
                "logging.level.com.sastix.toolkit.demoapp=DEBUG"
        })
public class ApiDataHandlerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    DemoAppService demoAppService;

    @DirtiesContext
    @Test
    public void helloTest(){
        String testData = "just testing";
        String echoed = demoAppService.helloEcho(testData);
        assertThat(echoed,is(testData));
    }
}
