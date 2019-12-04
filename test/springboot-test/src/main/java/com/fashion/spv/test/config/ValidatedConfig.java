package com.fashion.spv.test.config;

import com.fashion.mars.validated.config.annotation.EnableValidatedConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableValidatedConfig(fileName = "valid")
public class ValidatedConfig {


}
