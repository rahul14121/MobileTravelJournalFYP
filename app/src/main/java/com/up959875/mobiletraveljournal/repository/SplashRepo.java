package com.up959875.mobiletraveljournal.repository;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.up959875.mobiletraveljournal.models.DataWrapper;
import com.up959875.mobiletraveljournal.models.User;
import com.up959875.mobiletraveljournal.other.Constants;
import com.up959875.mobiletraveljournal.other.Status;


//Handles getting data on the splash screen, related to checking the user is authenticated and getting their data.
public class SplashRepo {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DataWrapper<User> user = new DataWrapper<>(new User(), Status.LOADING, null);
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(Constants.USERS);


   /**
    * It checks if the user is authenticated and returns a MutableLiveData object with the user's data
    * 
    * @return A MutableLiveData object that contains a DataWrapper object that contains a User object.
    */
    public MutableLiveData<DataWrapper<User>> checkUserIsAuth() {
        MutableLiveData<DataWrapper<User>> isUserAuthMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.setAuthenticated(false);
        } else {
            user.getData().setUid(firebaseUser.getUid());
            user.setAuthenticated(true);
        }
        isUserAuthMutableLiveData.setValue(user);
        return isUserAuthMutableLiveData;
    }


    /**
     * It gets a user from the database and returns a MutableLiveData object that contains a
     * DataWrapper object that contains the user object and a status
     * 
     * @param uid The user's unique ID.
     * @return A MutableLiveData object that contains a DataWrapper object that contains a User object.
     */
    public MutableLiveData<DataWrapper<User>> getUserFromDatabase(String uid) {
        MutableLiveData<DataWrapper<User>> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful()) {
                DocumentSnapshot document = userTask.getResult();
                if (document != null && document.exists()) {
                    User user = document.toObject(User.class);
                    userMutableLiveData.setValue(new DataWrapper<>(user, Status.SUCCESS, "Authorization successful!"));
                } else {
                    userMutableLiveData.setValue(new DataWrapper<>(null, Status.ERROR, "ERROR: Current user doesn't exist"));
                }
            } else if(userTask.getException() != null) {
                userMutableLiveData.setValue(new DataWrapper<>(null, Status.ERROR, "ERROR: " + userTask.getException().getMessage()));
            }
        });
        return userMutableLiveData;
    }

}
