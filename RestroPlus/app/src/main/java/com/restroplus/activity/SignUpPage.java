package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.presenter.SignUpPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.SignUpPageView;

public class SignUpPage extends AppCompatActivity implements SignUpPageView{

    private EditText inputEmail, inputPassword;
    private Button signIn, signUp;
    private ProgressBar progressBarSignUp;
    private SignUpPagePresenter signUpPagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sign_up_page);

        this.signUpPagePresenter = new SignUpPagePresenter(this,SignUpPage.this);
        this.inputEmail = findViewById(R.id.editTextEmailSignUpPage);
        this.inputPassword = findViewById(R.id.editTextPasswordSignUpPage);
        this.signUp = findViewById(R.id.buttonSignUp);
        this.signIn = findViewById(R.id.buttonSignIn);
        this.progressBarSignUp = findViewById(R.id.progressBarSignUpPage);
        signIn.setOnClickListener(v -> this.navigateToLoginPage());

        signUp.setOnClickListener(v -> {
            String email = inputEmail.getText().toString().trim();
            String password = inputPassword.getText().toString().trim();
            this.signUpPagePresenter.onSignUpClick(email,password,getApplicationContext());
        });
    }

    @Override
    public void showErrorMessageForEmptyFields() {
        Toast.makeText(this, ErrorConstants.ONE_OR_MORE_FIELDS_EMPTY,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessageForShortPassword() {
        Toast.makeText(this,ErrorConstants.PASSWORD_LESS_THAN_8,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar(Boolean showProgressBar) {
        if(showProgressBar) {
            this.progressBarSignUp.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
        {
            this.progressBarSignUp.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void showMessageForNoInternetConnection() {
        Toast.makeText(this,ErrorConstants.NO_INTERNET_CONNECTION,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailureMessage() {
        Toast.makeText(this,ErrorConstants.USER_ALREADY_EXISTS,Toast.LENGTH_SHORT).show();
        this.navigateToLoginPage();
    }

    @Override
    public void showSuccessMessage() {
        Toast.makeText(this, UserConstants.SIGN_UP_SUCCESSFUL,Toast.LENGTH_SHORT).show();
        this.navigateToLoginPage();
    }

    @Override
    public void navigateToLoginPage() {
        Intent navigateToLoginPage = new Intent(SignUpPage.this,LoginPage.class);
        startActivity(navigateToLoginPage);
    }

    @Override
    public void onBackPressed(){
        this.navigateToLoginPage();
    }

}
