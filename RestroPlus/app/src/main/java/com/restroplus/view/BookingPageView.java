package com.restroplus.view;

import android.widget.ImageButton;

import com.restroplus.model.Item;

import java.util.List;

/**
 * Created by M1037764 on 12/20/2017.
 */

public interface BookingPageView {

    void showClickedItemDetails(Item item);

    void showFullRestaurantDetails();

    void showAlertDialogForQuantityAndGetUserInput();

    void setSelectedItemDetails(Item item);

    void showItemAddedToCartMessageAndChangeCartIcon();

    void showNoItemsInCartMessage();

    void navigateToCartPage();

    void setSelectedButton(ImageButton addButton);

    void updateCartContents(List<Item> itemsInCart);
}
