package zero.okhttp_piccaso_recyclerview.Nav;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.zip.Inflater;

import zero.okhttp_piccaso_recyclerview.R;
import zero.okhttp_piccaso_recyclerview.database.My_Down;

public class Nav_down extends AppCompatActivity {
    private static final String TAG = "Nav_down";
    private RecyclerView re;
    private NavAdapter adapter;
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
                list.add(my_down);
            }else{
                DataSupport.deleteAll(My_Down.class,"name = ?",my_down.getName());
            }
        }

        if (list.size()>0){
            adapter=new NavAdapter();
            re= (RecyclerView) findViewById(R.id.nav_down_recyclerview);
            re.setAdapter(adapter);
            re.setLayoutManager(new GridLayoutManager(Nav_down.this,3));
        }else {
            Toast.makeText(Nav_down.this,"没有任何下载的文件",Toast.LENGTH_SHORT).show();
        }

    }

    private class NavAdapter extends RecyclerView.Adapter<NavAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=getLayoutInflater().inflate(R.layout.item,parent,false);
            return  new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            ViewGroup.LayoutParams lp=holder.imageView.getLayoutParams();
            lp.height=params.height;
            holder.itemView.setLayoutParams(lp);

            File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                +"/"+list.get(position).getName());
            Glide.with(Nav_down.this)
                    .load(file)
                    .placeholder(R.mipmap.down)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            public ViewHolder(View itemView) {
                super(itemView);
                imageView= (ImageView) itemView.findViewById(R.id.item);

            }
        }
    }
}
