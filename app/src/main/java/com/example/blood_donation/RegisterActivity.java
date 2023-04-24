package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.OnResultCallback;
import com.example.blood_donation.pdo.UserPDO;
import com.example.blood_donation.shared.Shared;

import org.json.JSONException;

public class RegisterActivity extends AppCompatActivity {
    EditText name, email, age, password;
    RadioGroup genderRadioGroup ;

    CheckBox hasIllnesses;
    Spinner bloodTypeSpinner, citySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name=findViewById(R.id.name_et);
        email=findViewById(R.id.email_et);
        age=findViewById(R.id.age_et);
        password=findViewById(R.id.password_et);
        hasIllnesses=findViewById(R.id.has_illnesses_checkbox);
        genderRadioGroup = findViewById(R.id.gender_radio_group);

        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        ArrayAdapter<String> bloodTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.blood_types_array));
        bloodTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodTypeSpinner.setAdapter(bloodTypeAdapter);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.saudi_cities));
        cityAdapter.setDropDownViewResource(R.layout.spinner_item);
        citySpinner.setAdapter(cityAdapter);
        findViewById(R.id.register_btn).setOnClickListener(v -> {
            create();
        });
    }
    private void create(){
        User user=new User();
        user.name=String.valueOf(name.getText());
        user.email=String.valueOf(email.getText());
        user.bloodType= (String) bloodTypeSpinner.getSelectedItem();
        user.city= (String) citySpinner.getSelectedItem();
        user.hasIllnesses= hasIllnesses.isChecked();
        user.age=  Integer.parseInt(age.getText().toString());
        user.password=String.valueOf(password.getText());
        switch (genderRadioGroup.getCheckedRadioButtonId()) {
            case R.id.male_radio_button:
                user.gender = "Male";
                break;
            case R.id.female_radio_button:
                user.gender = "Female";
                break;
        }
        Log.d("user",user.toString());

        UserPDO.checkIfExist(String.valueOf(email.getText()), new OnResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                UserPDO.create(user, new OnResultCallback<String>() {
                    @Override
                    public void onSuccess(String _) {
                        startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Shared.showSnackBar(findViewById(android.R.id.content),exception.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Exception exception) {
                Shared.showSnackBar(findViewById(android.R.id.content),exception.getMessage());
            }
        });
    }
}