package com.example.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerce.Prevalent.Prevalent;
import com.example.ecommerce.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    private Button addToCartButon ;
    private FloatingActionButton addToCartBtn ;
    private ImageView productImage ;
    private ElegantNumberButton numberButton ;
    private TextView productPrice ,productDescription ,productName ;
    private  String productID ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        productID =getIntent().getStringExtra("pid");

        addToCartButon=(Button )findViewById(R.id.pd_add_to_card_button);

        numberButton=(ElegantNumberButton)findViewById(R.id.number_btn) ;
        productImage=(ImageView )findViewById(R.id.product_image_details);
        productPrice=(TextView )findViewById(R.id.product_price_details);
        productDescription=(TextView )findViewById(R.id.product_description_details);
        productName=(TextView )findViewById(R.id.product_name_details);


        getProductDetails(productID);





        addToCartButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingToCartList();


            }
        });

    }

    private void addingToCartList()
    {

        String saveCurrentTime,saveCurrentDate ;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy") ;
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a") ;
        saveCurrentTime=currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String,Object>cartMap =new HashMap<>();
        cartMap.put("pid",productID) ;
        cartMap.put("pname",productName.getText().toString()) ;
        cartMap.put("price",productPrice.getText().toString()) ;
        cartMap.put("date",saveCurrentDate) ;
        cartMap.put("time",saveCurrentTime) ;
        cartMap.put("quantity",numberButton.getNumber()) ;
        cartMap.put("discount","") ;
        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products")
                .child(productID)
                .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {

                    cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                            .child("Products")
                            .child(productID)
                            .updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(ProductDetailActivity.this, "da them vao gio hang", Toast.LENGTH_SHORT).show();

                                Intent intent =new Intent(ProductDetailActivity.this, Home2Activity.class);
                                startActivity(intent);
                            }

                        }
                    });
                    }


                }


        });


    }

    private void getProductDetails(String productID)
    {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    Products products = datasnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productPrice.setText(products.getPrice());
                    productDescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productImage);





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }
}