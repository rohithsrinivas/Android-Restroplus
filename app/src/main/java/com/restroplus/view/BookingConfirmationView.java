package com.restroplus.view;

import com.restroplus.model.Booking;

/**
 * Created by M1037764 on 1/2/2018.
 */

public interface BookingConfirmationView {

    void showBookingDetails(Booking booking);

    void navigateToOpeningPage();
}
