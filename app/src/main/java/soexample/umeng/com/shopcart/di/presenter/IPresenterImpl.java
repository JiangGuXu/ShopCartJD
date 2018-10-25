package soexample.umeng.com.shopcart.di.presenter;

import java.lang.ref.WeakReference;
import java.util.List;

import soexample.umeng.com.shopcart.bean.ShopBean;
import soexample.umeng.com.shopcart.di.contract.Contract;
import soexample.umeng.com.shopcart.di.model.IModelImpl;

public class IPresenterImpl implements Contract.IPresenter<Contract.IView> {
    private Contract.IView iView;
    private Contract.IModel iModel;
    private WeakReference<Contract.IView> iViewWeakReference;
    private WeakReference<Contract.IModel> iModelWeakReference;
    @Override
    public void attachView(Contract.IView iView) {
        this.iView = iView;
        iModel = new IModelImpl();
        iViewWeakReference = new WeakReference<>(iView);
        iModelWeakReference = new WeakReference<>(iModel);
    }

    @Override
    public void deattachView(Contract.IView iView) {
        iViewWeakReference.clear();;
        iModelWeakReference.clear();
    }

    @Override
    public void requestShop(String url) {
        iModel.requestShop(url, new Contract.IModel.CallBack() {
            @Override
            public void responseShop(List<ShopBean.DataBean> list) {
                iView.showShop(list);
            }
        });
    }
}
