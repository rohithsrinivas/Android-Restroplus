package com.restroplus.presenter;

import com.restroplus.activity.BookingConfirmation;
import com.restroplus.model.Booking;
import com.restroplus.view.BookingConfirmationView;

/**
 * Created by M1037764 on 1/2/2018.
 */

public class BookingConfirmationPresenter {

    private BookingConfirmationView bookingConfirmationView;

    public BookingConfirmationPresenter(BookingConfirmationView bookingConfirmationView) {
        this.bookingConfirmationView = bookingConfirmationView;
    }

    public void onPageLoad(Booking booking) {
        this.bookingConfirmationView.showBookingDetails(booking);
    }

    public void onDoneButtonClick() {
        this.bookingConfirmationView.navigateToOpeningPage();
    }
}
