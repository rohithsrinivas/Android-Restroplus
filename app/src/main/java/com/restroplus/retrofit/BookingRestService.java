package com.restroplus.retrofit;


import com.restroplus.model.Booking;
import com.restroplus.model.BookingInputDto;
import com.restroplus.model.Restaurant;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.internal.operators.observable.ObservableLift;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BookingRestService {

	@POST("createBooking")
	Observable<Booking> placeBooking(
		@Body BookingInputDto bookingInputDto,
		@Header("Authorization") String credentials

	);

	@GET("bookingsForRestaurant/{restaurantId}")
	Observable<List<Booking>> getBookingsForRestaurant(
		@Path("restaurantId") Integer restaurantId,
		@Header("Authorization") String credentials
	);

	@GET("bookingsForUser/{bookedByUserId}")
	Observable<List<Booking>> getBookingsForUser(
		@Path("bookedByUserId") String bookedByUserId,
		@Header("Authorization") String credentials
	);

	@GET("restaurants/name/{restaurantName}/{numberOfTablesRequired}")
	Observable<List<Restaurant>> getRestaurantsByName(
			@Path("restaurantName") String restaurantName,
			@Path("numberOfTablesRequired") Integer numberOfTablesRequired,
			@Header("Authorization") String credentials
	);

	@GET("restaurants/address/{address}/{numberOfTablesRequired}")
	Observable<List<Restaurant>> getRestaurantsByAddress(
			@Path("address") String address,
			@Path("numberOfTablesRequired") Integer numberOfTablesRequired,
			@Header("Authorization") String credentials
	);

	@GET("health")
	Observable<Object> getServerStatus(
			@Header("Authorization") String credentials
	);



	
}
