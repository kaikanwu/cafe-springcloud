package com.kaikanwu.cafe.payment.domain.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

import static com.kaikanwu.cafe.infrastructure.infrasturcture.cache.CacheConfiguration.DEFAULT_CACHE_EXPIRES;

@Configuration
public class CacheRepository {

    @Bean(name = "settlementCache")
    public Cache getSettlementTTLCache() {
        return new CaffeineCache("settlementCache", Caffeine
                .newBuilder()
                .expireAfterAccess(DEFAULT_CACHE_EXPIRES, TimeUnit.MILLISECONDS)
                .build());
    }

}
