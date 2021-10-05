package com.incomediscovery.service;

import com.incomediscovery.model.Product;
import com.incomediscovery.model.PurchaseItem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NavigableMap;

import static org.junit.jupiter.api.Assertions.*;

class CashingServiceTest {

    CashingService cashingService = new CashingService("noname");
    double epsilon = 0.000001d;

    @BeforeEach
    void setUp() {
        cashingService.searchProducts("051");
        NavigableMap<String, Product> searchList = cashingService.getSearchList();

        if (!searchList.isEmpty()) {
           cashingService.addToShoppingCart(searchList.firstEntry().getValue(), 10);
           cashingService.addToShoppingCart(searchList.lastEntry().getValue(), 20);
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isShoppingCartEmpty() {
        assertFalse(cashingService.isShoppingCartEmpty());
        CashingService cashingService = new CashingService("noname");
        assertTrue(cashingService.isShoppingCartEmpty());
    }

    @Test
    void testIsCashEnough() {
        cashingService.tallyPurchases();
        assertAll(
                ()->assertFalse(cashingService.isCashEnough(5.0)),
                ()->assertFalse(cashingService.isCashEnough(118.70)),
                ()->assertTrue(cashingService.isCashEnough(150.0))
        );
    }

    @Test
    void testTallyPurchases() {
        Double[] totals = cashingService.tallyPurchases();

        for (double d:totals)
            System.out.println(d);

        assertAll(
                ()->assertNotNull(totals),
                ()->assertTrue( Math.abs(118.70 - totals[0]) < epsilon ),
                ()->assertTrue(Math.abs(0.0 - totals[1]) < epsilon),
                ()->assertTrue(Math.abs(0.0 - totals[2]) < epsilon),
                ()->assertTrue(Math.abs(0.60 - totals[3]) < epsilon),
                ()->assertTrue(Math.abs(119.30 - totals[4]) < epsilon),
                ()->assertTrue(Math.abs(0.0 - totals[5]) < epsilon),
                ()->assertTrue(Math.abs(0.0 - totals[6]) < epsilon)
        );

    }

    @Test
    void testMakeChange() {

        cashingService.tallyPurchases();
        Double[] totals = cashingService.makeChange(150.0);

        assertAll(
                ()->assertTrue(Math.abs(150 - totals[5]) < epsilon),
                ()->assertTrue(Math.abs(30.70 - totals[6]) < epsilon)
        );
    }

    @Test
    void testResetTransaction() {
        cashingService.resetTransaction();
        assertAll(
                ()->assertTrue(cashingService.isShoppingCartEmpty()),
                ()->assertTrue(cashingService.getSearchList().isEmpty()),
                ()->assertFalse(cashingService.getCheckedOut())
        );
    }

    @Test
    void testAddToShoppingCart() {
        NavigableMap<String, Product> searchList = cashingService.getSearchList();
        Product product = searchList.firstEntry().getValue();
        cashingService.addToShoppingCart(product, 1);
        List<PurchaseItem> shoppingCart = cashingService.getShoppingCart();

        assertAll(
                ()->assertFalse(shoppingCart.isEmpty()),
                ()->assertTrue(shoppingCart.get(shoppingCart.size()-1).getProduct().equals(product))
        );
    }

    @Test
    void testSearchProduct(){

        NavigableMap<String, Product> searchList = cashingService.searchProducts("");

        assertEquals(14,searchList.size());
        assertEquals("017082112774", searchList.firstEntry().getKey());

        searchList = cashingService.searchProducts("XYZ");
        assertTrue(searchList.isEmpty());

        searchList = cashingService.searchProducts("017");
        assertEquals(1, searchList.size());

        searchList = cashingService.searchProducts("051");
        assertEquals(3, searchList.size());

        searchList = cashingService.searchProducts("3057");
        assertEquals(6, searchList.size());

    }
}