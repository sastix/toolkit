package com.sastix.toolkit.restclient.config;

import com.sastix.toolkit.restclient.exception.ToolkitBusinessException;
import com.sastix.toolkit.restclient.handler.CommonExceptionHandler;
import com.sastix.toolkit.restclient.handler.ExceptionHandler;
import com.sastix.toolkit.restclient.service.CommonRetryPolicy;
import com.sastix.toolkit.restclient.service.RetryRestTemplate;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestClientException;


import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class RestTemplateConfiguration {

    private String backOffPeriod="5000";
    private String maxAttempts="3";
    Boolean toolkitClientSslEnabled=false;
    String toolkitClientSslJksKeystore="<tbd>";
    String toolkitClientSslJksKeystorePassword="<tbd>";
    ResourcePatternResolver resourcePatternResolver;


    private static final ConcurrentHashMap<String, ExceptionHandler> SUPPORTED_EXCEPTIONS = new ConcurrentHashMap<>();

    static {
        SUPPORTED_EXCEPTIONS.put(ToolkitBusinessException.class.getName(), ToolkitBusinessException::new);
    }

    public RestTemplateConfiguration(String backOffPeriod, String maxAttempts, ResourcePatternResolver resourcePatternResolver) {
        this.backOffPeriod = backOffPeriod;
        this.maxAttempts = maxAttempts;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public RestTemplateConfiguration(String backOffPeriod, String maxAttempts, Boolean toolkitClientSslEnabled, String toolkitClientSslJksKeystore, String toolkitClientSslJksKeystorePassword, ResourcePatternResolver resourcePatternResolver) {
        this.backOffPeriod = backOffPeriod;
        this.maxAttempts = maxAttempts;
        this.toolkitClientSslEnabled = toolkitClientSslEnabled;
        this.toolkitClientSslJksKeystore = toolkitClientSslJksKeystore;
        this.toolkitClientSslJksKeystorePassword = toolkitClientSslJksKeystorePassword;
        this.resourcePatternResolver = resourcePatternResolver;
    }

    /**
     * Configure and return the retry template.
     */
    public RetryTemplate getRetryTemplate() {
        //Create RetryTemplate
        final RetryTemplate retryTemplate = new RetryTemplate();

        //Create Fixed back policy
        final FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();

        //Set backOffPeriod
        fixedBackOffPolicy.setBackOffPeriod(Long.valueOf(backOffPeriod));

        //Set the backoff policy
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        //Create Simple Retry Policy
        final CommonRetryPolicy retryPolicy = new CommonRetryPolicy(Integer.valueOf(maxAttempts), Collections
                .<Class<? extends Throwable>, Boolean>singletonMap(RestClientException.class, true), false);


        //Set retry policy
        retryTemplate.setRetryPolicy(retryPolicy);

        //Return the RetryTemplate
        return retryTemplate;
    }


    public RetryRestTemplate getRestTemplate() throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return getRestTemplateWithSupportedExceptions(SUPPORTED_EXCEPTIONS);
    }

    public RetryRestTemplate getRestTemplateWithSupportedExceptions(ConcurrentHashMap<String, ExceptionHandler> supportedExceptionsMap) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        return getRestTemplateWithOptions(supportedExceptionsMap,null);
    }

    public RetryRestTemplate getRestTemplateWithOptions(ConcurrentHashMap<String, ExceptionHandler> supportedExceptionsMap,ConcurrentHashMap<Integer, String> avoidRetryOnStatusCodeMap) throws CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        //Creates the restTemplate instance
        final RetryRestTemplate retryRestTemplate = new RetryRestTemplate();
        if(toolkitClientSslEnabled!=null && toolkitClientSslEnabled) {
            retryRestTemplate.setRequestFactory(sslFactory());
        }
        //Create Custom Exception Handler
        final CommonExceptionHandler exceptionHandler = new CommonExceptionHandler();

        if(avoidRetryOnStatusCodeMap!=null){
            exceptionHandler.setAvoidRetryOnStatusCodeMap(avoidRetryOnStatusCodeMap);
        }

        //Set Supported Exceptions
        exceptionHandler.setSupportedExceptions(supportedExceptionsMap);

        //Set the custom exception handler ar default
        retryRestTemplate.setErrorHandler(exceptionHandler);

        //Set Retry Template
        retryRestTemplate.setRetryTemplate(getRetryTemplate());

        //Return the template instance
        return retryRestTemplate;
    }

    private ClientHttpRequestFactory sslFactory() throws KeyStoreException, NoSuchAlgorithmException, IOException, CertificateException, UnrecoverableKeyException, KeyManagementException {
        String keyStoreFile = toolkitClientSslJksKeystore;
        String keyStorePassword = toolkitClientSslJksKeystorePassword;

        InputStream keystoreInputStream = resourcePatternResolver.getResource(keyStoreFile).getInputStream();

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(keystoreInputStream, keyStorePassword.toCharArray());

        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
                new SSLContextBuilder()
                        .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                        .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                        .build(),
                NoopHostnameVerifier.INSTANCE);

        HttpClient httpClient = HttpClients.custom().setSSLSocketFactory(socketFactory).build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        return requestFactory;
    }

    public String getBackOffPeriod() {
        return backOffPeriod;
    }

    public void setBackOffPeriod(String backOffPeriod) {
        this.backOffPeriod = backOffPeriod;
    }

    public String getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(String maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Boolean getToolkitClientSslEnabled() {
        return toolkitClientSslEnabled;
    }

    public void setToolkitClientSslEnabled(Boolean toolkitClientSslEnabled) {
        this.toolkitClientSslEnabled = toolkitClientSslEnabled;
    }

    public String getToolkitClientSslJksKeystore() {
        return toolkitClientSslJksKeystore;
    }

    public void setToolkitClientSslJksKeystore(String toolkitClientSslJksKeystore) {
        this.toolkitClientSslJksKeystore = toolkitClientSslJksKeystore;
    }

    public String getToolkitClientSslJksKeystorePassword() {
        return toolkitClientSslJksKeystorePassword;
    }

    public void setToolkitClientSslJksKeystorePassword(String toolkitClientSslJksKeystorePassword) {
        this.toolkitClientSslJksKeystorePassword = toolkitClientSslJksKeystorePassword;
    }

    public ResourcePatternResolver getResourcePatternResolver() {
        return resourcePatternResolver;
    }

    public void setResourcePatternResolver(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }
}
