package com.restroplus.view;

import android.content.Context;

/**
 * Created by M1037764 on 12/15/2017.
 */

public interface OpeningPageView {

    void showWelcomeToast();

    void showMessageForNoInternetConnection();

    void navigateToBookingsPage();

    void showDialogForLoggingOut();

    void showLogoutMessage();
}
