package com.incomediscovery.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDataUploaderTest {

    ProductDataUploader productDataUploader = new ProductDataUploader("noname");

    @Test
    void testValidateGoodRecord() {
        String stringRecord = "305730184328,ADVIL COLD GEL,6.99,nd";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertAll(
                ()->assertEquals("305730184328", record[0]),
                ()->assertEquals("ADVIL COLD GEL", record[1]),
                ()->assertEquals("6.99", record[2]),
                ()->assertEquals("nd", record[3])
        );
    }

    @Test
    void testValidateGoodRecordWhiteSpace() {
        String stringRecord = "305730184328 ,ADVIL COLD GEL , 6.99, nd ";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertAll(
                ()->assertEquals("305730184328", record[0]),
                ()->assertEquals("ADVIL COLD GEL", record[1]),
                ()->assertEquals("6.99", record[2]),
                ()->assertEquals("nd", record[3])
        );
    }

    @Test
    void testValidateGoodRecordTooManyColumns() {
        String stringRecord = "305730184328 ,ADVIL COLD GEL , 6.99, nd , HERE, AM, I";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertAll(
                ()->assertEquals("305730184328", record[0]),
                ()->assertEquals("ADVIL COLD GEL", record[1]),
                ()->assertEquals("6.99", record[2]),
                ()->assertEquals("nd", record[3])
        );
    }
    @Test
    void testValidateTooShortToLongIDRecord() {
        String stringRecord = "30573018432,ADVIL COLD GEL,6.99,nd";
        String[] record = productDataUploader.validate(stringRecord.split(","));
        assertNull(record);

        stringRecord = "3057301843289,ADVIL COLD GEL,6.99,nd";
        record = productDataUploader.validate(stringRecord.split(","));
        assertNull(record);
    }

    @Test
    void testValidateEmptyNameRecord() {
        String stringRecord = "305730184328,,6.99,nd";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertNull(record);
    }

    @Test
    void testValidateBadPriceRecord() {
        String stringRecord = "305730184328,ADVIL COLD GEL,6.c9e,nd";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertNull(record);
    }

    @Test
    void testValidateBadTaxCategoryRecord() {
        String stringRecord = "305730184328,ADVIL COLD GEL,6.99,n";
        String[] record = productDataUploader.validate(stringRecord.split(","));

        assertNull(record);
    }


    @Test
    void isValidId() {
        assertAll(
                ()->assertTrue(productDataUploader.isValidId("305730184328")),
                ()->assertFalse(productDataUploader.isValidId("30573018432")),
                ()->assertFalse(productDataUploader.isValidId("30573018432899")),
                ()->assertFalse(productDataUploader.isValidId("3V5730184328")),
                ()->assertFalse(productDataUploader.isValidId("305730184.38"))
        );
    }

    @Test
    void isValidName() {
        assertAll(
                ()->assertFalse(productDataUploader.isValidName("")),
                ()->assertFalse(productDataUploader.isValidName(null)),
                ()->assertTrue(productDataUploader.isValidName("3")),
                ()->assertTrue(productDataUploader.isValidName("THE COW JUMPED OVER THE MOON")),
                ()->assertTrue(productDataUploader.isValidName("305730184.38"))
        );
    }

    @Test
    void isValidPrice() {
        assertAll(
                ()->assertFalse(productDataUploader.isValidPrice("")),
                ()->assertFalse(productDataUploader.isValidPrice(null)),
                ()->assertTrue(productDataUploader.isValidPrice("3")),
                ()->assertFalse(productDataUploader.isValidPrice("THE COW JUMPED OVER THE MOON")),
                ()->assertTrue(productDataUploader.isValidPrice("305.38")),
                ()->assertTrue(productDataUploader.isValidPrice(".38")),
                ()->assertTrue(productDataUploader.isValidPrice("305.")),
                ()->assertTrue(productDataUploader.isValidPrice("305730184.38"))
        );
    }

    @Test
    void isValidCategory() {
        assertAll(
                ()->assertFalse(productDataUploader.isValidCategory("")),
                ()->assertFalse(productDataUploader.isValidCategory(null)),
                ()->assertFalse(productDataUploader.isValidCategory("O")),
                ()->assertFalse(productDataUploader.isValidCategory("G")),
                ()->assertFalse(productDataUploader.isValidCategory("PD")),
                ()->assertTrue(productDataUploader.isValidCategory("pd")),
                ()->assertTrue(productDataUploader.isValidCategory("g")),
                ()->assertFalse(productDataUploader.isValidCategory("0")),
                ()->assertTrue(productDataUploader.isValidCategory("o")),
                ()->assertTrue(productDataUploader.isValidCategory("pf")),
                ()->assertTrue(productDataUploader.isValidCategory("nd")),
                ()->assertTrue(productDataUploader.isValidCategory("pd")),
                ()->assertTrue(productDataUploader.isValidCategory("c"))
        );
    }
}