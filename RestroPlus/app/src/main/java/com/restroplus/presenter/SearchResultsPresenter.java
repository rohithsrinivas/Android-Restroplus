package com.restroplus.presenter;

import com.restroplus.view.SearchResultsView;

/**
 * Created by M1037764 on 12/18/2017.
 */

public class SearchResultsPresenter {

    private SearchResultsView searchResultsView;

    public SearchResultsPresenter(SearchResultsView searchResultsView) {
        this.searchResultsView = searchResultsView;
    }

    public void onPageLoad(){
        this.searchResultsView.displayRestaurantResults();
    }
}
