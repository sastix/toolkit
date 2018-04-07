package com.sastix.toolkit.demoapp.server.config;


import com.sastix.toolkit.demoapp.common.domain.DemoAppContextUrl;
import com.sastix.toolkit.versioning.model.VersionDTO;
import com.sastix.toolkit.versioning.service.ApiVersionService;
import com.sastix.toolkit.versioning.service.ApiVersionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Version Configuration.
 */
@Configuration
@ComponentScan({"com.sastix.toolkit","com.sastix.toolkit.demoapp"})
public class VersionConfiguration implements DemoAppContextUrl {

    public static VersionDTO DEMOAPP_SERVER_VERSION = new VersionDTO()
            .withMinVersion(Double.valueOf(REST_API_V1_0))
            .withMaxVersion(Double.valueOf(REST_API_V1_1))
            .withVersionContext(Double.valueOf(REST_API_V1_0),  "/"+BASE_URL+"/v" + REST_API_V1_0);

    @Bean
    public ApiVersionService apiVersionService() {
            /*
             * you need to configure the api version service with the
			 * constructor argument of the api ranges you support
			 */
        return new ApiVersionServiceImpl(DEMOAPP_SERVER_VERSION);
    }
}
