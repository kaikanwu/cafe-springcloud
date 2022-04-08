package com.kaikanwu.cafe.infrastructure.infrasturcture.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"com.kaikanwu.cafe.infrastructure"})
public class JPAConfiguration {
}
