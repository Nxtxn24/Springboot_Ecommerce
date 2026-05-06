package com.example.app.demo.orderItem;

public class OrderItemDto {

    private Long productId;
    private String productName;
    private double priceAtPurchase;
    private int quantity;
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }
    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // getters + setters

    
}
