package com.restroplus.presenter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.restroplus.activity.LoginPage;
import com.restroplus.activity.OpeningPage;
import com.restroplus.constants.UserConstants;
import com.restroplus.util.ConnectivityUtils;
import com.restroplus.view.OpeningPageView;

/**
 * Created by M1037764 on 12/15/2017.
 */

public class OpeningPagePresenter{


    private OpeningPageView openingPageView;

    private OpeningPage openingPage;

    private GoogleSignInOptions googleSignInOptions;

    private GoogleSignInClient googleSignInClient;


    public OpeningPagePresenter(OpeningPageView openingPageView, OpeningPage openingPage){
        this.openingPageView=openingPageView;
        this.openingPage = openingPage;
        this.googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(UserConstants.OAUTH_GOOGLE_CLIENT_ID)
                .requestEmail().build();
        this.googleSignInClient = GoogleSignIn.getClient(this.openingPage.getApplicationContext(),this.googleSignInOptions);
    }


    public void onGetStartedButtonClick(){
        this.openingPageView.showWelcomeToast();
    }


    public void onShowBookingsButtonClick(Context context) {
        if(ConnectivityUtils.isDeviceConnectedToInternet(context))
            this.openingPageView.navigateToBookingsPage();
        else
            this.openingPageView.showMessageForNoInternetConnection();
    }

    public void onLogoutButtonClick() {
        this.googleSignInClient.signOut()
                .addOnCompleteListener(this.openingPage, task -> {
                    this.openingPageView.showLogoutMessage();
                });
        LoginManager.getInstance().logOut();

    }
}
