package com.example.blood_donation.pdo;

import org.json.JSONException;

public interface OnResultCallback <T>{
    void onSuccess(T result) ;
    void onFailure(Exception exception);
}
