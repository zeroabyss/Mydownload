package zero.okhttp_piccaso_recyclerview;

import android.app.Activity;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aiy on 2017/4/19.
 */

public class OkHttpUtils {
    private OkHttpClient client;


    private OkHttpUtils(){

        client = new OkHttpClient();

    }

    private static OkHttpUtils utils;

    public static OkHttpUtils getInstance() {


        if(utils==null){
            synchronized (OkHttpUtils.class){
                if(utils==null){
                    utils = new OkHttpUtils();

                    return utils;
                }
            }
        }
        return utils;
    }

    //定义一个callback接口并定义范型（适用于任意类的返回对象）用于接口回调
    interface CallBack<T>{
        void getData(T t);
    }

    public <T extends Object>void getBeanOfOk(final Activity activity, String url, final Class<T> clazz, final CallBack<T> callback){

        Request request = new Request.Builder().get().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body().string();

                final T t  = new Gson().fromJson(json,clazz);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(t!=null){
                            callback.getData(t);
                        }
                    }
                });
            }
        });
    }

    public void downIMG(String url,String name,Callback callback){
        Request request=new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
