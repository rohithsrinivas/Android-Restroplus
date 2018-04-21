package com.restroplus.util;

import android.annotation.SuppressLint;
import android.util.Log;

import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.retrofit.BookingRestService;
import com.restroplus.retrofit.RetrofitClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Credentials;
import retrofit2.Retrofit;

/**
 * Created by M1037764 on 12/19/2017.
 */
@SuppressLint("LongLogTag")
public class ServerUtil {

    public static boolean status = false;


    public static boolean isBookingRestServiceUp(){

        Retrofit retrofitClient= RetrofitClient.buildRetrofitClient(RESTServiceConstants.BASE_URL_BOOKING_MICRO_SERVICE);

        BookingRestService bookingRestService=retrofitClient.create(BookingRestService.class);

        Observable<Object> healthCheckCall = bookingRestService.getServerStatus(RESTServiceConstants.AUTH_HEADER);

        checkHealthOfTheServer(healthCheckCall);

        return status;

    }

    private static void checkHealthOfTheServer(Observable<Object> healthCheckCall) {
        try {
            healthCheckCall.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .doOnNext(o -> Log.d("serverStatus", "server is up and running"))
                    .doOnError(throwable -> Log.d("error while getting health, server is down", "" + throwable.getMessage()))
                    .subscribe(o -> status = true);
        }catch(Exception e){
            status = false;
        }
    }
}
