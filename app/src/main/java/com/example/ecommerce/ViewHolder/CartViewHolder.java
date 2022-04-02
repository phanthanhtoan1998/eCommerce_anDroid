package com.example.ecommerce.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Inteface.ItemClicklistner;
import com.example.ecommerce.R;


public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public TextView txtProductName, txtProductPrice, txtProductQuatiny;
    private ItemClicklistner itemClicklistner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
        txtProductQuatiny = itemView.findViewById(R.id.cart_product_quantiny);
    }

    @Override
    public void onClick(View view)
    {

        itemClicklistner.onClick(view,getAdapterPosition(),false);
    }


    public void setItemClicklistner(ItemClicklistner itemClicklistner) {
        this.itemClicklistner = itemClicklistner;
    }
}
