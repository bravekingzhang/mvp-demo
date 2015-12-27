package com.brzhang.yours;

import android.app.Application;
import android.content.Context;

/**
 * Created by brzhang on 15/12/27.
 * Description :
 */
public class App extends Application {

    private static  Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext(){
        return context;
    }
}
