package soexample.umeng.com.shopcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import soexample.umeng.com.shopcart.R;
import soexample.umeng.com.shopcart.bean.ShopBean;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.MyViewHolder> {
    private Context context;
    private List<ShopBean.DataBean> list;
    private BottomAdapter adapter;
    private ItemClickListener listener;


    public TopAdapter(Context context, List<ShopBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TopAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.top_layout,null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getSellerName());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.recyclerView.setLayoutManager(layoutManager);
        List<ShopBean.DataBean.ListBean> list1 = this.list.get(position).getList();
        adapter = new BottomAdapter(list1, context);
        holder.recyclerView.setAdapter(adapter);
        //第一层的接口回调
        adapter.setListener(new BottomAdapter.ItemClickListener() {
            @Override
            public void onItemClick() {
                //list集合返回到main中
                listener.onItemClick(list);
            }
        });
    }
    //声明接口
    //set方法
    public void setListener(ItemClickListener listener) {
        this.listener = listener;
    }
    //定义接口
    public interface ItemClickListener{
        //实现点击的方法，传递条目下标
        void onItemClick(List<ShopBean.DataBean> list);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        RecyclerView recyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_top);
            recyclerView = itemView.findViewById(R.id.recy_bottom);
        }
    }
}
