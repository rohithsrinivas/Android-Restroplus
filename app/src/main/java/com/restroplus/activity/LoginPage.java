package com.restroplus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.presenter.LoginPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.LoginPageView;

public class LoginPage extends AppCompatActivity implements LoginPageView{

    private LoginPagePresenter loginPagePresenter;

    private Button signInButton, registerButton;

    private SignInButton googleSignInButton;

    private EditText emailInput, passwordInput;

    private ProgressBar progressBarLogin;

    private LoginButton facebookLoginButton;

    private CallbackManager facebookCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_page);
        this.loginPagePresenter = new LoginPagePresenter(this,LoginPage.this);
        this.signInButton = findViewById(R.id.buttonSignInLoginPage);
        this.emailInput = findViewById(R.id.editTextEmailLoginPage);
        this.passwordInput = findViewById(R.id.editTextPasswordLoginPage);
        this.progressBarLogin = findViewById(R.id.progressBarLoginPage);
        this.registerButton = findViewById(R.id.buttonRegisterLoginPage);
        this.googleSignInButton = findViewById(R.id.google_sign_in_button);
        this.facebookLoginButton = findViewById(R.id.facebook_login_button);
        this.facebookCallbackManager = CallbackManager.Factory.create();
        this.loginPagePresenter.registerFacebookButtonCallback(this.facebookLoginButton,this.facebookCallbackManager);
        this.signInButton.setOnClickListener(v -> {
            this.loginPagePresenter.onSignInButtonClick(
                    emailInput.getText().toString().trim(),
                    passwordInput.getText().toString().trim()
                    );
        });
        this.registerButton.setOnClickListener(v -> this.loginPagePresenter.onRegisterButtonClick());
        this.googleSignInButton.setOnClickListener(v -> this.loginPagePresenter.onGoogleSignInButtonClick());
    }

    @Override
    public void onStart() {
        super.onStart();
        if(FirebaseUtils.isUserSessionValid()){
            this.displayHelloMessageToUser(FirebaseUtils.getLoggedInUserDetails().getEmail());
            this.navigateToOpeningPage();
        }
    }

    @Override
    public void displayHelloMessageToUser(String loggedInUserMail) {
        Toast.makeText(this, UserConstants.WELCOME+loggedInUserMail+"!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessageForEmptyFields() {
        Toast.makeText(this, ErrorConstants.ONE_OR_MORE_FIELDS_EMPTY,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToOpeningPage() {
        startActivity(new Intent(LoginPage.this,OpeningPage.class));
    }

    @Override
    public void showProgressBar(Boolean showProgressBar) {
        if(showProgressBar) {
            this.progressBarLogin.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            this.progressBarLogin.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showMessageForNoInternetConnection() {
        Toast.makeText(this,ErrorConstants.NO_INTERNET_CONNECTION,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessageForFailedAuthentication() {
        Toast.makeText(this,ErrorConstants.INVALID_CREDENTIALS,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clearTextFields() {
        this.emailInput.setText(UserConstants.EMPTY_STRING);
        this.passwordInput.setText(UserConstants.EMPTY_STRING);
    }

    @Override
    public void navigateToSignUpPage() {
        Intent navigateToSignUpPage = new Intent(LoginPage.this,SignUpPage.class);
        startActivity(navigateToSignUpPage);
    }

    @Override
    public void signInWithGoogle(GoogleSignInClient googleSignInClient) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, LoginPagePresenter.RC_SIGN_IN);
    }

    @Override
    public void showErrorMessageForFailedGoogleSignIn() {
        Toast.makeText(this,ErrorConstants.GOOGLE_SIGN_IN_FAILED,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayFacebookLoginCancelledMessage() {
        Toast.makeText(this,UserConstants.FACEBOOK_LOGIN_CANCELLED,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSomethingWrongWithFacebookMessage() {
        Toast.makeText(this,"something was wrong with facebook login",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        // TODO: 1/11/2018 minimize the application on back button clicked 
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loginPagePresenter.onIntentResult(requestCode, resultCode, data);
        this.facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
