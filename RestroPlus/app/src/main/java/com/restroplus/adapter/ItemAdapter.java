package com.restroplus.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.restroplus.constants.UserConstants;
import com.restroplus.model.Item;
import com.restroplus.restroplus.R;
import com.restroplus.view.BookingPageView;

import java.util.List;

/**
 * Created by M1037764 on 12/20/2017.
 */
@SuppressLint("ResourceAsColor")
public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> listOfItems;

    private BookingPageView bookingPageView;

    private ImageButton currentClickedButton;


    public ItemAdapter(List<Item> listOfItems, BookingPageView bookingPageView) {
        this.listOfItems = listOfItems;
        this.bookingPageView = bookingPageView;
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,itemPrice,itemCategory;
        TextView labelItemName,labelItemPrice,labelItemCategory;
        ImageButton addButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textViewForItemName);
            itemPrice = itemView.findViewById(R.id.textViewForItemPrice);
            itemCategory = itemView.findViewById(R.id.textViewForItemCategory);
            addButton = itemView.findViewById(R.id.buttonForAddingItem);
            labelItemCategory = itemView.findViewById(R.id.labelItemCategory);
            labelItemName = itemView.findViewById(R.id.labelItemName);
            labelItemPrice = itemView.findViewById(R.id.labelItemPrice);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_layout_list_item,parent,false);
        return new ItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        Item selectedItem = listOfItems.get(position);
        if(position%2==0)
            this.setTextColorToCyan(holder,"#191970","#006400");
        else
            this.setTextColorToGreen(holder,"#006400","#191970");

        holder.itemName.setText(UserConstants.SINGLE_SPACE+selectedItem.getItemName());
        holder.itemPrice.setText(UserConstants.SINGLE_SPACE+String.valueOf(selectedItem.getPrice()));
        holder.itemCategory.setText(UserConstants.SINGLE_SPACE+selectedItem.getItemCategory());
        this.currentClickedButton = holder.addButton;
        if(selectedItem.isInCart()) {
            this.changeCartIconAndDisableClick();
        }
        holder.addButton.setOnClickListener(v -> {
            this.bookingPageView.setSelectedItemDetails(selectedItem);
            this.bookingPageView.setSelectedButton(holder.addButton);
            if(!selectedItem.isInCart())
            this.bookingPageView.showAlertDialogForQuantityAndGetUserInput();
        });


    }

    private void setTextColorToCyan(ItemViewHolder holder , String colorCode,String colorCode2) {
        holder.labelItemName.setTextColor(Color.parseColor(colorCode2));
        holder.labelItemPrice.setTextColor(Color.parseColor(colorCode2));
        holder.labelItemCategory.setTextColor(Color.parseColor(colorCode2));
        holder.itemName.setTextColor(Color.parseColor(colorCode));
        holder.itemPrice.setTextColor(Color.parseColor(colorCode));
        holder.itemCategory.setTextColor(Color.parseColor(colorCode));
    }

    private void setTextColorToGreen(ItemViewHolder holder , String colorCode,String colorCode2) {
        holder.labelItemName.setTextColor(Color.parseColor(colorCode2));
        holder.labelItemPrice.setTextColor(Color.parseColor(colorCode2));
        holder.labelItemCategory.setTextColor(Color.parseColor(colorCode2));
        holder.itemName.setTextColor(Color.parseColor(colorCode));
        holder.itemPrice.setTextColor(Color.parseColor(colorCode));
        holder.itemCategory.setTextColor(Color.parseColor(colorCode));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public void changeCartIconAndDisableClick() {
        this.currentClickedButton.setClickable(false);
        this.currentClickedButton.setBackgroundResource(R.mipmap.incarticon);
    }


}
