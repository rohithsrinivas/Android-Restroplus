package com.restroplus.util;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.restroplus.constants.UserConstants;

/**
 * Created by M1037764 on 1/5/2018.
 */

public class FirebaseUtils {

    public static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static GoogleSignInOptions googleSignInOptions;

    public static GoogleSignInClient googleSignInClient;

    public static boolean isUserSessionValid(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            return true;
        }
        return false;
    }

    public static FirebaseUser getLoggedInUserDetails(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public static void killUserSession(){
        FirebaseAuth.getInstance().signOut();
    }

    public static void googleAccountSignOut(Activity page){
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(UserConstants.OAUTH_GOOGLE_CLIENT_ID)
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(page.getApplicationContext(),googleSignInOptions);
        googleSignInClient.signOut()
                .addOnCompleteListener(page, task -> {

                });
    }



}
