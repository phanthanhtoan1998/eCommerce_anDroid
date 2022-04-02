package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegsterActivity<name> extends AppCompatActivity {

private  ProgressDialog loadingBar ;
    private  Button createAccoutunButton ;
    private  EditText InputName,InputPhoneNumber ,InputPassword ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regster);
        createAccoutunButton=(Button)findViewById(R.id.register_btn);
        InputName=(EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber=(EditText) findViewById(R.id.register_phone_number_input);
        InputPassword=(EditText) findViewById(R.id.register_password_input);
        loadingBar=new ProgressDialog(this);
createAccoutunButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        CreateAccount();
    }

    private void CreateAccount() {
        String name =InputName.getText().toString() ;
        String phone =InputPhoneNumber.getText().toString() ;
        String password =InputPassword.getText().toString() ;
        if(TextUtils.isEmpty(name)){

            Toast.makeText(RegsterActivity.this, "hay nhap ten cua ban", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(phone)){

            Toast.makeText(RegsterActivity.this, "hay nhap so dien thoai cua ban", Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(password)){

            Toast.makeText(RegsterActivity.this, "hay nhap password cua ban", Toast.LENGTH_SHORT).show();
        }
        else {

            loadingBar.setTitle("create Account");
            loadingBar.setMessage("vui long doi");
            loadingBar.setCanceledOnTouchOutside(false) ;
            loadingBar.show();
            ValidatephoneNumber(name,phone,password);

        }
    }

});

    }

    private void ValidatephoneNumber(String name, String phone, String password) {

        final DatabaseReference RootRef ;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {

            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(!(datasnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object> userdataMap =new   HashMap<>();

                    userdataMap.put("phone",phone);
                    userdataMap.put("password",password);
                    userdataMap.put("name",name);
                    RootRef.child("Users").child(phone).updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegsterActivity.this, "chuc mung ban tao duoc tai khoan", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent =new Intent(RegsterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else  {
                                loadingBar.dismiss();
                                Toast.makeText(RegsterActivity.this, "mang khong on dinh vui long thu lai sau", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else {

                    Toast.makeText(RegsterActivity.this, "so dien thoa "+phone+"da ton tai", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                    Toast.makeText(RegsterActivity.this, "thu lai bang so dien thoai khac", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(RegsterActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}