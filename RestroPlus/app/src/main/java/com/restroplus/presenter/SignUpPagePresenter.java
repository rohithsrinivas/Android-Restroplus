package com.restroplus.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.restroplus.activity.SignUpPage;
import com.restroplus.util.ConnectivityUtils;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.SignUpPageView;

/**
 * Created by M1037764 on 1/4/2018.
 */

public class SignUpPagePresenter {

    private FirebaseAuth auth;

    private SignUpPageView signUpPageView;

    private SignUpPage signUpPage;

    private String email,password;

    public SignUpPagePresenter(SignUpPageView signUpPageView, SignUpPage signUpPage) {
        this.signUpPageView = signUpPageView;
        this.auth = FirebaseAuth.getInstance();
        this.signUpPage = signUpPage;
    }

    public void onSignUpClick(String email, String password, Context context){
        this.email = email;
        this.password = password;
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            this.signUpPageView.showErrorMessageForEmptyFields();
            return;
        }
        if(password.length()<8){
            this.signUpPageView.showErrorMessageForShortPassword();
            return;
        }
        else
        {
            if(ConnectivityUtils.isDeviceConnectedToInternet(context)) {
                this.signUpPageView.showProgressBar(true);
                Log.d("sign up page", "&&&&&&&&&&&&&&&&&&&&before the function call");
                this.createNewUser(this.email, this.password);
            }
            else{
                this.signUpPageView.showMessageForNoInternetConnection();
            }
        }
    }

    private void createNewUser(String email, String password) {
        this.auth
                .createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this.signUpPage, task -> {
                    signUpPageView.showProgressBar(false);
                    if(!task.isSuccessful()){
                        signUpPageView.showFailureMessage();
                        Log.d("Signuppage presenter","&*(#%^@$*(@#%$^*(%($23 registration failed");
                    }
                    else {
                        signUpPageView.showSuccessMessage();
                        FirebaseUtils.killUserSession();
                        signUpPageView.navigateToLoginPage();
                        Log.d("Signuppage presenter","@@@@@@@@@@@@@@@@ registration successful");
                        return;
                    }
                });
    }


}
