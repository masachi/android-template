package ga.cv3sarato.android.net.observer;

import android.graphics.Color;
import android.util.Log;

import ga.cv3sarato.android.MainApplication;
import ga.cv3sarato.android.net.ServerException;
import ga.cv3sarato.android.utils.annotation.RequiredFieldException;
import ga.cv3sarato.android.utils.toast.ToastUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public abstract class HylaaObserver<T> extends DisposableObserver<T> {


    @Override
    public void onError(Throwable e) {
        if (e instanceof ServerException) {
            Log.e("Server Error", e.toString());
            MainApplication.getInstance().Toast
                    .setText(e.getMessage() != null ? e.getMessage() : "")
                    .setDuration(10)
                    .show();
            e.printStackTrace();
        }
        if (e instanceof RequiredFieldException) {
            MainApplication.getInstance().Toast
                    .setText(MainApplication.getInstance().getString(((RequiredFieldException) e).getFieldName()) + (((RequiredFieldException) e).getErrorMessage() != null ? ((RequiredFieldException) e).getErrorMessage() : " required"))
                    .setDuration(5)
                    .setTintColor(Color.RED)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onComplete() {

    }

}
