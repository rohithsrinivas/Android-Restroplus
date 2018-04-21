package com.restroplus.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.restroplus.activity.ViewCartPage;
import com.restroplus.interactor.CartPageInteractor;
import com.restroplus.model.BookingInputDto;
import com.restroplus.model.Item;
import com.restroplus.model.Restaurant;
import com.restroplus.util.ConnectivityUtils;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.CartPageView;

import java.util.List;

/**
 * Created by M1037764 on 12/22/2017.
 */

public class ViewCartPagePresenter {

    private CartPageView cartPageView;

    private CartPageInteractor cartPageInteractor;

    private ViewCartPage viewCartPage;

    public ViewCartPagePresenter(ViewCartPage pageView, CartPageView cartPageView) {
        this.viewCartPage = pageView;
        this.cartPageView = cartPageView;
        this.cartPageInteractor = new CartPageInteractor(this.cartPageView);
    }

    public void onPageLoad() {
        this.cartPageView.showItemsInCart();
        this.cartPageView.showAmountPayable();
    }

    @SuppressLint("LongLogTag")
    public void onCheckOutButtonClick(Restaurant selectedRestaurant, int numberOfTablesRequired, List<Item> itemsInCart){
        if(ConnectivityUtils.isDeviceConnectedToInternet(this.viewCartPage.getApplicationContext())) {
            BookingInputDto bookingInputDto = new BookingInputDto();
            bookingInputDto.setNoOfTablesBooked(numberOfTablesRequired);
            bookingInputDto.setOrderedItems(itemsInCart);
            bookingInputDto.setRestaurantId(selectedRestaurant.getRestaurantId());
            bookingInputDto.setRestaurant(selectedRestaurant);
            // TODO: 1/4/2018 this is just for sample, after implementing firebase, this should be the email id of the user who is logged in to the app
            bookingInputDto.setUserEmail(FirebaseUtils.getLoggedInUserDetails().getEmail());
            bookingInputDto.setTableCategory("general");
            // TODO: 1/4/2018 this is just for sample, after implementing firebase, this should be the user id of the user who is logged in to the app
            bookingInputDto.setBookedByUserId(FirebaseUtils.getLoggedInUserDetails().getUid());
            this.cartPageView.showProgressBar(true);
            try {
                this.cartPageInteractor.placeBooking(bookingInputDto);
            }catch(Exception e){
                Log.d("&&&&&&&&cause is ",e.getMessage());
            }
        }
        else
        {
            this.cartPageView.showMessageForNoInternetConnection();
        }
    }
}
