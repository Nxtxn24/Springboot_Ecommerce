package com.example.app.demo.orderItem;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long productId;
    private String productName;
    private BigDecimal priceAtPurchase;
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
    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }
    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
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
