package com.restroplus.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.restroplus.constants.ErrorConstants;
import com.restroplus.constants.RESTServiceConstants;
import com.restroplus.constants.UserConstants;
import com.restroplus.model.Restaurant;
import com.restroplus.presenter.SearchPagePresenter;
import com.restroplus.restroplus.R;
import com.restroplus.util.ConversionUtil;
import com.restroplus.util.FirebaseUtils;
import com.restroplus.view.SearchPageView;

import java.util.List;

public class SearchPage extends AppCompatActivity implements SearchPageView{

    private EditText searchTermEditText,numberOfTablesEditText;
    private Button getRestaurantsButton;
    private RadioGroup searchTermRadioGroup;
    private SearchPagePresenter searchPagePresenter;
    private ProgressBar progressBarForResults;
    private int numberOfTablesRequired;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_search_page);
        searchTermEditText = findViewById(R.id.editTextSearchTerm);
        numberOfTablesEditText = findViewById(R.id.editTextForNumberOfTablesRequired);
        getRestaurantsButton = findViewById(R.id.buttonGetRestaurants);
        searchTermRadioGroup = findViewById(R.id.radioGroup);
        progressBarForResults = findViewById(R.id.progressBarForResults);
        searchPagePresenter = new SearchPagePresenter(this);

        getRestaurantsButton.setOnClickListener(v -> {
            String searchTerm = searchTermEditText.getText().toString().trim();
            String numberOfTables = numberOfTablesEditText.getText().toString().trim();
            numberOfTablesRequired = ConversionUtil.convertStringToInteger(numberOfTables);
            int index = searchTermRadioGroup.indexOfChild(findViewById(searchTermRadioGroup.getCheckedRadioButtonId()));
            searchPagePresenter.onGetRestaurantsButtonClick(searchTerm,numberOfTablesRequired,index,getApplicationContext());

        });
    }


    @Override
    public void showErrorMessageForEmptyOrInvalidFields() {
        Toast.makeText(this, ErrorConstants.ONE_OR_MORE_FIELDS_EMPTY,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getResultsFromServiceAndNavigateToResultsPage() {
        Toast.makeText(this,"All the inputs are valid, good to go",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showJsonInToast(List<Restaurant> restaurants) {
        Toast.makeText(this,""+restaurants,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoRestaurantsFoundMessage() {
        Toast.makeText(this, UserConstants.NO_RESTAURANTS_FOUND,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateToSearchResultsPage(List<Restaurant> restaurants) {
        Intent navigateToResultsPage  = new Intent(this, SearchResults.class);
        navigateToResultsPage.putExtra("searchResults",ConversionUtil.convertListToJsonString(restaurants));
        navigateToResultsPage.putExtra("numberOfTablesRequired",numberOfTablesRequired);
        startActivity(navigateToResultsPage);
    }

    @Override
    public void showProgressBar(Boolean showProgressBar) {
        if(showProgressBar) {
            progressBarForResults.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else
            {
            progressBarForResults.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void dismissProgressBar() {
        progressBarForResults.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showServerDownMessage() {
        Toast.makeText(this, RESTServiceConstants.SERVER_DOWN_MESSAGE,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSomethingWrongMessage() {
        Toast.makeText(this,ErrorConstants.SOMETHING_WRONG_RESULTS_MESSAGE,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMessage() {
        Toast.makeText(this,"inside do on next method",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMessageForNoInternetConnection() {
        Toast.makeText(this,ErrorConstants.NO_INTERNET_CONNECTION,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed(){
        Intent navigateBackToOpeningPage = new Intent(SearchPage.this,OpeningPage.class);
        startActivity(navigateBackToOpeningPage);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        if(!FirebaseUtils.isUserSessionValid()) {
//            Toast.makeText(this,UserConstants.SESSION_EXPIRED_MESSAGE,Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(SearchPage.this, LoginPage.class));
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
