package com.restroplus.view;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;

/**
 * Created by M1037764 on 1/5/2018.
 */

public interface LoginPageView {

    void displayHelloMessageToUser(String loggedInUserMail);

    void showErrorMessageForEmptyFields();

    void navigateToOpeningPage();

    void showProgressBar(Boolean showProgressBar);

    void showMessageForNoInternetConnection();

    void showErrorMessageForFailedAuthentication();

    void clearTextFields();

    void navigateToSignUpPage();

    void signInWithGoogle(GoogleSignInClient googleSignInClient);

    void showErrorMessageForFailedGoogleSignIn();

    void displayFacebookLoginCancelledMessage();

    void showSomethingWrongWithFacebookMessage();
}
