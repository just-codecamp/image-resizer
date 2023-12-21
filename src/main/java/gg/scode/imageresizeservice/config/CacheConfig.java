package gg.scode.imageresizeservice.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.Scheduler;
import gg.scode.imageresizeservice.config.properties.CropperProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@EnableCaching
@Configuration
@RequiredArgsConstructor
public class CacheConfig {

    private final CropperProperties properties;

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .scheduler(Scheduler.systemScheduler())
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofSeconds(60))
                .evictionListener(
                        (Object key, Object value, RemovalCause cause) ->
                                log.info("Key {} was evicted ({}): {}", key, cause, value)
                );
    }

    @Bean
    public CacheManager cacheManager(Caffeine<Object, Object> caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }

}
