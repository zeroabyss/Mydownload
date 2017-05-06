package zero.okhttp_piccaso_recyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private List<TestBean.DataBean.WallpaperListInfoBean> list=new ArrayList<>();
    private List<Integer> heights=new ArrayList<>();
    private RecyclerView re;
    private Myadapter adapter;
    private static  String URL="http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=0";
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        startTask();
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                OkHttpUtils.getInstance().getBeanOfOk(MainActivity.this, URL, TestBean.class
                        , new OkHttpUtils.CallBack<TestBean>() {
                            @Override
                            public void getData(TestBean testBean) {
                                if (testBean.getData()!=null){
                                    list.clear();
                                    list.addAll(testBean.getData().getWallpaperListInfo());
                                    Toast.makeText(MainActivity.this,"刷新成功",Toast.LENGTH_SHORT).show();
                                    adapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                }else {
                                    Toast.makeText(MainActivity.this,"刷新失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void initView() {
        re = (RecyclerView) findViewById(R.id.re);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        re.setLayoutManager(new GridLayoutManager(this,3));
//        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setAdapter(){
        adapter=new Myadapter();
        re.setAdapter(adapter);
    }

    private void initHeights(){
        Random random = new Random();

        for (int i = 0; i < list.size(); i++) {

            //集合中存储每个回调图片对应的随机高度
            heights.add(random.nextInt(200)+200);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                Toast.makeText(MainActivity.this,"backup",Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(MainActivity.this,"delete",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:

        }
        return true;
    }*/

    private void startTask(){
        OkHttpUtils.getInstance().getBeanOfOk(this, URL, TestBean.class, new OkHttpUtils.CallBack<TestBean>() {
            @Override
            public void getData(TestBean testBean) {
                Log.e("sda",testBean.toString());
                if (testBean.getData()!=null){
                    list.addAll(testBean.getData().getWallpaperListInfo());
                    Log.e(TAG, list.get(0).getID()+"" );
                    initHeights();
                    setAdapter();
                }
            }
        });
    }
    private class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(MainActivity.this).inflate(R.layout.item,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
//            RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
//            params.height=heights.get(position);
//            holder.itemView.setLayoutParams(params);
           // holder.a.setText(list.get(position).getPicName());
           Glide.with(MainActivity.this)
                    .load(list.get(position).getWallPaperMiddle())
                    .placeholder(R.mipmap.down)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imageView);
//            Glide.with(MainActivity.this)
//                    .load(list.get(position).getWallPaperMiddle())
//                    .thumbnail(Glide.with(MainActivity.this).load(R.mipmap.timg))
//                    .fitCenter()
//                    .crossFade()
//                    .into(holder.imageView);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String photoUrl=list.get(position).getWallPaperDownloadPath();
                    Intent i=Detail_View.newInstants(MainActivity.this,photoUrl);
                    startActivity(i);
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String path=list.get(position).getWallPaperDownloadPath();
                    String name=list.get(position).getPicName();
                    MyIntentService.newInstance(MainActivity.this,path,name);
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

          class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView imageView;
            TextView a;
            Button button;

            public ViewHolder(View itemView) {
                super(itemView);
                cardView= (CardView) itemView;
                imageView= (ImageView) itemView.findViewById(R.id.item);
               // a= (TextView) itemView.findViewById(R.id.tv);
                button= (Button) itemView.findViewById(R.id.down);
            }
        }
    }

}

