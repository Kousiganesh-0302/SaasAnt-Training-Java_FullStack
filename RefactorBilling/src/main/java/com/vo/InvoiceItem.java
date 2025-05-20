package com.vo;

public class InvoiceItem {
    private final Product product;
    private final double quantity;

    public InvoiceItem(Product product, double quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }

    public double getTaxAmount() {
        return getSubtotal() * product.getTaxPercentage() / 100;
    }

    public double getTotal() {
        return getSubtotal() + getTaxAmount();
    }

    public double getTaxPercentage() {
        return product.getTaxPercentage();
    }
}
