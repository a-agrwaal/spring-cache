package com.tech.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tech.demo.domain.Product;
import com.tech.demo.service.DemoService;

/**
 * <Your comments for this class/interface>
 *
 * @author agarwarj
 * @version 1.0
 * @date Dec 31, 2018
 */
@RestController
public class DemoController
{
    private final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private DemoService demoService;

    @GetMapping("/product/{name}")
    private Product fetchProductByName(@PathVariable("name") final String productName)
    {

        final Product product = demoService.fetchProductByName(productName);
        LOGGER.debug("Found Product:{}", product);
        return product;
    }

    @PostMapping("/inventory/{name}")
    private String updateInventory(@PathVariable("name") final String productName) {

        demoService.updateInventory(productName);
        return "Success!";

    }

    @PostMapping("/product/add/{name}")
    private Product addProduct(@PathVariable("name") final String productName) {

        return demoService.addProduct(productName);

    }
}
