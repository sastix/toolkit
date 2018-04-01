package com.sastix.toolkit.versioning.client;

import com.sastix.toolkit.versioning.model.VersionDTO;

public interface ApiVersionClient {

    VersionDTO getApiVersion();
    
    String getContext();
    
    String getApiUrl();

    void updateContext();
}
