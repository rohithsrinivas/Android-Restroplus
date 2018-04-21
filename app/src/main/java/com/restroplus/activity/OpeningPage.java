package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.presenter.OpeningPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.util.ServerUtil;
import com.restroplus.view.OpeningPageView;

public class OpeningPage extends AppCompatActivity implements OpeningPageView{

    private Button searchRestaurantsButton;

    private Button showBookingsButton;

    private AlertDialog.Builder logoutDialog;

    //@Inject
    private OpeningPagePresenter openingPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_opening_page);
        this.searchRestaurantsButton = findViewById(R.id.getStartedButton);
        this.showBookingsButton = findViewById(R.id.buttonShowBookings);
        this.openingPagePresenter=new OpeningPagePresenter(this,OpeningPage.this);
        this.searchRestaurantsButton.setOnClickListener(v -> this.openingPagePresenter.onGetStartedButtonClick());
        this.showBookingsButton.setOnClickListener(v -> this.openingPagePresenter.onShowBookingsButtonClick(getApplicationContext()));
//        this.logoutButton.setOnClickListener(v -> this.showDialogForLoggingOut());

    }


    @Override
    public void showWelcomeToast() {
        ServerUtil.isBookingRestServiceUp();
        Intent navigateToSearchPage=new Intent(this, SearchPage.class);
        startActivity(navigateToSearchPage);
    }

    @Override
    public void showMessageForNoInternetConnection() {
        Toast.makeText(this, ErrorConstants.NO_INTERNET_CONNECTION,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToBookingsPage() {
        // TODO: 1/2/2018  navigate to the my bookings page to show all the bookings based on user id after implementing log in functionality
        Intent navigateToBookingsPage = new Intent(OpeningPage.this,ViewBookings.class);
        startActivity(navigateToBookingsPage);
    }

    @Override
    public void onBackPressed(){
        this.showDialogForLoggingOut();
    }

    @Override
    public void showDialogForLoggingOut(){
        this.logoutDialog = new AlertDialog.Builder(this);
        this.logoutDialog.setTitle(UserConstants.CLEAR_SESSION_HEADER)
                .setMessage(UserConstants.GOING_BACK_MESSAGE)
                .setPositiveButton(UserConstants.YES, (dialog, which) -> {
                    FirebaseUtils.killUserSession();
                    this.openingPagePresenter.onLogoutButtonClick();
                    Intent navigateBack = new Intent(OpeningPage.this, LoginPage.class);
                    startActivity(navigateBack);
                })
                .setNegativeButton(UserConstants.NO, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void showLogoutMessage() {
        Toast.makeText(this,UserConstants.YOU_ARE_LOGGED_OUT,Toast.LENGTH_SHORT).show();
    }


//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this,UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(OpeningPage.this, LoginPage.class));
//        }
//    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        FirebaseUtils.killUserSession();
        LoginManager.getInstance().logOut();
    }
}
