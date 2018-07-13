package com.morse.httpframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.morse.httpframework.RetrofitCreateHelper;
import com.morse.httpframework.RxHelper;
import com.morse.httpframework.RxManager;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RxManager().register(getObservable().subscribe(new Consumer<Result>() {
            @Override
            public void accept(Result result) throws Exception {
                Log.d("morse", result.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("morse", throwable.getLocalizedMessage());
            }
        }));


    }

    public Observable<Result> getObservable() {
        return RetrofitCreateHelper.getInstance(DemoApplication.getInstace(), "http://gank.io/api/")
                .createApi(Api.class)
                .getXiandu()
//                .compose(RxHelper.<Result>transformer())
                .compose(RxHelper.<Result>rxSchedulerHelper());
    }
}
