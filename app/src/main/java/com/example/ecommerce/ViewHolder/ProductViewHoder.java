package com.example.ecommerce.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Inteface.ItemClicklistner;
import com.example.ecommerce.R;

public class ProductViewHoder  extends RecyclerView.ViewHolder implements View.OnClickListener  {


    public TextView txtProductName ,txtProducDescription ,txtProductPrice;
     public  ImageView  imageView ;
    public ItemClicklistner listner ;


    public ProductViewHoder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.product_image);

        txtProductName=(TextView)itemView.findViewById(R.id.product_name);

        txtProducDescription=(TextView)itemView.findViewById(R.id.product_description);
        txtProductPrice=(TextView)itemView.findViewById(R.id.product_price);

    }
    public  void setItemClickListner(ItemClicklistner listner)
    {
        this.listner=listner;
    }



    @Override
    public void onClick(View view)
    {
        listner.onClick(view,getAdapterPosition(),false) ;

    }
}
