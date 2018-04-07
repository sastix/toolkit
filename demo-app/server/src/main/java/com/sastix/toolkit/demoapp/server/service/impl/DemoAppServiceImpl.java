package com.sastix.toolkit.demoapp.server.service.impl;

import com.sastix.toolkit.demoapp.server.repository.GeneralCodeRepository;
import com.sastix.toolkit.demoapp.server.service.DemoAppService;
import com.sastix.toolkit.demoapp.server.utils.Conversions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoAppServiceImpl implements DemoAppService,Conversions {
    private static final Logger LOG = LoggerFactory.getLogger(DemoAppServiceImpl.class);

    @Autowired
    GeneralCodeRepository generalCodeRepository;

    @Override
    public String helloEcho(String echo) {
        return echo;
    }
}
