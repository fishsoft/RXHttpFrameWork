package com.morse.httpframe;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("data/Android/10/1")
    Observable<Result> getXiandu();

}
