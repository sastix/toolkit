package com.sastix.toolkit.demoapp.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApp {
    private static final Logger LOG = (Logger) LoggerFactory.getLogger(DemoApp.class);
	public static void main(String[] args) {
        LOG.info("**** Starting DEMO app ****");
		SpringApplication.run(DemoApp.class, args);
	}
}
