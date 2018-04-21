package com.restroplus.retrofit;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
	
	public static Retrofit retrofit;
	
	public static Retrofit buildRetrofitClient(String baseUrl) {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(30, TimeUnit.SECONDS)
				.readTimeout(30,TimeUnit.SECONDS)
				.writeTimeout(30,TimeUnit.SECONDS)
				.build();
		retrofit=new Retrofit.Builder()
				.baseUrl(baseUrl)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.client(okHttpClient)
				.build();

		return retrofit;
	}
	
	
}
