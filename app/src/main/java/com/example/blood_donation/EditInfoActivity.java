package com.example.blood_donation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.blood_donation.models.User;
import com.example.blood_donation.pdo.OnResultCallback;
import com.example.blood_donation.pdo.UserPDO;
import com.example.blood_donation.shared.Shared;

import java.util.Arrays;

public class EditInfoActivity extends AppCompatActivity {

    EditText name, email,phone, age, password;
    RadioGroup genderRadioGroup;

    CheckBox hasIllnesses;
    Spinner bloodTypeSpinner, citySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        name = findViewById(R.id.name_et);
        email = findViewById(R.id.email_et);
        phone=findViewById(R.id.phone_et);
        age = findViewById(R.id.age_et);
        password = findViewById(R.id.password_et);
        hasIllnesses = findViewById(R.id.has_illnesses_checkbox);
        genderRadioGroup = findViewById(R.id.gender_radio_group);

        bloodTypeSpinner = findViewById(R.id.blood_type_spinner);
        String[] bloodTypes = getResources().getStringArray(R.array.blood_types_array);
        ArrayAdapter<String> bloodTypeAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, bloodTypes);
        bloodTypeAdapter.setDropDownViewResource(R.layout.spinner_item);
        bloodTypeSpinner.setAdapter(bloodTypeAdapter);

        citySpinner = findViewById(R.id.city_spinner);
        String[] cities = getResources().getStringArray(R.array.saudi_cities);
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, cities);
        cityAdapter.setDropDownViewResource(R.layout.spinner_item);
        citySpinner.setAdapter(cityAdapter);

        bloodTypeSpinner.setSelection(2);

        name.setText(UserPDO.user.name);
        email.setText(UserPDO.user.email);
        phone.setText(UserPDO.user.phone);
        age.setText(String.valueOf(UserPDO.user.age));
        bloodTypeSpinner.setSelection(Arrays.asList(bloodTypes).indexOf(UserPDO.user.bloodType));
        citySpinner.setSelection(Arrays.asList(cities).indexOf(UserPDO.user.city));
        hasIllnesses.setChecked(UserPDO.user.hasIllnesses);
        if (UserPDO.user.gender.equals("Male")) {
            genderRadioGroup.check(R.id.male_radio_button);
        } else if (UserPDO.user.gender.equals("Female")) {
            genderRadioGroup.check(R.id.female_radio_button);
        }
        findViewById(R.id.edit_btn).setOnClickListener(v -> {
            update();
        });
    }

    private void update() {

        if (TextUtils.isEmpty(name.getText())) {
            name.setError("Name is required");
        } else  if (TextUtils.isEmpty(age.getText())) {
            age.setError("Age is required");
        } else  if (TextUtils.isEmpty(password.getText())) {
            password.setError("Password is required");
        }  else  if (TextUtils.isEmpty(phone.getText())) {
            phone.setError("Phone is required");
        }else if (TextUtils.isEmpty(email.getText())) {
            // The email field is empty
            email.setError("Email is required");
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
            // The email address is not valid
            email.setError("Invalid email address");
        } else {
            User user = new User();
            user.name = String.valueOf(name.getText());
            user.email = String.valueOf(email.getText());
            user.phone = String.valueOf(phone.getText());
            user.bloodType = (String) bloodTypeSpinner.getSelectedItem();
            user.city = (String) citySpinner.getSelectedItem();
            user.hasIllnesses = hasIllnesses.isChecked();
            user.age = Integer.parseInt(age.getText().toString());
            user.password = String.valueOf(password.getText());
            switch (genderRadioGroup.getCheckedRadioButtonId()) {
                case R.id.male_radio_button:
                    user.gender = "Male";
                    break;
                case R.id.female_radio_button:
                    user.gender = "Female";
                    break;
            }
            UserPDO.update(user, new OnResultCallback<String>() {
                @Override
                public void onSuccess(String _) {
                    startActivity(new Intent(EditInfoActivity.this, MainActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Exception exception) {
                    Shared.showSnackBar(findViewById(android.R.id.content), exception.getMessage());
                }
            });

        }
    }
}