package com.sastix.toolkit.demoapp.client.config;

import com.sastix.toolkit.demoapp.client.DemoAppClient;
import com.sastix.toolkit.demoapp.client.impl.DemoAppClientImpl;
import com.sastix.toolkit.demoapp.common.domain.DemoAppContextUrl;
import com.sastix.toolkit.demoapp.common.exception.DemoAppException;
import com.sastix.toolkit.demoapp.common.exception.InvalidDataTypeException;
import com.sastix.toolkit.restclient.config.RestTemplateConfiguration;
import com.sastix.toolkit.restclient.exception.ToolkitBusinessException;
import com.sastix.toolkit.restclient.handler.ExceptionHandler;
import com.sastix.toolkit.restclient.service.RetryRestTemplate;
import com.sastix.toolkit.versioning.client.ApiVersionClient;
import com.sastix.toolkit.versioning.client.ApiVersionClientImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DemoAppClientConfig implements DemoAppContextUrl {
    @Value("${demoapp.server.protocol:http}")
    private String protocol;

    @Value("${demoapp.server.host:localhost}")
    private String host;

    @Value("${demoapp.server.port:8085}")
    private String port;

    @Value("${demoapp.retry.backOffPeriod:5000}")
    private String backOffPeriod;

    @Value("${demoapp.retry.maxAttempts:3}")
    private String maxAttempts;

    @Value("${demoapp.client.ssl.enabled:false}")
    Boolean demoAppClientSslEnabled;

    @Value("${demoapp.client.ssl.jks.keystore:path}")
    String demoAppClientSslJksKeystore;

    @Value("${demoapp.client.ssl.jks.keystore.password:securedPass}")
    String demoAppClientSslJksKeystorePassword;

    @Autowired
    ResourcePatternResolver resourcePatternResolver;

    private static final ConcurrentHashMap<String, ExceptionHandler> SUPPORTED_EXCEPTIONS = new ConcurrentHashMap<>();

    static {
        SUPPORTED_EXCEPTIONS.put(ToolkitBusinessException.class.getName(), ToolkitBusinessException::new);
        SUPPORTED_EXCEPTIONS.put(InvalidDataTypeException.class.getName(), InvalidDataTypeException::new);
        SUPPORTED_EXCEPTIONS.put(DemoAppException.class.getName(), DemoAppException::new);
    }

    @Bean(name = "demoAppClient")
    public DemoAppClient demoAppClient(){
        return new DemoAppClientImpl();
    }

    @Autowired
    @Qualifier("demoAppRestTemplate")
    RetryRestTemplate retryRestTemplate;

    @Bean(name="demoAppRestTemplate")
    public RetryRestTemplate getRetryRestTemplate() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        RestTemplateConfiguration restTemplateConfiguration = new RestTemplateConfiguration(backOffPeriod, maxAttempts, demoAppClientSslEnabled, demoAppClientSslJksKeystore, demoAppClientSslJksKeystorePassword, resourcePatternResolver);
        return restTemplateConfiguration.getRestTemplateWithSupportedExceptions(SUPPORTED_EXCEPTIONS);
    }

    @Bean(name = "demoAppApiVersionClient")
    public ApiVersionClient getApiVersionClient() throws Exception {
        return new ApiVersionClientImpl(protocol, host, port, REST_API_V1_0, retryRestTemplate);
    }

}
