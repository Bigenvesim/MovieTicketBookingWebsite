/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author Jason
 */
public class CartItem implements Serializable {

    private int itemId;
    private String movieName;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal subTotal;

    public BigDecimal getSubTotal() {
        return subTotal;
    }


    public CartItem(int itemId, String movieName, BigDecimal unitPrice, int quantity) {
        this.itemId = itemId;
        this.movieName = movieName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}