package zero.okhttp_piccaso_recyclerview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.Random;

import zero.okhttp_piccaso_recyclerview.Like.Nav_Like;
import zero.okhttp_piccaso_recyclerview.Nav.Nav_down;
import zero.okhttp_piccaso_recyclerview.database.My_Down;
import zero.okhttp_piccaso_recyclerview.database.My_Like;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private List<TestBean.DataBean.WallpaperListInfoBean> list=new ArrayList<>();
    private List<Integer> heights=new ArrayList<>();
    private RecyclerView re;
    private Myadapter adapter;
    private NavigationView navigationView;
    private static  String URL="http://bz.budejie.com/?typeid=2&ver=3.4.3&no_cry=1&client=android&c=wallPaper&a=random&bigid=0";
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int REQUESTCODE_PERMISSION=1;

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
       /* if (navigationView.getVisibility()==View.VISIBLE){*/
            drawerLayout.closeDrawers();
        }else
            super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUESTCODE_PERMISSION:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED
                        &&grantResults.length>0){

                }else{
                    Toast.makeText(this,"申请权限失败",Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUESTCODE_PERMISSION);
        }

        initView();
       // drawerLayout.setFocusableInTouchMode(false);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_draweropen);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        navigationView= (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_down:
                        Nav_down.newInstance(MainActivity.this);
                        break;
                    case R.id.nav_love:
                        Nav_Like.newInstance(MainActivity.this, new OnItemListen() {
                            @Override
                            public void change(int position, boolean love,String name) {
                                if (name.equals(list.get(position).getPicName())) {
                                    Log.e(TAG, position+"" );
                                    Log.e(TAG, name );
                                    list.get(position).setLove(love);
                                    adapter.notifyItemChanged(position);
                                }
                            }
                        });
                        break;
                    default:

                }
                return true;
            }
        });

        LitePal.getDatabase();
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
        re.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        toolbar= (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:

        }
        return true;
    }

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
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.like=list.get(position).getLove();
            holder.button.setVisibility(View.VISIBLE);
            holder.likeButton.setVisibility(View.VISIBLE);
            if (holder.like){
                holder.likeButton.setImageResource(R.mipmap.like);
            }else{
                holder.likeButton.setImageResource(R.mipmap.unlike);
            }
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
                    Intent i=Detail_View.newInstants(MainActivity.this,photoUrl,"MainActivity");
                    startActivity(i);
                }
            });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String path=list.get(position).getWallPaperDownloadPath();
                    String name=list.get(position).getPicName();
                    File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+name);
                    if (file.exists()){
                        Toast.makeText(MainActivity.this,"文件已存在",Toast.LENGTH_SHORT).show();
                    }else {
                        My_Down my_down = new My_Down();
                        my_down.setName(list.get(position).getPicName());
                        my_down.setUrl(list.get(position).getWallPaperDownloadPath());
                        Log.e(TAG, list.get(position).getPicName());
                        Log.e(TAG, list.get(position).getWallPaperDownloadPath());
                        my_down.save();
                        MyIntentService.newInstance(MainActivity.this, path, name);
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
                        my_like.setListPosition(position);
                        my_like.updateAll("name = ? ",list.get(position).getPicName());
                        Log.e(TAG, position + "   " + list.get(position).getPicName() );
                    }
                    //没有收藏
                    else {
                        holder.likeButton.setImageResource(R.mipmap.like);
                        holder.like=true;
                        List<My_Like> likes=DataSupport.where("name = ?",list.get(position).getPicName()).find(My_Like.class);
                        //在数据库里面没有这个。
                        if (likes.size()==0){
                            My_Like my_like = new My_Like();
                            my_like.setName(list.get(position).getPicName());
                            my_like.setUrl(list.get(position).getWallPaperDownloadPath());
                            my_like.setListPosition(position);
                            my_like.setLove(true);
                            my_like.save();
                            Log.e(TAG, position + "   " + list.get(position).getPicName() );
                        }else if(likes.size()==1) {
                            //数据库里有
                            My_Like my_like=new My_Like();
                            my_like.setListPosition(position);
                            my_like.setLove(true);
                            my_like.updateAll("name = ? ",list.get(position).getPicName());
                            Log.e(TAG, position + "   " + list.get(position).getPicName()  );
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

          class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            ImageView imageView;
            Button button;
            ImageButton likeButton;
              Boolean like;
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

