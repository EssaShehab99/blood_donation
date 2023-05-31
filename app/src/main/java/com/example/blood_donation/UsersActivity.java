package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.OnResultCallback;
import com.example.blood_donation.pdo.UserPDO;
import com.example.blood_donation.shared.Shared;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import adapters.UsersAdapter;

public class UsersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UsersAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.recyclerView);
        fetchUsers();
    }

    void fetchUsers() {
        UserPDO.getAllUsers(new OnResultCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UsersActivity.this, LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                myAdapter = new UsersAdapter(UsersActivity.this, result);
                myAdapter.setOnCallButtonClickListener(user -> {
                    String phoneNumber = user.phone;
                    if (phoneNumber != null) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + phoneNumber));
                        startActivity(intent);
                    } else {
                        Shared.showSnackBar(findViewById(android.R.id.content), "Phone number not available");
                    }
                });
                myAdapter.setOnDeleteButtonClickListener(user -> {
                    UserPDO.delete(user.email, new OnResultCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Shared.showSnackBar(findViewById(android.R.id.content), "Success Delete");
                            fetchUsers();

                        }

                        @Override
                        public void onFailure(Exception exception) {
                            Shared.showSnackBar(findViewById(android.R.id.content), "Error");

                        }
                    });
                });
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onFailure(Exception exception) {
                Shared.showSnackBar(findViewById(android.R.id.content), exception.getMessage());
            }
        });
    }
}