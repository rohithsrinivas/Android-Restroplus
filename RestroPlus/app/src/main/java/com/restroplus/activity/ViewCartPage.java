package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.reflect.TypeToken;
import com.restroplus.adapter.CartItemAdapter;
import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Booking;
import com.restroplus.model.Item;
import com.restroplus.model.Restaurant;
import com.restroplus.presenter.BookingPagePresenter;
import com.restroplus.presenter.ViewCartPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConversionUtil;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.util.PaymentUtils;
import com.restroplus.view.CartPageView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewCartPage extends AppCompatActivity implements CartPageView{

    private List<Item> itemsInCart;

    private Restaurant selectedRestaurant;

    private List<Restaurant> searchResults;

    private int numberOfTablesRequired;

    private RecyclerView recyclerViewForCartItems;

    private ViewCartPagePresenter viewCartPagePresenter;

    private CartItemAdapter cartItemAdapter;

    private TextView amountPayable;

    private Button checkOutButton;

    private ProgressBar progressBarPlacebooking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_cart);
        this.recyclerViewForCartItems = findViewById(R.id.recyclerViewForCartItems);
        this.amountPayable = findViewById(R.id.textViewForTotalAmountPayable);
        this.checkOutButton = findViewById(R.id.buttonCheckOutFromCartPage);
        this.progressBarPlacebooking = findViewById(R.id.progressBarPlaceBooking);
        this.viewCartPagePresenter = new ViewCartPagePresenter(this,ViewCartPage.this);
        this.itemsInCart = ConversionUtil.gson.fromJson(getIntent().getStringExtra("itemsInCart"),
                new TypeToken<ArrayList<Item>>(){}.getType());
        this.selectedRestaurant = ConversionUtil.gson.fromJson(getIntent().getStringExtra("RestaurantDetails"),Restaurant.class);
        this.searchResults = ConversionUtil.gson.fromJson(getIntent().getStringExtra("searchResults"),
                new TypeToken<ArrayList<Restaurant>>(){}.getType());
        this.numberOfTablesRequired = getIntent().getIntExtra("numberOfTablesRequired",1);
        this.viewCartPagePresenter.onPageLoad();
        this.checkOutButton.setOnClickListener(v -> viewCartPagePresenter.onCheckOutButtonClick
                (this.selectedRestaurant,this.numberOfTablesRequired,this.itemsInCart));
    }


    @Override
    public void onBackPressed(){
        this.navigateBackToBookingPage();
    }

    @Override
    public void showItemsInCart() {
        this.cartItemAdapter = new CartItemAdapter(this.itemsInCart,this);
        this.recyclerViewForCartItems.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerViewForCartItems.setAdapter(this.cartItemAdapter);
        this.cartItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void showAmountPayable() {
        this.amountPayable.setText(this.amountPayable.getText()+String.valueOf(PaymentUtils.calculateTotalAmountPayable(this.itemsInCart)));
    }

    @Override
    public void showBookingPlacedMessage() {
        Toast.makeText(this, UserConstants.BOOKING_SUCCESSFUL,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSomethingWrongMessage() {
        Toast.makeText(this, ErrorConstants.SOMETHING_WRONG_PLACING_BOOKING_MESSAGE,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToBookingConfirmationPage(Booking booking) {
        Intent navigateToBookingConfirmationPage = new Intent(ViewCartPage.this,BookingConfirmation.class);
        navigateToBookingConfirmationPage.putExtra("BookingDetails",ConversionUtil.convertToJsonString(booking));
        startActivity(navigateToBookingConfirmationPage);
    }

    @Override
    public void showMessageForItemRemoved() {
        Toast.makeText(this,UserConstants.ITEM_REMOVED_FROM_CART,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateItemDetailsInSelectedRestaurant(Item itemToBeUpdated) {
        this.selectedRestaurant.getMenu().getItems().remove(itemToBeUpdated);
        itemToBeUpdated.setInCart(false);
        this.selectedRestaurant.getMenu().getItems().add(itemToBeUpdated);
    }

    @Override
    public void showRandomMessage() {
        Toast.makeText(this,"inside required do on next",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateBillingAmount() {
        this.amountPayable.setText(UserConstants.TOTAL_AMOUNT_PAYABLE
                +String.valueOf(PaymentUtils.calculateTotalAmountPayable(this.itemsInCart)));
    }

    @Override
    public void showProgressBar(Boolean showProgressBar) {
        if(showProgressBar) {
            this.progressBarPlacebooking.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            this.progressBarPlacebooking.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showCartEmptyMessage() {
        Toast.makeText(this,UserConstants.YOUR_CART_IS_EMPTY,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateBackToBookingPage() {
        Intent navigateBackToBookingPage = new Intent(ViewCartPage.this,BookingPage.class);
        navigateBackToBookingPage.putExtra("RestaurantDetails",ConversionUtil.convertToJsonString(this.selectedRestaurant));
        navigateBackToBookingPage.putExtra("searchResults",ConversionUtil.convertListToJsonString(this.searchResults));
        navigateBackToBookingPage.putExtra("numberOfTablesRequired",this.numberOfTablesRequired);
        navigateBackToBookingPage.putExtra("itemsInCart",ConversionUtil.convertListToJsonString(this.itemsInCart));
        startActivity(navigateBackToBookingPage);
    }

    @Override
    public void showMessageForNoInternetConnection() {
        Toast.makeText(this,ErrorConstants.NO_INTERNET_CONNECTION,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showServerDownMessage() {
        Toast.makeText(this, RESTServiceConstants.SERVER_DOWN_MESSAGE,Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this,UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(ViewCartPage.this, LoginPage.class));
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
