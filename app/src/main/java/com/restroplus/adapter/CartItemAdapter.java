package com.restroplus.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.restroplus.constants.UserConstants;
import com.restroplus.model.Item;
import com.restroplus.presenter.BookingPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.view.CartPageView;

import java.util.List;

/**
 * Created by M1037764 on 12/22/2017.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder>{

    private List<Item> listOfItemsInCart;

    private CartPageView cartPageView;

    private Boolean disableRemoveFromCartButton = Boolean.FALSE;

    public class CartItemViewHolder extends RecyclerView.ViewHolder{
        TextView itemName,itemPrice,category;
        ImageButton removeFromCartButton;

        public CartItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textViewForItemNameInCartPage);
            itemPrice = itemView.findViewById(R.id.textViewForPriceInCartPage);
            category = itemView.findViewById(R.id.textViewForCategoryInCartPage);
            removeFromCartButton = itemView.findViewById(R.id.buttonRemoveFromCartInCartPage);
        }
    }

    public CartItemAdapter(List<Item> listOfItemsInCart, CartPageView cartPageView) {
        this.listOfItemsInCart = listOfItemsInCart;
        this.cartPageView = cartPageView;
    }

    public CartItemAdapter(List<Item> listOfItemsInCart){
        this.listOfItemsInCart = listOfItemsInCart;
    }


    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_layout_cart_item,parent,false);
        return new CartItemViewHolder(cartItemView);
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        Item cartItem = this.listOfItemsInCart.get(position);

        holder.itemName.setText(UserConstants.SINGLE_SPACE+cartItem.getItemName());
        holder.category.setText(UserConstants.SINGLE_SPACE+cartItem.getItemCategory());
        holder.itemPrice.setText(UserConstants.SINGLE_SPACE+this.getPriceText(cartItem.getPrice(),cartItem.getQuantity()));
        if(this.disableRemoveFromCartButton) {
            holder.removeFromCartButton.setClickable(false);
            holder.removeFromCartButton.setVisibility(View.GONE);
//            LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(, LinearLayout.LayoutParams.WRAP_CONTENT
//            ,0f);
            //holder.imageLayout.setLayoutParams(params);
            holder.itemName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
            holder.category.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
            holder.itemPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13f);
        }
        holder.removeFromCartButton.setOnClickListener(v -> {
            cartPageView.updateItemDetailsInSelectedRestaurant(cartItem);
            this.listOfItemsInCart.remove(position);
            notifyDataSetChanged();
            notifyItemRangeChanged(position,this.listOfItemsInCart.size());
            cartPageView.showMessageForItemRemoved();
            cartPageView.updateBillingAmount();
            if(listOfItemsInCart.size()==0)
            {
                cartPageView.showCartEmptyMessage();
                cartPageView.navigateBackToBookingPage();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.listOfItemsInCart.size();
    }

    private String getPriceText(Double price,Integer quantity){
        return String.valueOf(price)+" * "+String.valueOf(quantity)+" = "+String.valueOf(price*quantity);
    }

    public void disableRemoveFromCartButton(Boolean disableRemoveFromCart){
        if(disableRemoveFromCart)
            this.disableRemoveFromCartButton = Boolean.TRUE;
    }
}
