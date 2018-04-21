package com.restroplus.constants;

import okhttp3.Credentials;

/**
 * Created by M1037764 on 12/20/2017.
 */

public interface RESTServiceConstants {

    String BASE_URL_BOOKING_MICRO_SERVICE = "http://192.168.1.11:2020/api/booking/";

    String AUTH_HEADER = Credentials.basic("rohith","rohith123");

    String SERVER_DOWN_MESSAGE = "server is currently down, please try again";

}
