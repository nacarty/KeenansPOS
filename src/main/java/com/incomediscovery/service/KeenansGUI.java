package com.incomediscovery.service;

import com.incomediscovery.model.Product;
import com.incomediscovery.model.PurchaseItem;
import com.incomediscovery.utility.Constants;
import com.incomediscovery.utility.KeenansJPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.NavigableMap;

public class KeenansGUI extends JFrame {

    KeenansJPanel panel;
    private final CashingService cashingService;
    private Product chosenProduct;

    public KeenansGUI(String filename){
        super("      Welcome to Keenans Point of Sale...");

        cashingService = new CashingService(filename);

        setSize(600,880);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        panel = new KeenansJPanel();
        super.add(panel);


        setActionListeners();

        }

        void setActionListeners() {

            panel.searchButton.addActionListener(e -> {
                NavigableMap<String, Product> searchList = cashingService.searchProducts(panel.searchField.getText().trim());

                panel.productTblModel.setRowCount(0);

                if (searchList == null || searchList.isEmpty())
                    panel.searchField.setText("NO RESULTS");
                else if (searchList.size()==1) {
                    chosenProduct = searchList.firstEntry().getValue();
                    displaySelectedProduct(chosenProduct);
                }
                else{
                    panel.itemNameField.setText("");
                    panel.itemPriceField.setText("0.00");
                    addRowsToProductTable(searchList);
                }
            });

            panel.productTable.getSelectionModel().addListSelectionListener(event -> {
                int row = panel.productTable.getSelectedRow();
                if (row > -1) {
                    chosenProduct = cashingService.selectProduct(panel.productTable.getValueAt(row, 0).toString());
                    panel.itemNameField.setText(chosenProduct.getName());
                    panel.itemPriceField.setText(String.format("%7.2f",chosenProduct.getPrice()));
                }
            });

            panel.addItemButton.addActionListener(e -> {
                if (!cashingService.getCheckedOut() && panel.itemNameField.getText() != null && !panel.itemNameField.getText().isEmpty()) {

                    PurchaseItem item = cashingService.addToShoppingCart(chosenProduct, (Integer) panel.numberEditor.getValue());
                    addItemToPurchaseList(panel.purchaseListTblModel, item);
                }
            });

            panel.cancelButton.addActionListener(e -> {
                panel.itemNameField.setText("");
                panel.itemPriceField.setText("0.00");
                panel.searchField.setText("");
                panel.numberEditor.setValue(1);
                panel.cashEditor.setValue(0.0);
                panel.productTblModel.setRowCount(0);
                panel.purchaseListTblModel.setRowCount(0);
                cashingService.resetTransaction();
                panel.cashEditor.setVisible(false);
                panel.cashButton.setVisible(false);
                panel.cancelButton.setText("Cancel");});

            panel.checkoutButton.addActionListener(e -> {
            if (!cashingService.getCheckedOut() && !cashingService.isShoppingCartEmpty()){
                panel.itemNameField.setText("");
                panel.itemPriceField.setText("0.00");
                panel.searchField.setText("");
                panel.productTblModel.setRowCount(0);
                panel.cashEditor.setVisible(true);
                panel.cashButton.setVisible(true);
                Double[] totalCostArray = cashingService.tallyPurchases();

                addTotalLineToPurchaseList(panel.purchaseListTblModel, "", "");

                for (int i = 0; i <5; i++)
                    addTotalLineToPurchaseList(panel.purchaseListTblModel, Constants.receiptAmountLabels[i],
                            String.format("%7.2f",totalCostArray[i]));
            }
          });

            panel.cashButton.addActionListener(e -> {
                Double cashAmount = (Double)panel.cashEditor.getValue();

                if (!cashingService.isShoppingCartEmpty() && cashingService.isCashEnough(cashAmount)) {
                    Double[] totalCostArray = cashingService.makeChange(cashAmount);
                    addTotalLineToPurchaseList(panel.purchaseListTblModel, Constants.receiptAmountLabels[5], String.format("%7.2f",totalCostArray[5]));
                    addTotalLineToPurchaseList(panel.purchaseListTblModel, Constants.receiptAmountLabels[6], String.format("%7.2f",totalCostArray[6]));

                    panel.cancelButton.setText("FINISH");
                }
                else
                    panel.cashEditor.setValue(0.0);
            });
        }

    final void addTotalLineToPurchaseList(DefaultTableModel tableModel, String label, String amount){
        String[] record = new String[6];

        record[0] = "";
        record[1] = "";
        record[2] = "";
        record[3] = label;
        record[4] = amount;
        record[5] = "";
        tableModel.insertRow(tableModel.getRowCount(), record);
    }

    final void addItemToPurchaseList(DefaultTableModel tableModel, PurchaseItem item){
        String[] record = new String[6];

        record[0] = item.getProduct().getName();
        record[1] = item.getProduct().getId();
        record[2] = String.format("%7.2f", item.getProduct().getPrice());
        record[3] = ""+item.getQuantity();
        record[4] = String.format("%7.2f",item.getPreTaxCost());
        record[5] = item.getProduct().getCategory().toString();

        tableModel.insertRow(tableModel.getRowCount(), record);
    }

    final void addRowsToProductTable(NavigableMap<String, Product> searchList){
        String[] record = new String[2];
        for (Product product: searchList.values()){
            record[0] = product.getId();
            record[1] = product.getName();
            panel.productTblModel.insertRow(panel.productTblModel.getRowCount(), record);
        }
    }

    void displaySelectedProduct(Product product){
        panel.itemNameField.setText(product.getName());
        panel.itemPriceField.setText(String.format("%7.2f", product.getPrice()));
    }

}
