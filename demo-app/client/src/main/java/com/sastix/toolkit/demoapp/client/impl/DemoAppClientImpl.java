package com.sastix.toolkit.demoapp.client.impl;

import com.sastix.toolkit.demoapp.client.DemoAppClient;
import com.sastix.toolkit.demoapp.common.domain.DemoAppContextUrl;
import com.sastix.toolkit.restclient.service.RetryRestTemplate;
import com.sastix.toolkit.versioning.client.ApiVersionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DemoAppClientImpl implements DemoAppClient, DemoAppContextUrl {
    private Logger LOG = (Logger) LoggerFactory.getLogger(DemoAppClientImpl.class);

    @Autowired
    @Qualifier("demoAppApiVersionClient")
    ApiVersionClient apiVersionClient;

    @Autowired
    @Qualifier("demoAppRestTemplate")
    RetryRestTemplate retryRestTemplate;

    @Override
    public String helloEcho(String echo) {
        final String url = apiVersionClient.getApiUrl() + "/"+HELLO;
        LOG.debug("/"+HELLO+" call [get]: " + url);
        String response = retryRestTemplate.getForObject(url, String.class);
        return response;
    }
}
