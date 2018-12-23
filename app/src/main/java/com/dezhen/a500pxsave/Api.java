package com.dezhen.a500pxsave;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by root on 24/07/17.
 */

public class Api {
    private static final String BASE_URL = "https://500px.com/";

    public interface HtmlApi {
        @GET("photo/{code}")
        Observable<String> load(@Path("code") String code);
    }

    public static final Observable<String> loadHtml(String code) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit.create(HtmlApi.class).load(code);
    }
}
