package com.example.blood_donation.pdo;

import com.example.blood_donation.models.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
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
    public static void delete(String email, OnResultCallback<String> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        // Query for the user with the specified email
        Query query = usersRef.whereEqualTo("email", email).limit(1);
        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Delete the user document
                        DocumentSnapshot userSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        userSnapshot.getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    callback.onSuccess("User deleted successfully");
                                })
                                .addOnFailureListener(callback::onFailure);
                    } else {
                        // User with the specified email not found
                        callback.onSuccess("User not found");
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }

    public static void getAllUsers(OnResultCallback<List<User>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> users = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        User user = document.toObject(User.class);
                        users.add(user);
                    }
                    callback.onSuccess(users);
                })
                .addOnFailureListener(callback::onFailure);
    }
}
