package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.blood_donation.databinding.ActivityMainBinding;
import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.UserPDO;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private User user;
    TextView name, email, bloodType, hasIllnesses, gender, age, city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        bloodType = findViewById(R.id.bloodType);
        hasIllnesses = findViewById(R.id.hasIllnesses);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        city = findViewById(R.id.city);

    }

    private User getUserData() {
        //TODO: Implementation to get the user data from somewhere
        return null;
    }
}


