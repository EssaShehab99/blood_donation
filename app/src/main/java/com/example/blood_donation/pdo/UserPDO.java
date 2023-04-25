package com.example.blood_donation.pdo;

import com.example.blood_donation.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;

public class UserPDO {
    static public User user;
    public static void create(User user, OnResultCallback<String> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .add(user.toMap())
                .addOnSuccessListener(documentReference -> {
                    String userId = documentReference.getId();
                    UserPDO.user=user;
                    UserPDO.user.id=userId;
                    callback.onSuccess(userId);
                })
                .addOnFailureListener(callback::onFailure);
    }
    public static void checkIfExist(String email, OnResultCallback<Boolean> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    boolean exists = false;
                    for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                        exists = true;
                        break;
                    }
                    if(exists) callback.onFailure( new Exception("User with email " + email + " already exists"));

                    else callback.onSuccess(exists);
                })
                .addOnFailureListener(callback::onFailure);
    }
    public static void login(String email, String password, OnResultCallback<User> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        user = User.fromMap(Objects.requireNonNull(documentSnapshot.getData()), documentSnapshot.getId());
                        callback.onSuccess(user);
                    } else {
                        callback.onFailure(new Exception("Email or password not correct"));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }
    public static void update(User user, OnResultCallback<String> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("users").document(UserPDO.user.id);
        userRef.update(user.toMap())
                .addOnSuccessListener(aVoid -> {
                    UserPDO.user = user;
                    callback.onSuccess(null);
                })
                .addOnFailureListener(callback::onFailure);
    }

}
