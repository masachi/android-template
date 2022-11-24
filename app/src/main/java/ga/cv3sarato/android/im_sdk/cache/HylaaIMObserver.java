package ga.cv3sarato.android.im_sdk.cache;

import io.reactivex.observers.DisposableObserver;

public abstract class HylaaIMObserver<Cb> extends DisposableObserver<Cb> {

    @Override
    public void onError(Throwable e) {
        System.err.println(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
