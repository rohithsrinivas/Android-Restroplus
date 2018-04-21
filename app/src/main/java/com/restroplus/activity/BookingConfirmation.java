package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.restroplus.adapter.CartItemAdapter;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Booking;
import com.restroplus.presenter.BookingConfirmationPresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConversionUtil;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.BookingConfirmationView;

public class BookingConfirmation extends AppCompatActivity implements BookingConfirmationView{

    private Booking bookingDetails;

    private TextView bookingId,restaurantName,numberOfTablesBooked,billingAmount;

    private BookingConfirmationPresenter bookingConfirmationPresenter;

    private CartItemAdapter bookingItemAdapter;

    private RecyclerView recyclerViewForBookedItems;

    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_confirmation);
        this.bookingDetails = ConversionUtil.gson.fromJson(getIntent().getStringExtra("BookingDetails"),Booking.class);
        this.bookingConfirmationPresenter = new BookingConfirmationPresenter(this);
        this.recyclerViewForBookedItems = findViewById(R.id.recyclerViewForOrderedItemsBookingDetailsDialog);
        this.bookingId = findViewById(R.id.textViewBookingIdBookingDetailsDialog);
        this.restaurantName = findViewById(R.id.textViewRestaurantNameBookingDetailsDialog);
        this.numberOfTablesBooked = findViewById(R.id.textViewNumberOfTablesBookingDetailsDialog);
        this.billingAmount = findViewById(R.id.textViewBillingAmountBookingDetailsDialog);
        this.done = findViewById(R.id.buttonDoneConfirmation);
        this.bookingConfirmationPresenter.onPageLoad(this.bookingDetails);
        this.done.setOnClickListener(v -> {
           this.bookingConfirmationPresenter.onDoneButtonClick();
        });

    }

    @Override
    public void onBackPressed(){
        this.navigateToOpeningPage();
    }

    @Override
    public void showBookingDetails(Booking booking) {
        this.bookingId.setText(this.bookingId.getText() + String.valueOf(this.bookingDetails.getBookingId()));
        this.restaurantName.setText(this.restaurantName.getText() + String.valueOf(this.bookingDetails.getRestaurant().getRestaurantName()));
        this.numberOfTablesBooked.setText(this.numberOfTablesBooked.getText() + String.valueOf(this.bookingDetails.getNumberOfTablesBooked()));
        this.bookingItemAdapter = new CartItemAdapter(this.bookingDetails.getOrderedItems());
        this.bookingItemAdapter.disableRemoveFromCartButton(Boolean.TRUE);
        this.recyclerViewForBookedItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerViewForBookedItems.setAdapter(this.bookingItemAdapter);
        this.bookingItemAdapter.notifyDataSetChanged();
        this.billingAmount.setText(this.billingAmount.getText() + String.valueOf(this.bookingDetails.getBillingAmount()));

    }

    @Override
    public void navigateToOpeningPage() {
        Intent navigateToOpeningPage = new Intent(BookingConfirmation.this,OpeningPage.class);
        startActivity(navigateToOpeningPage);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this, UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(BookingConfirmation.this, LoginPage.class));
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
