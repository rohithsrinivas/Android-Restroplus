package com.restroplus.util;

import com.restroplus.model.Item;

import java.util.List;

/**
 * Created by M1037764 on 12/22/2017.
 */

public class PaymentUtils {

    public static Double calculateTotalAmountPayable(List<Item> listOfItemsInCart){
        Double amountPayable = 0D;
        for(Item item : listOfItemsInCart){
            amountPayable += (item.getPrice()*item.getQuantity());
        }
        return amountPayable;
    }

    public static Double calculateGSTForAmount(Double amount){
        return amount*0.18;
    }
}
