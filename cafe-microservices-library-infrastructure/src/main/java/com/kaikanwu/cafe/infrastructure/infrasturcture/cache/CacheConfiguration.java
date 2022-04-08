package com.kaikanwu.cafe.infrastructure.infrasturcture.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;


@Configuration
public class CacheConfiguration {

    /**
     * 默认缓存时间一分钟
     */
    public static final long DEFAULT_CACHE_EXPIRES = 60 * 1000;

    @Bean
    public CacheManager configCacheManager() {
        CaffeineCacheManager manager = new CaffeineCacheManager();
        manager.setCaffeine(Caffeine.newBuilder().expireAfterWrite(DEFAULT_CACHE_EXPIRES, TimeUnit.MILLISECONDS));
        return manager;
    }


}
