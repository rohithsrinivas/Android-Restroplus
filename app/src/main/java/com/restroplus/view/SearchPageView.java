package com.restroplus.view;

import com.restroplus.model.Restaurant;

import java.util.List;

/**
 * Created by M1037764 on 12/15/2017.
 */

public interface SearchPageView {

    void showErrorMessageForEmptyOrInvalidFields();

    void getResultsFromServiceAndNavigateToResultsPage();

    void showJsonInToast(List<Restaurant> restaurants);

    void showNoRestaurantsFoundMessage();

    void navigateToSearchResultsPage(List<Restaurant> restaurants);

    void showProgressBar(Boolean showProgressBar);

    void dismissProgressBar();

    void showServerDownMessage();

    void showSomethingWrongMessage();

    void showRandomMessage();

    void showMessageForNoInternetConnection();
}
