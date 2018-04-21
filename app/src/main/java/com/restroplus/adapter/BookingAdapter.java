package com.restroplus.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.restroplus.constants.UserConstants;
import com.restroplus.model.Booking;
import com.restroplus.restroplus.R;
import com.restroplus.view.ViewBookingsView;

import java.util.List;

/**
 * Created by M1037764 on 1/9/2018.
 */

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    List<Booking> bookingsForUser;

    ViewBookingsView viewBookingsView;

    public class BookingViewHolder extends RecyclerView.ViewHolder{
        TextView bookingId, restaurantName, bookingDate;
        Button viewBookingButton;

        public BookingViewHolder(View bookingView){
            super(bookingView);
            bookingId = bookingView.findViewById(R.id.textViewBookingIdViewBookings);
            restaurantName = bookingView.findViewById(R.id.textViewRestaurantNameViewBookings);
            bookingDate = bookingView.findViewById(R.id.textViewBookingDateViewBookings);
            viewBookingButton = bookingView.findViewById(R.id.buttonViewBookingDetailsViewBookings);
        }
    }

    public BookingAdapter(List<Booking> bookingsForUser, ViewBookingsView viewBookingsView) {
        this.bookingsForUser = bookingsForUser;
        this.viewBookingsView = viewBookingsView;
    }


    @Override
    public BookingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View bookingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_layout_booking_list_item,parent,false);
        return new BookingViewHolder(bookingView);
    }

    @Override
    public void onBindViewHolder(BookingViewHolder holder, int position) {
        Log.d("^^^^^^^^^^^^^^^^",""+position);
        Booking currentBooking = this.bookingsForUser.get(position);

        holder.bookingId.setText(UserConstants.SINGLE_SPACE+String.valueOf(currentBooking.getBookingId()));
        holder.restaurantName.setText(UserConstants.SINGLE_SPACE+String.valueOf(currentBooking.getRestaurant().getRestaurantName()));
        holder.bookingDate.setText(UserConstants.SINGLE_SPACE+String.valueOf(currentBooking.getBookingDate()));
        holder.viewBookingButton.setOnClickListener(v -> {
            this.viewBookingsView.showBookingDetailsInDialog(currentBooking);
        });

    }



    @Override
    public int getItemCount() {
        return this.bookingsForUser.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
