package com.restroplus.view;

/**
 * Created by M1037764 on 1/4/2018.
 */

public interface SignUpPageView {

    void showErrorMessageForEmptyFields();

    void showErrorMessageForShortPassword();

    void showProgressBar(Boolean showProgressBar);

    void showMessageForNoInternetConnection();

    void showFailureMessage();

    void showSuccessMessage();

    void navigateToLoginPage();
}
