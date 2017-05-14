package zero.okhttp_piccaso_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class Detail_View extends AppCompatActivity {
    private static final String TAG = "Detail_View";
    private static final String WHO="who";
    private ImageView detail_view;

    public static Intent newInstants(Context context,String photo,String who){
        Intent i=new Intent(context,Detail_View.class);
        i.putExtra("photo",photo);
        i.putExtra(WHO,who);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        initView();
        String photo=getIntent().getStringExtra("photo");
        String who=getIntent().getStringExtra(WHO);
        Log.e(TAG, photo );
        switch (who){
            case "MainActivity":
                showView(photo);
                break;
            case "Nav_down":
                File file=new File(photo);
                if (file.exists()){
                    showView(file);
                }else
                    Toast.makeText(Detail_View.this,"文件加载失败",Toast.LENGTH_LONG).show();
                break;
            default:
        }
    }

    private void initView() {
        detail_view = (ImageView) findViewById(R.id.detail_view);
    }
    private void showView(String photo){
        Picasso.with(this)
                .load(photo)
                .into(detail_view);
    }
    private void showView(File file){
        Picasso.with(this)
                .load(file)
                .into(detail_view);
    }

}
