package com.incomediscovery.model;

import java.util.Locale;

public class PurchaseItem {

    private final Product product;
    private Integer quantity;

    //It is computationally better to calculate these taxes during the transaction that at the end
    private Double preTaxCost;
    private Double stateTax;
    private Double countyTax;
    private Double cityTax;
    private Double finalCost;

    public PurchaseItem(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
        calculateCosts();
    }

    @Override
    public String toString(){
       return  String.format(Locale.US,"%1$-20s  %2$-16s %4$7.2fx%3$d=%5$7.2f  %6$s",
               product.getName(), product.getId(), quantity,product.getPrice(),preTaxCost,product.getCategory().toString());

    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPreTaxCost() {
        return preTaxCost;
    }

    public Double getStateTax() {
        return stateTax;
    }

    public Double getCountyTax() {
        return countyTax;
    }

    public Double getCityTax() {
        return cityTax;
    }

    public Double getFinalCost() {
        return finalCost;
    }

    private void calculateCosts(){
        preTaxCost = product.getPrice()*quantity;
        cityTax = TaxCategory.CITY.getTaxRate()*quantity;

        switch(product.getCategory()){
            case GROCERIES:
                stateTax = 0.0;
                countyTax = 0.0;
                break;
            default:
                stateTax = TaxCategory.STATE.getTaxRate()*quantity;
                countyTax = TaxCategory.COUNTY.getTaxRate()*quantity;
        }
        finalCost = preTaxCost + stateTax + countyTax + cityTax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PurchaseItem other = (PurchaseItem) o;
        return product.equals(other.getProduct());
    }

    @Override
    public int hashCode() {
        return product.hashCode();
    }
}
