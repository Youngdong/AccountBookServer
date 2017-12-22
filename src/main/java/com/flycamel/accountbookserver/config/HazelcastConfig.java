package com.flycamel.accountbookserver.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Bean
    HazelcastInstance hazelcastInstance() {
        return HazelcastClient.newHazelcastClient();
    }

    @Bean
    CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }
}
