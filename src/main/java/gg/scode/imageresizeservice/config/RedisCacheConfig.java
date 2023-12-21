package gg.scode.imageresizeservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.StringRedisSerializer;

@Deprecated
//@EnableCaching
//@Configuration
//@RequiredArgsConstructor
public class RedisCacheConfig {

//    @Value("${spring.data.redis.host}")
    private String redisHost;

//    @Value("${spring.data.redis.port}")
    private int redisPort;

//    private final CropperProperties properties;

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(
//                new RedisStandaloneConfiguration(
//                        redisHost,redisPort
//                )
//        );
//    }

//    @Bean
//    public CacheManager cacheManager() {
//
//        RedisCacheConfiguration redisConfig = RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
//                )
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
//                );
////                .entryTtl(Duration.ofSeconds(properties.getCache().getTtl()));
//
//
//        return RedisCacheManager.RedisCacheManagerBuilder
//                .fromConnectionFactory(redisConnectionFactory())
//                .cacheDefaults(redisConfig)
//                .build();
//
//    }


}
