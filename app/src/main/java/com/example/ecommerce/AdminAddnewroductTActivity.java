package com.example.ecommerce;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class AdminAddnewroductTActivity extends AppCompatActivity {


    private  String CategoryName,Description,Price,Pname,saveCurrentDate,saveCurrentTime;
    private  Button AddNewProductButton ;
    private  ImageView InputProductIImage ;
    private  EditText  InputProductName ,InputProductDescription ,InputProductPrice ;

    private  String  productRandomKey,downloadImageUrl ;
    private static final int GalleryPick = 1  ;
    private Uri ImageUri ;
    private  StorageReference ProductImagesRef ;
    private  DatabaseReference ProductRef ;
    private ProgressDialog loadingBar ;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_addnewroduct_t);

        CategoryName=getIntent().getExtras().get("category").toString();
        ProductImagesRef=FirebaseStorage.getInstance().getReference().child("Product Images");

        ProductRef= FirebaseDatabase.getInstance().getReference().child("Products");



        AddNewProductButton=(Button)findViewById(R.id.add_new_pruductc);
        InputProductIImage =(ImageView) findViewById(R.id.select_product_image);
        InputProductName =(EditText) findViewById(R.id.product_name);
        InputProductDescription =(EditText)findViewById(R.id.product_description);
        InputProductPrice =(EditText)findViewById(R.id.product_price);
        loadingBar=new ProgressDialog(this);


        InputProductIImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick&&resultCode==RESULT_OK&&data!=null)
        {
            ImageUri=data.getData() ;
            InputProductIImage.setImageURI(ImageUri);


        }
    }

    private void ValidateProductData() {
        Description=InputProductDescription.getText().toString();

        Price=InputProductPrice.getText().toString();
        Pname=InputProductName.getText().toString();
        if(ImageUri==null)
        {
            Toast.makeText(this, "nhap hinh anh san  pham ... ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "nhap mieu ta san pham.... ", Toast.LENGTH_SHORT).show();

        }

         else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "nhap gia san pham.... ", Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "nhap ten  san pham.... ", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }


    }



    private void OpenGallery() {
        Intent galleryIntent =new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);

    }



    private void StoreProductInformation()
    {
        loadingBar.setTitle("them moi san pham");
        loadingBar.setMessage("vui long doi....");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar =Calendar.getInstance();
        SimpleDateFormat  currentDate =new SimpleDateFormat( "MMM dd, yyyy");
        saveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat  currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=currentTime.format(calendar.getTime());
        productRandomKey=saveCurrentDate   + saveCurrentTime ;
        StorageReference filePath= ProductImagesRef.child(ImageUri.getLastPathSegment()+productRandomKey+". jpg");

        final UploadTask uploadTask=filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {

                String message =e.toString() ;
                Toast.makeText(AdminAddnewroductTActivity.this, "Error", Toast.LENGTH_SHORT).show();

                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddnewroductTActivity.this, "luu hinh anh san pham ...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask =uploadTask.continueWithTask(new   Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();

                        }


                        downloadImageUrl=filePath.getDownloadUrl().toString();
                        return  filePath.getDownloadUrl() ;
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddnewroductTActivity.this, "hinh anh san pham  da luu vao ", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase () {

            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid", productRandomKey);
            productMap.put("date", saveCurrentDate);
            productMap.put("time", saveCurrentTime);
            productMap.put("description", Description);
            productMap.put("image", downloadImageUrl);
            productMap.put("category", CategoryName);
            productMap.put("price", Price);
            productMap.put("pname", Pname);
            ProductRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        Intent intent = new Intent(AdminAddnewroductTActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                        loadingBar.dismiss();



                        Toast.makeText(AdminAddnewroductTActivity.this, "san pham da duoc them vao thanh cong ", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AdminAddnewroductTActivity.this, "Error " + message, Toast.LENGTH_SHORT).show();

                    }
                }
            });




    }
}