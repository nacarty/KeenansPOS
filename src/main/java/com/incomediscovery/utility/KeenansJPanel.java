package com.incomediscovery.utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class KeenansJPanel extends JPanel {
   public  static JLabel searchLabel, itemHeaderLabel, itemPriceLabel, scrollpaneLabel, PurchaseListLabel;
   public static JTextField searchField, itemNameField, itemPriceField;
    public  static JButton searchButton, addItemButton, cancelButton, checkoutButton, cashButton;
    public static SpinnerModel spinnerModel, cashSpinnerModel;
    public static JSpinner numberEditor, cashEditor;
    public static DefaultTableModel productTblModel, purchaseListTblModel;
    public static JTable productTable, purchaseListTbl;
    public static JScrollPane productsScrollPane, purchaseListScrollPane;
    public static TableColumnModel productTableColumnModel, purchaseListTblColumnModel;

    public KeenansJPanel() {
        super();
        setLayout(null);

        searchLabel = new JLabel("Search Item by Code");
        searchLabel.setBounds(10, 20, 120, 25);
        add(searchLabel);
        searchField = new JTextField();
        searchField.setBounds(140, 20, 120, 25);
        searchField.setBackground(Color.WHITE);
        add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(270, 20, 80, 25);
        searchButton.setBackground(Color.GRAY);
        add(searchButton);

        itemHeaderLabel = new JLabel("Item Name");
        itemHeaderLabel.setBounds(10, 60, 120, 25);
        add(itemHeaderLabel);

        itemPriceLabel = new JLabel("Price");
        itemPriceLabel.setBounds(180, 60, 120, 25);
        add(itemPriceLabel);

        itemNameField = new JTextField();
        itemNameField.setBounds(10, 80, 160, 25);
        itemNameField.setBackground(Color.WHITE);
        add(itemNameField);

        itemNameField.setText("");
        itemNameField.setEditable(false);

        itemPriceField = new JTextField();
        itemPriceField.setBounds(180, 80, 50, 25);
        itemPriceField.setBackground(Color.WHITE);
        add(itemPriceField);

        itemPriceField.setText("0.00");
        itemPriceField.setEditable(false);

        spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
        numberEditor = new JSpinner(spinnerModel);
        numberEditor.setBounds(240, 80, 40, 25);
        numberEditor.setBackground(Color.WHITE);
        add(numberEditor);

        addItemButton = new JButton("Add");
        addItemButton.setBounds(290, 80, 60, 25);
        addItemButton.setBackground(Color.GRAY);
        add(addItemButton);

        scrollpaneLabel = new JLabel("Looked-up Products");
        scrollpaneLabel.setBounds(10, 120, 300, 25);
        add(scrollpaneLabel);

        productTblModel = new DefaultTableModel();
        for (String columnname : Constants.productLookUpColumnNames)
            productTblModel.addColumn(columnname);

        productTable = new JTable(productTblModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productsScrollPane = new JScrollPane();
        productsScrollPane.setBounds(10, 145, 340, 200);
        productTable.setSelectionBackground(Color.GREEN);
        productsScrollPane.setViewportView(productTable);
        add(productsScrollPane);

        productTableColumnModel = productTable.getColumnModel();
        productTableColumnModel.getColumn(1).setPreferredWidth(240);

        PurchaseListLabel = new JLabel("Items Purchased");
        PurchaseListLabel.setBounds(10, 375, 200, 25);
        add(PurchaseListLabel);

        purchaseListTblModel = new DefaultTableModel();
        for (String columnname : Constants.receiptColumnNames)
            purchaseListTblModel.addColumn(columnname);

        purchaseListTbl = new JTable(purchaseListTblModel);
        purchaseListTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        purchaseListScrollPane = new JScrollPane();
        purchaseListScrollPane.setBounds(10, 405, 560, 400);
        purchaseListTbl.setSelectionBackground(Color.GREEN);
        purchaseListScrollPane.setViewportView(purchaseListTbl);
        purchaseListTbl.setLayout(null);
        add(purchaseListScrollPane);

        purchaseListTblColumnModel = purchaseListTbl.getColumnModel();
        purchaseListTblColumnModel.getColumn(0).setPreferredWidth(170);
        purchaseListTblColumnModel.getColumn(1).setPreferredWidth(90);
        purchaseListTblColumnModel.getColumn(2).setPreferredWidth(70);
        purchaseListTblColumnModel.getColumn(3).setPreferredWidth(70);
        purchaseListTblColumnModel.getColumn(4).setPreferredWidth(70);
        purchaseListTblColumnModel.getColumn(5).setPreferredWidth(30);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(460, 20, 100, 25);
        cancelButton.setBackground(Color.YELLOW);
        add(cancelButton);

        checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(460, 80, 100, 25);
        checkoutButton.setBackground(Color.ORANGE);
        add(checkoutButton);

        cashSpinnerModel = new SpinnerNumberModel(1, 0.1, Integer.MAX_VALUE, 0.01);
        cashEditor = new JSpinner(cashSpinnerModel);
        cashEditor.setBounds(460, 140, 100, 25);
        cashEditor.setBackground(Color.WHITE);
        cashEditor.setVisible(false);
        add(cashEditor);

        cashButton = new JButton("Pay($)");
        cashButton.setBounds(460, 164, 100, 25);
        cashButton.setBackground(Color.ORANGE);
        cashButton.setVisible(false);
        add(cashButton);
    }

}
