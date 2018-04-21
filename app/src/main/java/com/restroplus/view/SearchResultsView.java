package com.restroplus.view;

import com.restroplus.model.Restaurant;

/**
 * Created by M1037764 on 12/18/2017.
 */

public interface SearchResultsView {

    void displayRestaurantResults();

    void showClickedButtonDetails(String message);

    void navigateToBookingPage(Restaurant restaurant);
}
