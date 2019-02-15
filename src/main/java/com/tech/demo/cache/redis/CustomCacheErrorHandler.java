package com.tech.demo.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

/**
 * Redis Error handler.
 *
 * @author agarwarj
 * @version 1.0
 * @date Jan 1, 2019
 */
public class CustomCacheErrorHandler implements CacheErrorHandler
{

    private static final Logger logger = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheClearError(final RuntimeException exception, final Cache cache)
    {
        logger.debug("handleCacheClearError: cache name:{},error msg : {} ", cache.getName(), exception.getMessage());

    }

    @Override
    public void handleCacheEvictError(final RuntimeException exception, final Cache cache, final Object key)
    {
        logger.debug(
                     "handleCacheEvictError: cache name:{}, key:{},error msg : {} ",
                     cache.getName(),
                     key,
                exception.getMessage(), exception);

    }

    @Override
    public void handleCacheGetError(final RuntimeException exception, final Cache cache, final Object key)
    {
        logger.debug(
                     "handleCacheGetError: cache name:{}, key:{},error msg : {}",
                     cache.getName(),
                     key,
                exception.getMessage(), exception);
    }

    @Override
    public void handleCachePutError(final RuntimeException exception, final Cache cache, final Object key, final Object value) {
        logger.debug("handleCachePutError: cache name:{}, key:{},error msg : {}", cache.getName(), key, value,
                exception.getMessage(), exception);

    }

}

