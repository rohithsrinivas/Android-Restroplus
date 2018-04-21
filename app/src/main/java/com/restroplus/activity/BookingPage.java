package com.restroplus.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.reflect.TypeToken;
import com.restroplus.adapter.ItemAdapter;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Item;
import com.restroplus.model.Restaurant;
import com.restroplus.presenter.BookingPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConversionUtil;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.util.ValidationUtil;
import com.restroplus.view.BookingPageView;

import java.util.ArrayList;
import java.util.List;

public class BookingPage extends AppCompatActivity implements BookingPageView{

    private BookingPagePresenter bookingPagePresenter;

    private TextView headerMessageTextView;

    private int numberOfTablesRequired, enteredQuantity = 0;

    private Restaurant selectedRestaurant;

    private ItemAdapter itemAdapter;

    private RecyclerView itemRecyclerView;

    private AlertDialog.Builder quantityDialog,clearCartItemsDialog;

    private TextView quantity,numberOfItemsInCart;

    private View dialogEditView;

    private Button quantityIncrease,quantityDecrease;

    private Item selectedItem;

    private FloatingActionButton viewCartButton;

    private List<Restaurant> searchResults;

    private List<Item> itemsInCart;

    public ImageButton currentClickedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_booking_page);
        this.headerMessageTextView = findViewById(R.id.textViewForHeaderInBooking);
        this.itemRecyclerView = findViewById(R.id.recyclerViewForItems);
        this.viewCartButton = findViewById(R.id.buttonViewCart);
        this.numberOfItemsInCart = findViewById(R.id.textViewNumberOfItemsInCart);
        this.selectedRestaurant = ConversionUtil.gson.fromJson(getIntent().getStringExtra("RestaurantDetails"),Restaurant.class);
        this.numberOfTablesRequired = getIntent().getIntExtra("numberOfTablesRequired",1);
        this.searchResults = ConversionUtil.gson.fromJson(getIntent().getStringExtra("searchResults"),
                new TypeToken<ArrayList<Restaurant>>(){}.getType());
        this.itemsInCart = ConversionUtil.gson.fromJson(getIntent().getStringExtra("itemsInCart"),
                new TypeToken<ArrayList<Item>>(){}.getType());
        this.bookingPagePresenter = new BookingPagePresenter(this);
        this.bookingPagePresenter.onPageLoad();
        //this.setItemsInCartForPresenter();
        this.viewCartButton.setOnClickListener(v -> bookingPagePresenter.onViewCartButtonClick(this.itemsInCart));
    }



    @Override
    public void onBackPressed(){
        this.showDialogForClearingCart();
    }

    private void navigateBackToSearchResultsPage() {
        Intent navigateBackToSearchPage = new Intent(BookingPage.this,SearchResults.class);
        navigateBackToSearchPage.putExtra("searchResults", ConversionUtil.convertListToJsonString(this.searchResults));
        navigateBackToSearchPage.putExtra("numberOfTablesRequired",this.numberOfTablesRequired);
        startActivity(navigateBackToSearchPage);
    }

    @Override
    public void showClickedItemDetails(Item item) {
        Toast.makeText(this,item.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFullRestaurantDetails() {
        this.headerMessageTextView.setText(this.getHeaderMessage());
        this.itemAdapter = new ItemAdapter(selectedRestaurant.getMenu().getItems(),this);
        this.itemRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.itemRecyclerView.setAdapter(this.itemAdapter);
        this.itemAdapter.notifyDataSetChanged();
        if(!ValidationUtil.checkForEmptyList(this.itemsInCart))
            this.itemsInCart = new ArrayList<>();
        this.numberOfItemsInCart.setText(String.valueOf(this.itemsInCart.size()));
    }

    @Override
    public void showAlertDialogForQuantityAndGetUserInput() {
        this.dialogEditView = this.getLayoutInflater().inflate(R.layout.layout_dialog_edit_view,null);
        this.quantity  = dialogEditView.findViewById(R.id.textViewForItemQuantity);
        this.quantityIncrease = dialogEditView.findViewById(R.id.buttonIncreaseQuantity);
        this.quantityDecrease = dialogEditView.findViewById(R.id.buttonReduceQuantity);
        this.quantity.setText(String.valueOf(1));
        this.quantityDialog = new AlertDialog.Builder(this);
        this.quantityDialog.setTitle(UserConstants.ENTER_QUANTITY)
                .setMessage(UserConstants.SPECIFY_NO_OF_UNITS)
                .setView(dialogEditView)
                .setPositiveButton(UserConstants.ADD_ITEM_TO_CART, (dialog, which) -> {
                    enteredQuantity = ConversionUtil.convertStringToInteger(quantity.getText().toString());
                    selectedItem.setQuantity(enteredQuantity);
                    selectedItem.setInCart(true);
                    BookingPage.this.bookingPagePresenter.addItemToCart(selectedItem,this.itemsInCart);
                    BookingPage.this.changeCartIconAndDisableClick();
                    this.updateNumberOfItemsInCart();
                    dialog.dismiss();
                })
                .setNegativeButton(UserConstants.CANCEL, (dialog, which) -> {
                })
                .show();

        this.quantityDecrease.setOnClickListener(v -> {
            enteredQuantity = ConversionUtil.convertStringToInteger(quantity.getText().toString());
            if (enteredQuantity > 1) {
                quantity.setText(String.valueOf(--enteredQuantity));
            }
        });

        this.quantityIncrease.setOnClickListener(v -> {
            enteredQuantity = ConversionUtil.convertStringToInteger(quantity.getText().toString());
            quantity.setText(String.valueOf(++enteredQuantity));
        });

    }

    private void updateNumberOfItemsInCart() {
        this.numberOfItemsInCart.setText(String.valueOf(this.itemsInCart.size()));
    }

    @Override
    public void setSelectedItemDetails(Item item) {
        this.selectedItem = item;
    }

    @Override
    public void showItemAddedToCartMessageAndChangeCartIcon() {
        Toast.makeText(this,UserConstants.ITEM_ADDED_TO_CART,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoItemsInCartMessage() {
        Toast.makeText(this,UserConstants.NO_ITEMS_IN_YOUR_CART,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToCartPage() {
        Intent navigateToCartPage = new Intent(BookingPage.this,ViewCartPage.class);
        navigateToCartPage.putExtra("itemsInCart",ConversionUtil.convertListToJsonString(this.itemsInCart));
        navigateToCartPage.putExtra("RestaurantDetails",ConversionUtil.convertToJsonString(this.selectedRestaurant));
        navigateToCartPage.putExtra("numberOfTablesRequired",this.numberOfTablesRequired);
        navigateToCartPage.putExtra("searchResults",ConversionUtil.convertListToJsonString(this.searchResults));
        startActivity(navigateToCartPage);
    }


    @Override
    public void setSelectedButton(ImageButton addButton) {
        this.currentClickedButton = addButton;
    }

    @Override
    public void updateCartContents(List<Item> itemsInCart) {
        this.itemsInCart = itemsInCart;
    }

    private String getHeaderMessage(){
        return "You need "+this.numberOfTablesRequired+" table(s) in "+this.selectedRestaurant.getRestaurantName();
    }

//    private void setItemsInCartForPresenter() {
//        if(ValidationUtil.checkForEmptyList(this.itemsInCart))
//        this.bookingPagePresenter.setItemsInCart(this.itemsInCart);
//    }

    private void changeCartIconAndDisableClick() {
        this.currentClickedButton.setClickable(false);
        this.currentClickedButton.setBackgroundResource(R.mipmap.incarticon);
    }

    private void showDialogForClearingCart(){
        this.clearCartItemsDialog = new AlertDialog.Builder(this);
        this.clearCartItemsDialog.setTitle(UserConstants.CLEAR_CART_MESSAGE)
                .setMessage(UserConstants.GOING_BACK_MESSAGE)
                .setPositiveButton(UserConstants.YES, (dialog, which) -> navigateBackToSearchResultsPage())
                .setNegativeButton(UserConstants.NO, (dialog, which) -> dialog.dismiss())
                .show();
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this,UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(BookingPage.this, LoginPage.class));
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
