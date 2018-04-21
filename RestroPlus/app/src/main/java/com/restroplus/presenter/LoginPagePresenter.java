package com.restroplus.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.restroplus.activity.LoginPage;
import com.restroplus.constants.UserConstants;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConnectivityUtils;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.LoginPageView;

import static android.provider.Settings.Global.getString;

/**
 * Created by M1037764 on 1/5/2018.
 */

public class LoginPagePresenter {

    private LoginPage loginPage;

    private LoginPageView loginPageView;

    private FirebaseAuth auth;

    private GoogleSignInOptions googleSignInOptions;

    public static final int RC_SIGN_IN = 100;

    private GoogleSignInClient googleSignInClient;

    public LoginPagePresenter(LoginPage loginPage, LoginPageView loginPageView) {
        this.loginPage = loginPage;
        this.loginPageView = loginPageView;
        this.googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(UserConstants.OAUTH_GOOGLE_CLIENT_ID)
                .requestEmail().build();
        this.googleSignInClient = GoogleSignIn.getClient(this.loginPage.getApplicationContext(),this.googleSignInOptions);
        this.auth = FirebaseAuth.getInstance();
    }

    public void onSignInButtonClick(String email, String password) {
        if(ConnectivityUtils.isDeviceConnectedToInternet(this.loginPage.getApplicationContext())) {

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                this.loginPageView.showErrorMessageForEmptyFields();
                return;
            } else {
                this.loginPageView.showProgressBar(true);
                this.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this.loginPage, task -> {
                            if (task.isSuccessful()) {
                                this.loginPageView.clearTextFields();
                                this.loginPageView.navigateToOpeningPage();
                                this.loginPageView.displayHelloMessageToUser(FirebaseUtils.getLoggedInUserDetails().getEmail());
                            } else {
                                this.loginPageView.showProgressBar(false);
                                this.loginPageView.showErrorMessageForFailedAuthentication();
                            }
                        });
            }

        }
        else{
            this.loginPageView.showMessageForNoInternetConnection();
        }

    }

    public void onRegisterButtonClick() {
        this.loginPageView.navigateToSignUpPage();
    }

    public void onGoogleSignInButtonClick() {
        this.loginPageView.signInWithGoogle(this.googleSignInClient);
    }

    @SuppressLint("LongLogTag")
    public void onIntentResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                this.authenticateWithFirebase(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("%%% google sign in &&&&&", "Google sign in failed", e);
                // ...
                this.loginPageView.showErrorMessageForFailedGoogleSignIn();
            }
        }
    }

    private void authenticateWithFirebase(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        this.auth.signInWithCredential(credential)
                .addOnCompleteListener(this.loginPage, task -> {
                    if(task.isSuccessful()){
                        loginPageView.displayHelloMessageToUser(FirebaseUtils.getLoggedInUserDetails().getEmail());
                        loginPageView.navigateToOpeningPage();
                    }
                    else{
                        loginPageView.showErrorMessageForFailedGoogleSignIn();
                    }
                });
    }

    public void registerFacebookButtonCallback(LoginButton facebookLoginButton, CallbackManager facebookCallbackManager) {
        facebookLoginButton.setReadPermissions("email", "public_profile");
        facebookLoginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                loginPageView.displayFacebookLoginCancelledMessage();
            }

            @Override
            public void onError(FacebookException exception) {
                loginPageView.showSomethingWrongWithFacebookMessage();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential facebookCredential = FacebookAuthProvider.getCredential(accessToken.getToken());
        this.auth.signInWithCredential(facebookCredential)
                .addOnCompleteListener(this.loginPage, task -> {
                    if (task.isSuccessful()) {
                        LoginPagePresenter.this.loginPageView.displayHelloMessageToUser(FirebaseUtils.getLoggedInUserDetails().getEmail());
                        LoginPagePresenter.this.loginPageView.navigateToOpeningPage();
                    } else {
                        LoginPagePresenter.this.loginPageView.showErrorMessageForFailedAuthentication();
                    }
                });
    }
}
