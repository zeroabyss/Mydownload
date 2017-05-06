package zero.okhttp_piccaso_recyclerview;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class MyIntentService extends IntentService {
    public static final String PATH="zero.MyIntentService.path";
    public static final String NAME="zero.MyIntentService.name";

    public static void newInstance(Context context,String url,String name){
        Intent i=new Intent(context,MyIntentService.class);
        i.putExtra(PATH,url);
        i.putExtra(NAME,name);
        context.startService(i);
    }

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String path=intent.getStringExtra(PATH);
            final String name=intent.getStringExtra(NAME);
            down(path,name);
        }
    }

    private void down(String url,String name){

        OkHttpClient client=new OkHttpClient();
        Request request =new Request.Builder()
                .url(url)
                .build();
        InputStream inputStream=null;
        File file=null;
        FileOutputStream fileOutputStream=null;
        try{
            Response response=client.newCall(request).execute();

            String sdPath= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
            if (response!=null){

                   /* inputStream=response.body().byteStream();
                    file=new File(sdPath+"/"+name);
                Log.e(TAG, file.getPath() );
                    byte[] b=new byte[1024];
                    int len;
                    fileOutputStream=new FileOutputStream(file);
                    while((len=inputStream.read(b))!=-1){
                        fileOutputStream.write(b,0,len);
                    }
                    response.body().close();
                Log.e(TAG, "下载完成");*/
                //Toast.makeText(MyApplication.getContext(),"下载完成",Toast.LENGTH_SHORT).show();
                inputStream=response.body().byteStream();
                Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                file=new File(sdPath+"/"+name);
                fileOutputStream=new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
                insertImageToSystemGallery(MyIntentService.this,file.getPath(),bitmap);
                Handler handler=new Handler(getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyIntentService.this,"下载完成",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e){
             e.printStackTrace();
        }finally {
            try{
              if (inputStream!=null){
                 inputStream.close();
              }
              if (fileOutputStream!=null){
                fileOutputStream.close();
              }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void insertImageToSystemGallery(Context context, String filePath, Bitmap bitmap){
        MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "", "");
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(new File(filePath));
        intent.setData(uri);
        context.sendBroadcast(intent);
    }
}
