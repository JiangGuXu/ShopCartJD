package soexample.umeng.com.shopcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import soexample.umeng.com.shopcart.R;
import soexample.umeng.com.shopcart.bean.ShopBean;

public class BottomAdapter extends RecyclerView.Adapter<BottomAdapter.MyViewHolder> {
    private List<ShopBean.DataBean.ListBean> list;
    private Context context;
    private ItemClickListener listener;


    public BottomAdapter(List<ShopBean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BottomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context,R.layout.bottom_layout,null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final BottomAdapter.MyViewHolder holder, final int position) {
        String images = list.get(position).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.img_shop);
        holder.text_title.setText(list.get(position).getTitle());
        holder.text_price.setText("￥"+list.get(position).getPrice());
        holder.editText.setText(list.get(position).getNum()+"");
        if (list.get(position).isCheck()){
            holder.img_check.setImageResource(R.drawable.check_yes);
        }else {
            holder.img_check.setImageResource(R.drawable.checkno);
        }
        //点击选中图片的监听
        holder.img_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isCheck()){
                    list.get(position).setCheck(false);
                }else {
                    list.get(position).setCheck(true);
                }
                listener.onItemClick();
                notifyDataSetChanged();
            }
        });
        //点击加号
        holder.img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = list.get(position).getNum();
                num++;
                list.get(position).setNum(num);
                listener.onItemClick();
            }
        });
        //点击减号
        holder.img_subtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = list.get(position).getNum();
                if (num==1){
                    return;
                }
                num--;
                list.get(position).setNum(num);
                listener.onItemClick();
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
        void onItemClick();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_check;
        ImageView img_shop;
        TextView text_title;
        TextView text_price;
        ImageView img_add;
        ImageView img_subtract;
        EditText editText;
        public MyViewHolder(View itemView) {
            super(itemView);
            img_shop = itemView.findViewById(R.id.img_icon);
            text_title = itemView.findViewById(R.id.text_titlt);
            text_price = itemView.findViewById(R.id.text_price);
            img_check = itemView.findViewById(R.id.img_check);
            img_add = itemView.findViewById(R.id.img_add);
            img_subtract = itemView.findViewById(R.id.img_subtract);
            editText = itemView.findViewById(R.id.edit_text);
        }
    }
}
