package com.incomediscovery.service;

import com.incomediscovery.model.Product;
import org.junit.jupiter.api.*;

import java.util.NavigableMap;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private ProductService productService  = new ProductService("noname");

    @Test
    void testGetProductTreeMap() {
              assertAll(
                ()->assertNotNull(productService),
                ()->assertNotNull(productService.getProductTreeMap()),
                ()->assertEquals(14, productService.getProductTreeMap().size()),
                ()->assertEquals("017082112774", productService.getProductTreeMap().firstKey()),
                ()->assertEquals("305732450421", productService.getProductTreeMap().lastKey())
        );
    }

    @Test
    void testGetProductsByPrefix() {
        assertAll(
                ()->assertTrue(productService.getProductsByPrefix("000").isEmpty()),
                ()->assertTrue(productService.getProductsByPrefix("ABC").isEmpty()),
                ()->assertFalse(productService.getProductsByPrefix("").isEmpty()),
                ()->assertFalse(productService.getProductsByPrefix("0").isEmpty()),
                ()->assertEquals(1, productService.getProductsByPrefix("017").size()),
                ()->assertEquals(3, productService.getProductsByPrefix("051").size()),
                ()->assertEquals(14, productService.getProductsByPrefix("").size())
        );
    }
}