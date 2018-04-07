package com.sastix.toolkit.demoapp.integrationtest.sandbox.client;

import com.sastix.toolkit.demoapp.client.DemoAppClient;
import com.sastix.toolkit.demoapp.client.config.DemoAppClientConfig;
import com.sastix.toolkit.demoapp.common.domain.DemoAppContextUrl;
import com.sastix.toolkit.demoapp.server.DemoApp;
import com.sastix.toolkit.restclient.service.RetryRestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class, DemoAppClientConfig.class},
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
        })
public class DemoAppClientTest implements DemoAppContextUrl {
    private static final Logger LOG = LoggerFactory.getLogger(DemoAppClientTest.class);

    @Autowired
    @Qualifier("demoAppRestTemplate")
    RetryRestTemplate retryRestTemplate;

    @Autowired
    @Qualifier("demoAppClient")
    DemoAppClient demoAppClient;

    @Test
    public void helloTest() {
        String testData = "some dummy data";
        String echoed = demoAppClient.helloEcho(testData);
        assertThat(echoed,is(testData));
    }
}

