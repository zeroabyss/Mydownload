package zero.okhttp_piccaso_recyclerview.Nav;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zero.okhttp_piccaso_recyclerview.R;
import zero.okhttp_piccaso_recyclerview.database.My_Down;

public class Nav_down extends AppCompatActivity {
    private static final String TAG = "Nav_down";
    private List<My_Down> list=new ArrayList<>();
    public static void newInstance(Context context){
        Intent i=new Intent(context,Nav_down.class);
        context.startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_down);
        Log.e(TAG, "onCreate: ");
        LitePal.getDatabase();
        List<My_Down> allDownUrl=DataSupport.findAll(My_Down.class);

        for (My_Down my_down:allDownUrl){
            Log.e(TAG, my_down.getName() );
            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+my_down.getName());
            if (file.exists()){
                Log.e(TAG, "文件存在");
            }

        }
    }
}
