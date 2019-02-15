package com.tech.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.tech.demo.cache.constants.CacheConstants;
import com.tech.demo.domain.Product;

/**
 * Service to demo on cache annotations - {@link Cacheable}, {@link CacheEvict} and {@link CachePut}.
 *
 * @author agarwarj
 * @version 1.0
 * @date Dec 31, 2018
 */
@Component
public class DemoService implements InitializingBean
{
    private static final String BADMINTON = "Badminton";
    private final Logger LOGGER = LoggerFactory.getLogger(DemoService.class);
    private Map<String, Product> productMap = null;

    @Override
    public void afterPropertiesSet() throws Exception
    {
        LOGGER.debug("Initialize DemoService product map");
        productMap = new HashMap<>();
        productMap.put(BADMINTON.toLowerCase(), new Product(BADMINTON, 100));
        LOGGER.info("Initialized- Product Map : {}", productMap);

    }

    /**
     * returns product available in product map by name.
     *
     * @param name
     * @return {@link Integer}
     */
    @Cacheable(value = CacheConstants.PRODUCT_DATA, key = "{#name}")
    public Product fetchProductByName(final String name)
    {
        LOGGER.debug("fetch Product by Name :{}", name);
        final Optional<String> optionalName = Optional.ofNullable(name);

        final Product inventory = productMap.get(optionalName.map(k -> {
            return k.toLowerCase();
        }).orElse(null));
        final Optional<Product> optionalInv = Optional.ofNullable(inventory);
        return optionalInv.orElse(new Product("NoProduct", 0));
    }

    /**
     * Updates inventory of product specified by product name to constant value (200).
     * 
     * @param name
     */
    @CacheEvict(value = CacheConstants.PRODUCT_DATA, key = "{#name}")
    public void updateInventory(final String name) {
        LOGGER.debug("update inventory of Product by Name :{}", name);
        final Optional<String> optionalName = Optional.ofNullable(name);

        final Product inventory = productMap.get(optionalName.map(k -> {
            return k.toLowerCase();
        }).orElse(null));
        inventory.setAvailableCount(200);
        LOGGER.info("Updated- Product Map : {}", productMap);
    }

    /**
     * Adds/Initialises product specified by given name in product map with default (100) inventory.
     * 
     * @param name
     * @return {@link Product}
     */
    @CachePut(value = CacheConstants.PRODUCT_DATA, key = "{#name}")
    public Product addProduct(final String name) {
        LOGGER.debug("Add inventory of Product by Name :{}", name);
        final Optional<String> optionalName = Optional.ofNullable(name);
        String lowerCaseName = optionalName.map(k -> {
            return k.toLowerCase();
        }).orElse(null);
        productMap.put(lowerCaseName, new Product(lowerCaseName, 100));
        LOGGER.info("Added- Product Map : {}", productMap);
        return productMap.get(lowerCaseName);
    }

}
