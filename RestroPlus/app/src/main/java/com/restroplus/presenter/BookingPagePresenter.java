package com.restroplus.presenter;

import com.restroplus.interactor.BookingPageInteractor;
import com.restroplus.model.Item;
import com.restroplus.view.BookingPageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by M1037764 on 12/20/2017.
 */

public class BookingPagePresenter {

    private BookingPageView bookingPageView;

    private BookingPageInteractor bookingPageInteractor;

    public BookingPagePresenter(BookingPageView bookingPageView) {
        this.bookingPageView = bookingPageView;
        this.bookingPageInteractor = new BookingPageInteractor(this.bookingPageView);

    }

    public void onPageLoad(){
        this.bookingPageView.showFullRestaurantDetails();
    }

    public void addItemToCart(Item selectedItem, List<Item> itemsInCart) {
        itemsInCart.add(selectedItem);
        this.bookingPageView.showItemAddedToCartMessageAndChangeCartIcon();
    }

    public void onViewCartButtonClick(List<Item> itemsInCart){
        if(itemsInCart.size()==0)
            this.bookingPageView.showNoItemsInCartMessage();
        else
            this.bookingPageView.navigateToCartPage();
    }
}
