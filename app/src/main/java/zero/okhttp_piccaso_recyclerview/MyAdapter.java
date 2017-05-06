package zero.okhttp_piccaso_recyclerview;

/**
 * Created by Aiy on 2017/4/19.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/8/24 0024.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //声明两个集合用于接受构造方法传来的参数在本地使用
    private List<TestBean.DataBean.WallpaperListInfoBean> list;

    private List<Integer> heights;

    //声明上下文引用，用于加载布局文件
    private Context context;

    //用构造方法传入需要的参数，
    public MyAdapter(Context context, List<TestBean.DataBean.WallpaperListInfoBean> list, List<Integer> heights) {
        this.context = context;
        this.list = list;
        this.heights = heights;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //返回MyViewHolder对象，通过构造方法传入加载布局文件得到的view对象
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //通过itemview得到每个图片的pararms对象
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();

        //将高度修改为传入的随机高度
        params.height = heights.get(position);

        //设置修改参数
        holder.itemView.setLayoutParams(params);


//        holder.iv.setImageResource(R.mipmap.ic_launcher);

        //用过Picasso框架对图片处理并显示到iv上
        //用with()方法初始化，,
        Picasso.with(context)
                //load()下载图片
                .load(list.get(position).getWallPaperMiddle())

                //下载中显示的图片
                .placeholder(R.mipmap.ic_launcher)

                //下载失败显示的图片
                .error(R.mipmap.ic_launcher)

                //init()显示到指定控件
                .into(holder.iv);

    }

    @Override
    public int getItemCount() {

        //返回数据源大小
        return list.size();
    }

    //自定义MyViewHolder类用于复用
    class MyViewHolder extends RecyclerView.ViewHolder {
        //声明imageview对象
        private ImageView iv;

        //构造方法中初始化imageview对象
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item);
        }
    }
}
