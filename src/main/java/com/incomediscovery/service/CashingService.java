package com.incomediscovery.service;

import com.incomediscovery.model.Product;
import com.incomediscovery.model.PurchaseItem;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CashingService {

    private final ProductService productService;
    private NavigableMap<String, Product> searchList;
    private List<PurchaseItem> shoppingCart;
    private Double[] totalCostArray;
    private Boolean checkedOut, paid;

    CashingService(String filename){
        productService = new ProductService(filename);
        shoppingCart = new ArrayList<>();
        totalCostArray = new Double[]{0.0, 0.0,0.0,0.0,0.0,0.0,0.0};
        checkedOut = false;

    }

    final boolean isShoppingCartEmpty() {
        return shoppingCart.isEmpty();
    }

    final boolean isCashEnough(Double cash) {
        return cash >= totalCostArray[4] ;
    }

    final  Boolean getCheckedOut() {
        return checkedOut;
    }


    public NavigableMap<String, Product> getSearchList() {
        return searchList;
    }

    final Double[] tallyPurchases(){
        shoppingCart.forEach(purchaseItem -> {
            totalCostArray[0] += purchaseItem.getPreTaxCost();
            totalCostArray[1] += purchaseItem.getStateTax();
            totalCostArray[2] += purchaseItem.getCountyTax();
            totalCostArray[3] += purchaseItem.getCityTax();
            totalCostArray[4] += purchaseItem.getFinalCost();
        });

        checkedOut = true;

        return totalCostArray;
    }

    final Double[] makeChange(Double cashAmount){
        totalCostArray[5] = cashAmount;
        totalCostArray[6] = cashAmount - totalCostArray[4];
        shoppingCart = new ArrayList<>();

        return totalCostArray;
    }

    final void resetTransaction(){
        searchList = new TreeMap<>();
        shoppingCart = new ArrayList<>();
        totalCostArray = new Double[]{0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        checkedOut = false;
    }

    final PurchaseItem addToShoppingCart(Product chosenProduct, int amount){
        PurchaseItem item = new PurchaseItem(chosenProduct, amount);
        shoppingCart.add(item);
        return item;
    }

    public List<PurchaseItem> getShoppingCart() {
        return shoppingCart;
    }

    final Product selectProduct(String productId){
        return searchList.get(productId);
    }

    final NavigableMap<String, Product> searchProducts(String prefix){
        searchList =  productService.getProductsByPrefix(prefix);
        return searchList;
    }

}
