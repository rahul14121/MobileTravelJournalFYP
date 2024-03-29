package com.up959875.mobiletraveljournal.other;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.up959875.mobiletraveljournal.R;
import com.up959875.mobiletraveljournal.other.Constants;


//Class to deal with Google sign-ins and sign-ups.
public class GoogleService {

    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount googleSignInAccount;


    public GoogleSignInClient getGoogleSignInClient() {
        return googleSignInClient;
    }


    public GoogleSignInAccount getGoogleSignInAccount() {
        return googleSignInAccount;
    }


    //Initialises the app to sign in, and uses the ID to get authentication from Google login services.
    public void initGoogleSignInClient(Context context) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1041764244766-385gh2mpf7mbdqvv15fevv74jdie8r5a.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions);
    }


    //Takes the account used when signed in, and checks the data, and if it can be used to sign in.
    public String getGoogleSignInAccount(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null)
                    return Constants.SUCCESS;
            } catch (ApiException e) {
                String statusCode = CommonStatusCodes.getStatusCodeString(e.getStatusCode());
                String message = "Error: Internal API error";
                if (statusCode.equals("NETWORK_ERROR"))
                    message = "Error: Please check your network connection";
                else if (statusCode.equals("TIMEOUT"))
                    message = "Error: Timed out while awaiting the result";
                return message;
            }
        }
        return "Error: Activity request error";
    }


    public AuthCredential getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        return GoogleAuthProvider.getCredential(googleTokenId, null);
    }

}
