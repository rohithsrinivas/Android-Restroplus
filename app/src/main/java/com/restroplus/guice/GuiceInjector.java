package com.restroplus.guice;

import android.util.Log;
import android.widget.Toast;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceInjector {
	
	public static Injector injector;
	
	static{
		injector=Guice.createInjector(new GuiceModule());
		Log.d("message","working");
	}

	public static void main(String[] abc){

	}

}
