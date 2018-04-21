package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.gson.reflect.TypeToken;
import com.restroplus.adapter.RestaurantAdapter;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Restaurant;
import com.restroplus.presenter.SearchResultsPresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConversionUtil;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.SearchResultsView;

import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity implements SearchResultsView{

    private RecyclerView recyclerView;

    private List<Restaurant> searchResults;

    private RestaurantAdapter restaurantAdapter;

    private SearchResultsPresenter searchResultsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_results);
        recyclerView = findViewById(R.id.recylerViewForRestaurants);
        searchResultsPresenter = new SearchResultsPresenter(this);
        this.searchResults = ConversionUtil.gson.fromJson(getIntent().getStringExtra("searchResults"),
                new TypeToken<ArrayList<Restaurant>>(){}.getType());
        this.searchResultsPresenter.onPageLoad();

    }

    @Override
    public void displayRestaurantResults() {
        this.restaurantAdapter = new RestaurantAdapter(searchResults,this);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.recyclerView.setAdapter(restaurantAdapter);
        this.restaurantAdapter.notifyDataSetChanged();
    }

    @Override
    public void showClickedButtonDetails(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToBookingPage(Restaurant restaurant) {
        Intent navigateToBookingPage = new Intent(SearchResults.this,BookingPage.class);
        navigateToBookingPage.putExtra("RestaurantDetails", ConversionUtil.convertToJsonString(restaurant));
        navigateToBookingPage.putExtra("numberOfTablesRequired",getIntent().getIntExtra("numberOfTablesRequired",1));
        navigateToBookingPage.putExtra("searchResults",ConversionUtil.convertListToJsonString(this.searchResults));
        startActivity(navigateToBookingPage);
    }

    @Override
    public void onBackPressed(){
        Intent navigateBackToSearchPage = new Intent(SearchResults.this,SearchPage.class);
        startActivity(navigateBackToSearchPage);
    }

//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this, UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SearchResults.this, LoginPage.class));
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
