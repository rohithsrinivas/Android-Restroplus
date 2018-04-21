package com.restroplus.interactor;

import com.restroplus.model.Item;
import com.restroplus.view.BookingPageView;

import java.util.List;

/**
 * Created by M1037764 on 12/20/2017.
 */

public class BookingPageInteractor {

    BookingPageView bookingPageView;

    public BookingPageInteractor(BookingPageView bookingPageView) {
        this.bookingPageView = bookingPageView;
    }

    public void saveCartDetails(List<Item> itemsInCart){
        // TODO: 12/21/2017 trigger a network call which will save the Cart entity with the userId and the list of items that the user added into the cart
    }
}
