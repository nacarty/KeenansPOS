package com.incomediscovery.model;

public enum TaxCategory {
    CITY(0.02),
    COUNTY(0.007),
    STATE(0.063);

    private double taxRate;

    TaxCategory(double taxRate){
        this.taxRate = taxRate;
    }

    public double getTaxRate(){
        return taxRate;
    }
}
