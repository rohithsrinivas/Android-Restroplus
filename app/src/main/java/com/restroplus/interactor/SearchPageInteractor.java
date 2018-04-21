package com.restroplus.interactor;

import android.annotation.SuppressLint;
import android.util.Log;

import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.model.Restaurant;
import com.restroplus.retrofit.BookingRestService;
import com.restroplus.retrofit.RetrofitClient;
import com.restroplus.util.ServerUtil;
import com.restroplus.util.ValidationUtil;
import com.restroplus.view.SearchPageView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by M1037764 on 12/16/2017.
 */

public class SearchPageInteractor {

    private Retrofit retrofitClient;

    private BookingRestService bookingRestService;

    private SearchPageView view;

    public SearchPageInteractor(SearchPageView view) {
        this.retrofitClient= RetrofitClient.buildRetrofitClient(RESTServiceConstants.BASE_URL_BOOKING_MICRO_SERVICE);
        this.view = view;
    }

    private void buildRetrofitForRestaurantService() {
        this.bookingRestService=this.retrofitClient.create(BookingRestService.class);
    }

    public boolean checkFieldsForValidInputs(String searchTerm, int numberOfTablesRequired, int selectedId){
        String searchBy = this.findSearchTerm(selectedId);
        if(ValidationUtil.checkForEmptyString(searchTerm) && numberOfTablesRequired>0 && !searchBy.equalsIgnoreCase("none")){
            return true;
        }
        else {
            return false;
        }
    }

    public String findSearchTerm(int selectedId){
        if(selectedId == 0 )
            return "byName";
        else if(selectedId == 1 )
            return "byAddress";
        else
            return "none";
    }


    public void fetchRestaurantsForSearch(String searchTerm, int numberOfTablesRequired, int selectedId){
        this.buildRetrofitForRestaurantService();
        try {
            if (ServerUtil.isBookingRestServiceUp()) {
                if (selectedId == 0) {
                    this.callRestServiceForNameResults(searchTerm, numberOfTablesRequired);
                } else if (selectedId == 1) {
                    this.callRestServiceForAddressResults(searchTerm, numberOfTablesRequired);
                }
            } else {
                this.view.showProgressBar(false);
                this.view.showServerDownMessage();
            }
        }
        catch (Exception e){
            this.view.showProgressBar(false);
            this.view.showSomethingWrongMessage();
        }
    }

    @SuppressLint("LongLogTag")
    private void callRestServiceForAddressResults(String searchTerm, int numberOfTablesRequired) {
        Observable<List<Restaurant>> restaurantsByAddressCall
                = this.bookingRestService.getRestaurantsByAddress(searchTerm,numberOfTablesRequired,RESTServiceConstants.AUTH_HEADER);
                restaurantsByAddressCall
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(restaurants -> {
                            //this.view.showProgressBar(true);
                            Log.d("!@!@!@!@!@!@!@!@!@", "changed from accept ");
                        })
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(restaurants -> {
                            this.view.showProgressBar(false);
                    Log.d("((((((((((((((((((","subscribed ^(@%#$&(@#%$ ");
                    if(ValidationUtil.checkForEmptyListWithSize(restaurants))
                        this.view.navigateToSearchResultsPage(restaurants);
                    else if(ValidationUtil.checkForEmptyList(restaurants))
                        this.view.showNoRestaurantsFoundMessage();
                    else
                        this.view.showSomethingWrongMessage();
                });

    }

    @SuppressLint("LongLogTag")
    private void callRestServiceForNameResults(String searchTerm, int numberOfTablesRequired) {
        Observable<List<Restaurant>> restaurantsByNameCall
                 = this.bookingRestService.getRestaurantsByName(searchTerm,numberOfTablesRequired,RESTServiceConstants.AUTH_HEADER);
            restaurantsByNameCall.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(restaurants -> {
                        //view.showProgressBar(true);
                        Log.d("!@!@!@!@!@!@!@!@!@","changed from accept ");
                    })
            .subscribe(restaurants -> {
                this.view.showProgressBar(false);
                Log.d("((((((((((((((((((","subscribed ^(@%#$&(@#%$ ");
                if(ValidationUtil.checkForEmptyListWithSize(restaurants))
                    this.view.navigateToSearchResultsPage(restaurants);
                else if(ValidationUtil.checkForEmptyList(restaurants))
                    this.view.showNoRestaurantsFoundMessage();
                else
                    this.view.showSomethingWrongMessage();
            });

    }
}
