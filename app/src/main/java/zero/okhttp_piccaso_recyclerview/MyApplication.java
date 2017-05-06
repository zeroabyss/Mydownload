package zero.okhttp_piccaso_recyclerview;

import android.app.Application;
import android.content.Context;

/**
 * Created by Aiy on 2017/5/4.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
