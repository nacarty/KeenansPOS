package com.incomediscovery.utility;

import com.incomediscovery.model.ProductCategory;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Constants {
   public static final Map<String, ProductCategory> productCategoryMap = Arrays.stream(ProductCategory.values())
                                                     .collect(Collectors.toMap(ProductCategory::toString, Function.identity()));
   public static final String[] productLookUpColumnNames = {"Product Id", "Description"};
   public static final String[] receiptColumnNames = {"Description", "Product Id", "Price", "Amt", "Total", "Tax"};
   public static final String floorSuffix = "999999999999";
   public static final String ceilingSuffix = "000000000000";
   public static final String[] receiptAmountLabels = {"Sub-Total", "State Tax", "County Tax", "City Tax", "Total", "Paid", "Change"};

}
