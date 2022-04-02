package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private  ImageView tShirts,sportTshirts, famaleDresses ,sweathers ;
    private  ImageView glasses ,hatsCaps,walletsBagsPurses ,shoes ;
    private  ImageView headPhonesHandFree ,Laptops ,wattches ,mobilePhones ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);
        tShirts=(ImageView)findViewById(R.id.t_shirts);
        sportTshirts=(ImageView)findViewById(R.id.sports_t_shirts);
        famaleDresses=(ImageView)findViewById(R.id.female_dresses);
        sweathers=(ImageView)findViewById(R.id.sweather);
        glasses=(ImageView)findViewById(R.id.glasses_wallets);
        hatsCaps=(ImageView)findViewById(R.id.hast_caps);
        walletsBagsPurses=(ImageView)findViewById(R.id.purses_bags_wallets);
        shoes=(ImageView)findViewById(R.id.shoess);
        headPhonesHandFree=(ImageView)findViewById(R.id.headphoness_handfree);
        Laptops=(ImageView)findViewById(R.id.laptop_pc);
        wattches=(ImageView)findViewById(R.id.watches);
        mobilePhones=(ImageView)findViewById(R.id.mobilephones);




        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","tShirts");
                startActivity(intent);
            }
        });
        sportTshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Sport tShirts");
                startActivity(intent);
            }
        });
        famaleDresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Famale Dresses");
                startActivity(intent);
            }
        });
        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Sweathers");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Glasses");
                startActivity(intent);
            }
        });
        hatsCaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Hats Caps");
                startActivity(intent);
            }
        });
            walletsBagsPurses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Wallets Bags Purses ");
                startActivity(intent);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Shoes");
                startActivity(intent);
            }
        });
        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","HeadPhones HandFree");
                startActivity(intent);
            }
        });
        Laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Laptops");
                startActivity(intent);
            }
        });
        wattches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Watches");
                startActivity(intent);
            }
        });
        mobilePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(AdminCategoryActivity.this, AdminAddnewroductTActivity.class);
                intent.putExtra("category","Mobile Phones");
                startActivity(intent);
            }
        });


    }
}