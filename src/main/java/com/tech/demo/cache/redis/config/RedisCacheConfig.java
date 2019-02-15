package com.tech.demo.cache.redis.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import com.tech.demo.cache.redis.CustomCacheErrorHandler;
import com.tech.demo.cache.redis.LoggingRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis cache server config.
 *
 * @author agarwarj
 * @version 1.0
 * @date Jan 1, 2019
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport
{
    @Value("${spring.redis.host:localhost}")
    private String redisHost;

    @Value("${spring.redis.port:6379}")
    private Integer redisPort;

    @Value("${product.data.expiry.seconds.redis:300}")
    private long productDataExpiry;

    @Override
    @Bean
    public CacheManager cacheManager()
    {
        // Takes care of default configurations.
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        // Overriding default TTL, value serializer
        config = config
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer()))
            .entryTtl(Duration.ofSeconds(productDataExpiry)).disableCachingNullValues();

        // Create CacheManager with Jedis Connection Factory
        final RedisCacheManager cacheManager = RedisCacheManagerBuilder.fromConnectionFactory(jedisConnectionFactory())
            .cacheDefaults(config).build();
        return cacheManager;
    }

    @Override
    public CacheErrorHandler errorHandler()
    {
        return new CustomCacheErrorHandler();
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        final JedisClientConfiguration jedisClientConfig =
            JedisClientConfiguration.builder().usePooling().poolConfig(jedisPoolConfig()).build();
        final JedisConnectionFactory factory =
            new JedisConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort), jedisClientConfig);
        return factory;
    }

    @Bean
    JedisPoolConfig jedisPoolConfig() {
        final JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(200);;
        return jedisPoolConfig;
    }

    @Bean
    GenericJackson2JsonRedisSerializer jsonRedisSerializer()
    {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> redisTemplate = new LoggingRedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(jsonRedisSerializer());
        redisTemplate.setHashKeySerializer(jsonRedisSerializer());
        redisTemplate.setValueSerializer(jsonRedisSerializer());
        return redisTemplate;
    }
}
