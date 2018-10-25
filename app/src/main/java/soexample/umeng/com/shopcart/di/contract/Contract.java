package soexample.umeng.com.shopcart.di.contract;

import java.util.List;

import soexample.umeng.com.shopcart.bean.ShopBean;

public interface Contract {
    public interface IView{
        void showShop(List<ShopBean.DataBean> list);
    }
    public interface IPresenter<IView>{
        void attachView(IView iView);
        void deattachView(IView iView);
        void requestShop(String url);
    }
    public interface IModel{
        void requestShop(String url,CallBack callBack);
        interface CallBack{
            void responseShop(List<ShopBean.DataBean> list);
        }
    }
}
