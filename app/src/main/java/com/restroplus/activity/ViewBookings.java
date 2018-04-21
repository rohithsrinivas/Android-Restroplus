package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.restroplus.adapter.BookingAdapter;
import com.restroplus.adapter.CartItemAdapter;
import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Booking;
import com.restroplus.presenter.ViewBookingsPresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.ViewBookingsView;

import java.util.List;

public class ViewBookings extends AppCompatActivity implements ViewBookingsView{

    private ViewBookingsPresenter viewBookingsPresenter;

    private AlertDialog.Builder bookingDetailsDialog;

    private View bookingDetailsView;

    private BookingAdapter bookingAdapter;

    private RecyclerView recyclerViewForBookings;

    private CartItemAdapter cartItemAdapter;

    private ScrollView dialogScrollView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_bookings);
        this.recyclerViewForBookings = findViewById(R.id.recyclerViewForBookings);
        //this.dialogScrollView = findViewById(R.id.scrollViewForBookingDialog);
        this.progressBar = findViewById(R.id.progressBarViewBookings);
        this.viewBookingsPresenter = new ViewBookingsPresenter(this);
        this.viewBookingsPresenter.onPageLoad();
    }

    @Override
    public void showBookings(List<Booking> bookings) {
        this.bookingAdapter = new BookingAdapter(bookings,this);
        this.recyclerViewForBookings.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.recyclerViewForBookings.setLayoutManager(linearLayoutManager);
        this.recyclerViewForBookings.setAdapter(this.bookingAdapter);
        this.bookingAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar(Boolean showProgressBar) {
        if(showProgressBar) {
            this.progressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            this.progressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showBookingDetailsInDialog(Booking currentBooking) {
        this.bookingDetailsView = this.getLayoutInflater().inflate(R.layout.inner_dialog_booking_details,null);
        TextView bookingId = this.bookingDetailsView.findViewById(R.id.textViewBookingIdBookingDetailsDialog);
        TextView restaurantName = this.bookingDetailsView.findViewById(R.id.textViewRestaurantNameBookingDetailsDialog);
        TextView numberOfTables = this.bookingDetailsView.findViewById(R.id.textViewNumberOfTablesBookingDetailsDialog);
        RecyclerView recyclerViewForItems = this.bookingDetailsView.findViewById(R.id.recyclerViewForOrderedItemsBookingDetailsDialog);
        TextView billingAmount = this.bookingDetailsView.findViewById(R.id.textViewBillingAmountBookingDetailsDialog);
        bookingId.setText(bookingId.getText()+String.valueOf(currentBooking.getBookingId()));
        restaurantName.setText(restaurantName.getText()+String.valueOf(currentBooking.getRestaurant().getRestaurantName()));
        numberOfTables.setText(numberOfTables.getText()+String.valueOf(currentBooking.getNumberOfTablesBooked()));
        billingAmount.setText(billingAmount.getText()+String.valueOf(currentBooking.getBillingAmount()));
        this.cartItemAdapter = new CartItemAdapter(currentBooking.getOrderedItems());
        this.cartItemAdapter.disableRemoveFromCartButton(Boolean.TRUE);
        recyclerViewForItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerViewForItems.setAdapter(this.cartItemAdapter);
        this.cartItemAdapter.notifyDataSetChanged();
        this.bookingDetailsDialog = new AlertDialog.Builder(this);
        this.bookingDetailsDialog
                .setTitle(UserConstants.BOOKING_DETAILS_TITLE)
                .setView(this.bookingDetailsView)
                .setPositiveButton(UserConstants.OK, (dialog, which) -> dialog.dismiss())
                .show();
        //this.dialogScrollView.scrollTo(0,0);
    }

    @Override
    public void showErrorMessageForSomethingWrong() {
        Toast.makeText(this, ErrorConstants.SOMETHING_WRONG_BOOKINGS_MESSAGE,Toast.LENGTH_SHORT).show();
        this.navigateToOpeningPage();
    }

    @Override
    public void navigateToOpeningPage(){
        startActivity(new Intent(ViewBookings.this,OpeningPage.class));
    }

    @Override
    public void showServerDownMessage() {
        Toast.makeText(this, RESTServiceConstants.SERVER_DOWN_MESSAGE,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoBookingsMessage() {
        Toast.makeText(this, UserConstants.NO_BOOKINGS_MESSAGE,Toast.LENGTH_SHORT).show();
        this.navigateToOpeningPage();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this,UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(ViewBookings.this, LoginPage.class));
//        }
//    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        FirebaseUtils.killUserSession();
        LoginManager.getInstance().logOut();
    }


}
