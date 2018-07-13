package com.morse.httpframe;

import android.app.Application;

public class DemoApplication extends Application {

    private static DemoApplication instace;

    @Override
    public void onCreate() {
        super.onCreate();
        instace = this;
    }

    public static DemoApplication getInstace() {
        return instace;
    }
}
