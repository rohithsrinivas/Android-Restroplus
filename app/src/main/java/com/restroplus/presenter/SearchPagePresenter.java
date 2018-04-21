package com.restroplus.presenter;

import android.content.Context;
import android.widget.Toast;

import com.restroplus.interactor.SearchPageInteractor;
import com.restroplus.model.Restaurant;
import com.restroplus.util.ConnectivityUtils;
import com.restroplus.util.ValidationUtil;
import com.restroplus.view.SearchPageView;

import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by M1037764 on 12/15/2017.
 */

public class SearchPagePresenter {

    SearchPageView searchPageView;

    SearchPageInteractor searchPageInteractor;

    public SearchPagePresenter(SearchPageView searchPageView) {
        this.searchPageView = searchPageView;
        this.searchPageInteractor = new SearchPageInteractor(searchPageView);
    }

    public void onGetRestaurantsButtonClick(String searchTerm, int numberOfTablesRequired, int selectedId,Context context) {
        if (ConnectivityUtils.isDeviceConnectedToInternet(context)) {
            if (searchPageInteractor.checkFieldsForValidInputs(searchTerm, numberOfTablesRequired, selectedId)) {
                this.searchPageView.showProgressBar(true);
                this.searchPageInteractor.fetchRestaurantsForSearch(searchTerm, numberOfTablesRequired, selectedId);
            } else {
                this.searchPageView.showErrorMessageForEmptyOrInvalidFields();
            }
        }
        else
        {
            this.searchPageView.showMessageForNoInternetConnection();
        }
    }






}
