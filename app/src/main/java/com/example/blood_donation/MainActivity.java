package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blood_donation.databinding.ActivityMainBinding;
import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.UserPDO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextView name, email, phone, bloodType, hasIllnesses, gender, age, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.icon_user);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        bloodType = findViewById(R.id.bloodType);
        hasIllnesses = findViewById(R.id.hasIllnesses);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        city = findViewById(R.id.city);
        Log.d("user",UserPDO.user.toString());
        name.setText(UserPDO.user.name);
        email.setText(UserPDO.user.email);
        phone.setText(UserPDO.user.phone);
        bloodType.setText(UserPDO.user.bloodType);
        hasIllnesses.setText(UserPDO.user.hasIllnesses ? "Has Illnesses" : "Has Not Illnesses");
        gender.setText(UserPDO.user.gender);
        age.setText(String.valueOf(UserPDO.user.age));
        city.setText(UserPDO.user.city);

        Drawable drawable = ContextCompat.getDrawable(this, Objects.equals(UserPDO.user.gender, "Male") ?R.drawable.user_male:R.drawable.user_female);
        imageView.setImageDrawable(drawable);


    }

    public void update(View view) {
        startActivity(new Intent(MainActivity.this,EditInfoActivity.class));
    }
}


