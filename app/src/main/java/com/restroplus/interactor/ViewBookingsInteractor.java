package com.restroplus.interactor;

import android.annotation.SuppressLint;
import android.util.Log;

import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.model.Booking;
import com.restroplus.retrofit.BookingRestService;
import com.restroplus.retrofit.RetrofitClient;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.util.ServerUtil;
import com.restroplus.util.ValidationUtil;
import com.restroplus.view.ViewBookingsView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by M1037764 on 1/4/2018.
 */
@SuppressLint("LongLogTag")
public class ViewBookingsInteractor {

    private Retrofit retrofitClient;

    private BookingRestService bookingRestService;

    private ViewBookingsView viewBookingsView;

    public ViewBookingsInteractor(ViewBookingsView viewBookingsView) {
        this.viewBookingsView = viewBookingsView;
        this.retrofitClient = RetrofitClient.buildRetrofitClient(RESTServiceConstants.BASE_URL_BOOKING_MICRO_SERVICE);
        this.bookingRestService=this.retrofitClient.create(BookingRestService.class);
    }


    public void fetchAllBookingsForUser() {
        try {
            if (ServerUtil.isBookingRestServiceUp()) {
                Observable<List<Booking>> bookingsForUserCall = this.bookingRestService.getBookingsForUser(FirebaseUtils.getLoggedInUserDetails().getUid(),
                        RESTServiceConstants.AUTH_HEADER);
                bookingsForUserCall.observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(bookings -> {
                            // TODO: 1/9/2018 show progress bar here
                        })
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(bookings -> {
                            this.viewBookingsView.showProgressBar(false);
                            if(ValidationUtil.checkForEmptyListWithSize(bookings))
                            this.viewBookingsView.showBookings(bookings);
                            else
                                this.viewBookingsView.showNoBookingsMessage();
                            });

            }
            else{
                this.viewBookingsView.showProgressBar(false);
                this.viewBookingsView.showServerDownMessage();
                this.viewBookingsView.navigateToOpeningPage();
            }
        }
        catch (Exception e){
            Log.d("error in fetchAllBookingsForUser",""+e.getMessage()+e.getStackTrace());
            this.viewBookingsView.showProgressBar(false);
            this.viewBookingsView.showErrorMessageForSomethingWrong();
        }
    }

}
