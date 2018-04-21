package com.restroplus.presenter;

import com.restroplus.interactor.ViewBookingsInteractor;
import com.restroplus.view.ViewBookingsView;

/**
 * Created by M1037764 on 1/4/2018.
 */

public class ViewBookingsPresenter {

    private ViewBookingsView viewBookingsView;

    private ViewBookingsInteractor viewBookingsInteractor;

    public ViewBookingsPresenter(ViewBookingsView viewBookingsView) {
        this.viewBookingsView = viewBookingsView;
        this.viewBookingsInteractor = new ViewBookingsInteractor(viewBookingsView);
    }


    public void onPageLoad() {
        this.viewBookingsView.showProgressBar(true);
        this.viewBookingsInteractor.fetchAllBookingsForUser();
    }
}
