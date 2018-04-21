package com.restroplus.view;

import com.restroplus.model.Booking;
import com.restroplus.model.Item;
import com.restroplus.model.Restaurant;

/**
 * Created by M1037764 on 12/22/2017.
 */

public interface CartPageView {


    void showItemsInCart();

    void showAmountPayable();

    void showBookingPlacedMessage();

    void showSomethingWrongMessage();

    void navigateToBookingConfirmationPage(Booking booking);

    void showMessageForItemRemoved();

    void updateItemDetailsInSelectedRestaurant(Item itemToBeUpdated);

    void showRandomMessage();

    void updateBillingAmount();

    void showProgressBar(Boolean showProgressBar);

    void showCartEmptyMessage();

    void navigateBackToBookingPage();

    void showMessageForNoInternetConnection();

    void showServerDownMessage();
}
