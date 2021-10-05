package com.incomediscovery.utility;

import com.incomediscovery.model.Product;

import java.io.*;
import java.util.Arrays;
import java.util.TreeMap;

public class ProductDataUploader {
    private BufferedReader csvFileReader;

    public  ProductDataUploader(String filename){
            readDefaultProductInfoFile(filename);
    }

    private void readDefaultProductInfoFile(String filename) {
        try {
            csvFileReader = new BufferedReader(new FileReader(filename));
            System.out.println("The supplied Product Data File has been found...\n");
        }
        catch (FileNotFoundException fnfe1) {
            System.out.println("The supplied Product Data File has not been found...\nLooking for the default Product Data File");
            String defaultProductInfoFileURL = getClass().getClassLoader().getResource("defaultProductFile.csv").toString();
            String fileURLprefix = "file:/";
            filename = defaultProductInfoFileURL.substring(defaultProductInfoFileURL.indexOf(fileURLprefix)+fileURLprefix.length());

            try {
                csvFileReader = new BufferedReader(new FileReader(filename));
                System.out.println("The default Product Data file '" + filename + "' has been found.");
            } catch (FileNotFoundException fnfe2) {
                System.out.println("The default Product Data file '" + filename + "' has not been found. \nExiting...");
                System.exit(1);
            }
        }
        catch(NullPointerException npe){
            System.err.println("Null pinter exception occurred while getting default Data File");
        }
    }

    public final TreeMap<String, Product> populateProductList(){
        TreeMap<String, Product> productMap = new TreeMap<>();

        try{
           csvFileReader.lines().forEach(recordString-> {
               if (recordString != null) {
                   String[] recordArr = recordString.split(",");
                   recordArr = validate(recordArr);
                   if (recordArr != null)
                       productMap.put(recordArr[0], new Product(recordArr));
                   else
                       System.err.println("Bad record found in data file: " + Arrays.toString(recordArr));
               }
           });
        }
        catch(UncheckedIOException ioe){
            System.out.println("Error reading the Product Data file.\n");
        }
        finally{
            try{
                if (csvFileReader != null)
                    csvFileReader.close();
            }
            catch(IOException ioe){
                System.out.println("Error closing the Product Data file. This may cause a memory leak.\n");
            }
        }
        return productMap;
    }

    String[] validate(String[] recordArr){

        if (recordArr == null || recordArr.length < 4)
            return null;
        for (int i = 0; i < 4 ; i++) {
            if (recordArr[i] == null)
                return null;
            recordArr[i] = recordArr[i].trim();
        }
        if ( isValidId(recordArr[0]) && isValidName(recordArr[1])
                && isValidPrice(recordArr[2]) && isValidCategory(recordArr[3]))
            return recordArr;
        else
            return null;
    }

    boolean isValidId(String id){
        return id !=null && id.matches("\\d{12}");
    }

    boolean isValidName(String name){
        return name != null && !name.isEmpty();
    }

    boolean isValidPrice(String priceString){
        try {
            Double.parseDouble(priceString);
            return true;
        }
        catch(Exception nfe){
            return false;
        }
    }

    boolean isValidCategory(String category){
        return Constants.productCategoryMap.containsKey(category);
    }

}
