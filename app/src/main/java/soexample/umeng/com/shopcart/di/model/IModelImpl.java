package soexample.umeng.com.shopcart.di.model;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import soexample.umeng.com.shopcart.bean.ShopBean;
import soexample.umeng.com.shopcart.di.contract.Contract;

public class IModelImpl implements Contract.IModel {
    @Override
    public void requestShop(String url, final CallBack callBack) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                Gson gson = new Gson();
                ShopBean shopBean = gson.fromJson(response.body().string(), ShopBean.class);
                List<ShopBean.DataBean> data = shopBean.getData();
                callBack.responseShop(data);
            }
        });
    }
}
