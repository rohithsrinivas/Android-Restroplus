package com.restroplus.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by M1037764 on 12/21/2017.
 */

public class Cart implements Serializable {

    private List<Item> itemsInCart;

    private Integer userId;

    public Cart() {
    }

    public List<Item> getItemsInCart() {
        return itemsInCart;
    }

    public void setItemsInCart(List<Item> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
