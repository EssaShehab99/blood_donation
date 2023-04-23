package com.example.blood_donation.models;
import org.json.JSONException;
import org.json.JSONObject;

public class Person {
    public String id;
    public String name;
    public String bloodType;
    public boolean hasIllnesses;
    public String type;
    public String gender;
    public int age;
    public String city;

    // Constructor
    public Person(String id, String name, String bloodType, boolean hasIllnesses, String type, String gender, int age, String city) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.hasIllnesses = hasIllnesses;
        this.type = type;
        this.gender = gender;
        this.age = age;
        this.city = city;
    }

    // Methods for JSON serialization and deserialization
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("name", this.name);
        jsonObject.put("bloodType", this.bloodType);
        jsonObject.put("hasIllnesses", this.hasIllnesses);
        jsonObject.put("type", this.type);
        jsonObject.put("gender", this.gender);
        jsonObject.put("age", this.age);
        jsonObject.put("city", this.city);
        return jsonObject;
    }

    public static Person fromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        String bloodType = jsonObject.getString("bloodType");
        boolean hasIllnesses = jsonObject.getBoolean("hasIllnesses");
        String type = jsonObject.getString("type");
        String gender = jsonObject.getString("gender");
        int age = jsonObject.getInt("age");
        String city = jsonObject.getString("city");
        return new Person(id, name, bloodType, hasIllnesses, type, gender, age, city);
    }
}
