package zero.okhttp_piccaso_recyclerview.Like;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import zero.okhttp_piccaso_recyclerview.Detail_View;
import zero.okhttp_piccaso_recyclerview.MainActivity;
import zero.okhttp_piccaso_recyclerview.MyIntentService;
import zero.okhttp_piccaso_recyclerview.R;
import zero.okhttp_piccaso_recyclerview.database.My_Down;
import zero.okhttp_piccaso_recyclerview.database.My_Like;

public class Nav_Like extends AppCompatActivity {
    private RecyclerView recyclerView;
    private static final String TAG = "Nav_Like";
    private Adapter adapter;
    private List<My_Like> list=new ArrayList<>();

    public static void newInstance(Context context){
        Intent intent=new Intent(context,Nav_Like.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_down);
        LitePal.getDatabase();
        recyclerView= (RecyclerView) findViewById(R.id.nav_down_recyclerview);
        list= DataSupport.where("love = ?","1").find(My_Like.class);
        Log.e(TAG,list.toString());
        if (list.size()>0){
            adapter=new Adapter();
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        }else{
            Toast.makeText(this,"没有任何收藏",Toast.LENGTH_SHORT).show();
        }
    }

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v=getLayoutInflater().inflate(R.layout.item,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.button.setVisibility(View.VISIBLE);
            holder.likeButton.setVisibility(View.VISIBLE);
            holder.likeButton.setImageResource(R.mipmap.like);
            Glide.with(Nav_Like.this)
                    .load(list.get(position).getUrl())
                    .placeholder(R.mipmap.down)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView);


            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String photoUrl=list.get(position).getUrl();
                    Intent i= Detail_View.newInstants(Nav_Like.this,photoUrl,"MainActivity");
                    startActivity(i);
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String path=list.get(position).getUrl();
                    String name=list.get(position).getName();
                    File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+name);
                    if (file.exists()){
                        Toast.makeText(Nav_Like.this,"文件已存在",Toast.LENGTH_SHORT).show();
                    }else {
                        My_Down my_down = new My_Down();
                        my_down.setName(list.get(position).getName());
                        my_down.setUrl(list.get(position).getUrl());
                        Log.e(TAG, list.get(position).getName());
                        Log.e(TAG, list.get(position).getUrl());
                        my_down.save();
                        MyIntentService.newInstance(Nav_Like.this, path, name);
                    }
                }
            });
            holder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //like是有收藏
                    if (holder.like) {
                        holder.likeButton.setImageResource(R.mipmap.unlike);
                        holder.like=false;
                        My_Like my_like=new My_Like();
                        my_like.setToDefault("love");
                        my_like.updateAll("name = ? ",list.get(position).getName());
                    }
                    //没有收藏
                    else {
                        holder.likeButton.setImageResource(R.mipmap.like);
                        holder.like=true;
                        List<My_Like> likes=DataSupport.where("name = ?",list.get(position).getName()).find(My_Like.class);
                        //在数据库里面没有这个。
                        if (likes.size()==0){
                            My_Like my_like = new My_Like();
                            my_like.setName(list.get(position).getName());
                            my_like.setUrl(list.get(position).getUrl());
                            my_like.setLove(true);
                            my_like.save();
                        }else if(likes.size()==1) {
                            //数据库里有
                            My_Like my_like=new My_Like();
                            my_like.setLove(true);
                            my_like.updateAll("name = ? ",list.get(position).getName());
                        }else {
                            Log.e(TAG, "出现意外错误" );
                        }
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            CardView cardView;
            ImageView imageView;
            Button button;
            ImageButton likeButton;
            Boolean like=true;
            public ViewHolder(View itemView) {
                super(itemView);
                cardView= (CardView) itemView;
                imageView= (ImageView) itemView.findViewById(R.id.item);
                // a= (TextView) itemView.findViewById(R.id.tv);
                button= (Button) itemView.findViewById(R.id.down);
                likeButton= (ImageButton) itemView.findViewById(R.id.like);
            }
        }
    }
}
