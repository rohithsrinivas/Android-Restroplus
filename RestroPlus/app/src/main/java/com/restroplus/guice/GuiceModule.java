package com.restroplus.guice;

import com.google.inject.AbstractModule;
import com.restroplus.presenter.OpeningPagePresenter;


public class GuiceModule extends AbstractModule{



	@Override
	protected void configure() {
		//bind(IOpeningPage.class).to(OpeningPagePresenter.class);
		bind(OpeningPagePresenter.class);
	}

}
