package com.example.blood_donation.models;
import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
    public String id;
    public String name;
    public String email;
    public String bloodType;
    public boolean hasIllnesses;
    public String gender;
    public int age;
    public String city;
    public String password;
    public User(){}
    // Constructor
    public User(String id, String name, String email, String bloodType, boolean hasIllnesses, String gender, int age, String city) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bloodType = bloodType;
        this.hasIllnesses = hasIllnesses;
        this.gender = gender;
        this.age = age;
        this.city = city;
    }

    // Methods for JSON serialization and deserialization
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("email", email);
        result.put("bloodType", bloodType);
        result.put("hasIllnesses", hasIllnesses);
        result.put("gender", gender);
        result.put("age", age);
        result.put("city", city);
        result.put("password", password);
        return result;
    }

    public static User fromMap(Map<String, Object> map,String id) {
        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String bloodType = (String) map.get("bloodType");
        boolean hasIllnesses = (boolean) map.get("hasIllnesses");
        String gender = (String) map.get("gender");
        int age = ((Long) Objects.requireNonNull(map.get("age"))).intValue();
        String city = (String) map.get("city");
        return new User(id, name, email, bloodType, hasIllnesses,  gender, age, city);
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bloodType='" + bloodType + '\'' +
                ", hasIllnesses=" + hasIllnesses +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

}
