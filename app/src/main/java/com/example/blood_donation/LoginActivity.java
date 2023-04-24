package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.OnResultCallback;
import com.example.blood_donation.pdo.UserPDO;
import com.example.blood_donation.shared.Shared;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        findViewById(R.id.login_btn).setOnClickListener(v -> {
            if (TextUtils.isEmpty(email.getText()))
                email.setError("Enter value");
            else if (TextUtils.isEmpty(password.getText()))
                password.setError("Enter value");
            else {
                login();
            }
        });
        findViewById(R.id.not_have_account).setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }


    private void login() {
        UserPDO.login(String.valueOf(email.getText()), String.valueOf(password.getText()), new OnResultCallback<User>() {
            @Override
            public void onSuccess(User result) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Exception exception) {
                Shared.showSnackBar(findViewById(android.R.id.content), exception.getMessage());
            }
        });
    }
}