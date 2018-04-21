package com.restroplus.interactor;

import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.model.Booking;
import com.restroplus.model.BookingInputDto;
import com.restroplus.retrofit.BookingRestService;
import com.restroplus.retrofit.RetrofitClient;
import com.restroplus.util.ServerUtil;
import com.restroplus.view.CartPageView;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by M1037764 on 1/2/2018.
 */

public class CartPageInteractor {

    private Retrofit retrofitClient;

    private BookingRestService bookingRestService;

    private CartPageView cartPageView;


    public CartPageInteractor(CartPageView cartPageView) {
        this.cartPageView = cartPageView;
        this.retrofitClient = RetrofitClient.buildRetrofitClient(RESTServiceConstants.BASE_URL_BOOKING_MICRO_SERVICE);
        this.bookingRestService=this.retrofitClient.create(BookingRestService.class);
    }

    public void placeBooking (BookingInputDto bookingInputDto) {
        try {
            if(bookingInputDto == null) {
                throw new Exception();
            }
            if (ServerUtil.isBookingRestServiceUp()) {
                Observable<Booking> placeBookingCall = this.bookingRestService.placeBooking(bookingInputDto, RESTServiceConstants.AUTH_HEADER);
                placeBookingCall
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(booking -> {
                            // TODO: 1/2/2018 show progressbar
                        })
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(booking -> {
                            if (booking.getBookingId() != null) {
                                this.cartPageView.showProgressBar(false);
                                cartPageView.navigateToBookingConfirmationPage(booking);
                            } else {
                                this.cartPageView.showProgressBar(false);
                                cartPageView.showSomethingWrongMessage();
                            }
                        });
            } else {
                this.cartPageView.showProgressBar(false);
                this.cartPageView.showServerDownMessage();
            }
        }
        catch (Exception e){
            this.cartPageView.showProgressBar(false);
            this.cartPageView.showSomethingWrongMessage();
        }
    }

}
