package com.incomediscovery.model;


import com.incomediscovery.utility.Constants;

import java.util.Objects;

public class Product implements Comparable<Product>{

    private final String id;
    private final String name;
    private final Double price;
    private final ProductCategory category;


    private Product(String id, String name, double price, String categoryText) {
        this.id = id;
        this.name = name;
        this.price = price;
        category = Constants.productCategoryMap.get(categoryText);
    }

    public Product(String[] record){
        this(record[0], record[1], Double.parseDouble(record[2]), record[3] );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public int compareTo(Product product){
        return id.compareTo(product.getId());
    }

    @Override
    public String toString() {
        return id + "     " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.substring(7));
    }
}
