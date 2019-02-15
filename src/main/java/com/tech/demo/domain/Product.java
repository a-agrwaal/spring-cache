package com.tech.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <Your comments for this class/interface>
 *
 * @author agarwarj
 * @version 1.0
 * @date Dec 31, 2018
 */
@Data
@AllArgsConstructor
public class Product
{
    private String productName;
    private Integer availableCount;

}
