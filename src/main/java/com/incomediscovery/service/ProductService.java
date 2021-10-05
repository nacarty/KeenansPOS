package com.incomediscovery.service;

import com.incomediscovery.utility.Constants;
import com.incomediscovery.model.Product;
import com.incomediscovery.utility.ProductDataUploader;

import java.util.NavigableMap;
import java.util.TreeMap;

public class ProductService {
    private final TreeMap<String, Product> productTreeMap;

    public ProductService(String productFilename){
        productTreeMap = new ProductDataUploader(productFilename).populateProductList();

        if (productTreeMap == null || productTreeMap.isEmpty()){
            System.err.println("Error reading Data file. Exiting.....");
            System.exit(1);
        }

    }


   final NavigableMap getProductsByPrefix(String prefix){
        if (productTreeMap == null || productTreeMap.isEmpty())
            return null;
       return productTreeMap.subMap(prefix + Constants.ceilingSuffix, true,
                                                  prefix + Constants.floorSuffix, true);
    }

    TreeMap<String, Product> getProductTreeMap() {
        return productTreeMap;
    }
}