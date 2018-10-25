package soexample.umeng.com.shopcart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.shopcart.adapter.TopAdapter;
import soexample.umeng.com.shopcart.bean.ShopBean;
import soexample.umeng.com.shopcart.di.contract.Contract;
import soexample.umeng.com.shopcart.di.presenter.IPresenterImpl;

public class MainActivity extends AppCompatActivity implements Contract.IView, View.OnClickListener {

    private Contract.IPresenter presenter;
    private RecyclerView recyclerView;
    private ImageView img_check;
    private boolean check=false;
    private List<ShopBean.DataBean> blist = new ArrayList<>();
    private TopAdapter adapter;
    private TextView text_total;
    private double total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new IPresenterImpl();
        presenter.attachView(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        text_total = findViewById(R.id.text_total);
        img_check = findViewById(R.id.check_all);
        img_check.setOnClickListener(this);
        recyclerView = findViewById(R.id.recy_top);
        recyclerView.setLayoutManager(layoutManager);
        presenter.requestShop("http://www.zhaoapi.cn/product/getCarts?uid=71");

    }
    //展示商品的方法
    @Override
    public void showShop(final List<ShopBean.DataBean> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                blist = list;
                adapter = new TopAdapter(MainActivity.this, list);
                recyclerView.setAdapter(adapter);
                //点击事件的方法
                click();
            }
        });
    }
    public void click(){
        adapter.setListener(new TopAdapter.ItemClickListener() {
            @Override
            public void onItemClick(List<ShopBean.DataBean> list) {
                //首先让总数为0
                total=0;
                //所有商品数和已经选中的商品数
                int num=0,numall=0;
                for (int i = 0; i < list.size(); i++) {
                    List<ShopBean.DataBean.ListBean> list1 = blist.get(i).getList();
                    for (int j = 0; j < list1.size(); j++) {
                        //判断是否选中
                        if (list1.get(j).isCheck()){
                            //如果选中获取价钱并求和
                            total += list1.get(j).getPrice()*list1.get(j).getNum();
                            //选中商品数
                            num++;
                        }
                        //所有商品数
                        numall++;
                    }
                }
                //判断选中的商品和所有商品数是否相同
                if (num!=numall){
                    img_check.setImageResource(R.drawable.checkno);
                    check=false;
                }else {
                    img_check.setImageResource(R.drawable.check_yes);
                    check=true;
                }
                //给text_view赋值
                text_total.setText("￥"+total);
                adapter.notifyDataSetChanged();
                //改变数量的方法
                changeNum();
            }

        });

    }
    //改变
    private void changeNum() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.deattachView(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点击全选
            case R.id.check_all:
                if (check){
                    //未选中设置的图片
                    img_check.setImageResource(R.drawable.checkno);
                    check=false;
                    //遍历商家
                    for (int i = 0; i < blist.size(); i++) {
                        List<ShopBean.DataBean.ListBean> list = blist.get(i).getList();
                        //遍历商品
                        for (int j = 0; j < list.size(); j++) {
                            list.get(j).setCheck(false);
                        }
                    }
                    total=0;
                    text_total.setText("￥0.0");
                }else {
                    //刚开始为false,先进入这个方法
                    total=0;
                    //改变选中的图片
                    img_check.setImageResource(R.drawable.check_yes);
                    //把开关改为true
                    check=true;
                    //循环遍历商家
                    for (int i = 0; i < blist.size(); i++) {
                        List<ShopBean.DataBean.ListBean> list = blist.get(i).getList();
                        //遍历商品
                        for (int j = 0; j < list.size(); j++) {
                            //判断是否为选中，如
                            // 果选中统计价格
                            list.get(j).setCheck(true);
                            total += list.get(j).getPrice()*list.get(j).getNum();
                        }
                    }
                    text_total.setText("￥"+total);
                }
                adapter.notifyDataSetChanged();
                break;
        }
    }
}
