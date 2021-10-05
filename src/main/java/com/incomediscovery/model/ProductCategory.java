package com.incomediscovery.model;

public enum ProductCategory {
    CLOTHING("c"),
    GROCERIES("g"),
    NON_PRESCRIPTION_DRUGS("nd"),
    PRESCRIPTION_DRUGS("pd"),
    PREPARED_FOOD("pf"),
    OTHER("o");

    private final String text;

    ProductCategory(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
