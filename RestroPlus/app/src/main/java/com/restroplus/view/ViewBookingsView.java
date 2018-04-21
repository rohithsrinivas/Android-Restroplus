package com.restroplus.view;

import com.restroplus.model.Booking;

import java.util.List;

/**
 * Created by M1037764 on 1/4/2018.
 */

public interface ViewBookingsView {
    void showBookings(List<Booking> bookings);

    void showProgressBar(Boolean showProgressBar);

    void showBookingDetailsInDialog(Booking currentBooking);

    void showErrorMessageForSomethingWrong();

    void navigateToOpeningPage();

    void showServerDownMessage();

    void showNoBookingsMessage();
}
