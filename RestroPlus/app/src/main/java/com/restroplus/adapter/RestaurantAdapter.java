package com.restroplus.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.restroplus.model.Restaurant;
import com.restroplus.restroplus.R;
import com.restroplus.view.SearchResultsView;

import java.util.List;

/**
 * Created by M1037764 on 12/18/2017.
 */
@SuppressLint("LongLogTag")
public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurants;

    private SearchResultsView searchResultsView;

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{
        TextView nameOfTheRestaurant, addressOfTheRestaurant,restaurantTableFleet;
        Button bookRestaurantButton;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            nameOfTheRestaurant = itemView.findViewById(R.id.textViewNameOfTheRestaurantValue);
            addressOfTheRestaurant = itemView.findViewById(R.id.textViewAddressOfTheRestaurantValue);
            bookRestaurantButton = itemView.findViewById(R.id.buttonBookRestaurant);
            restaurantTableFleet = itemView.findViewById(R.id.textViewTableFleetValue);
        }
    }

    public RestaurantAdapter(List<Restaurant> restaurants,SearchResultsView searchResultsView) {
        this.restaurants = restaurants;
        this.searchResultsView = searchResultsView;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inner_layout_list_restaurant,parent,false);
        return new RestaurantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.nameOfTheRestaurant.setText(restaurant.getRestaurantName());
        holder.addressOfTheRestaurant.setText(restaurant.getRestaurantAddress());
        holder.restaurantTableFleet.setText(String.valueOf(restaurant.getTableFleet()));
        holder.bookRestaurantButton.setOnClickListener(v -> {
            Log.d("*&*&*&*&*&*&*&onButtonClick","done"+restaurant.toString());
            searchResultsView.navigateToBookingPage(restaurant);
        });
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }


}
