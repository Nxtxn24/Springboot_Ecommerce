

package com.example.app.demo.cart;

import lombok.Data;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class CartQuantityRequest {
    @PositiveOrZero(message = "Quantity cannot be negative")
    private int quantity;
}
