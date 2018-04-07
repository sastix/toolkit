package com.sastix.toolkit.demoapp.integrationtest.sandbox.client;

import com.sastix.toolkit.demoapp.client.config.DemoAppClientConfig;
import com.sastix.toolkit.demoapp.common.domain.DemoAppContextUrl;
import com.sastix.toolkit.demoapp.server.DemoApp;
import com.sastix.toolkit.restclient.config.ToolkitRestTemplateConfiguration;
import com.sastix.toolkit.versioning.client.ApiVersionClient;
import com.sastix.toolkit.versioning.model.VersionDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class, DemoAppClientConfig.class, ToolkitRestTemplateConfiguration.class},
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
public class DemoAppClientApiVersionTest implements DemoAppContextUrl {
    private static final Logger LOG = LoggerFactory.getLogger(DemoAppClientApiVersionTest.class);

    @Autowired
    @Qualifier("demoAppApiVersionClient")
    ApiVersionClient apiVersionClient;

    @Test
    public void demoAppApiVersionTest(){
        VersionDTO versionDTO = apiVersionClient.getApiVersion();
        String apiUrl = apiVersionClient.getApiUrl();
        String demoAppContext = apiVersionClient.getContext();
        assertThat(versionDTO.getMaxVersion(),is(1.0));
        assertThat(apiUrl,is("http://localhost:8585/"+BASE_URL+"/v1"));
        assertThat(demoAppContext,is("/"+BASE_URL+"/v1"));
    }
}
