package zero.okhttp_piccaso_recyclerview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Detail_View extends AppCompatActivity {
    private static final String TAG = "Detail_View";
    private ImageView detail_view;

    public static Intent newInstants(Context context,String photo){
        Intent i=new Intent(context,Detail_View.class);
        i.putExtra("photo",photo);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        initView();
        String photo=getIntent().getStringExtra("photo");
        Log.e(TAG, photo );
        Picasso.with(this)
                .load(photo)
                .into(detail_view);
    }

    private void initView() {
        detail_view = (ImageView) findViewById(R.id.detail_view);
    }
}
