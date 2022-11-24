package ga.cv3sarato.android.utils.cache;

import ga.cv3sarato.android.im_sdk.cache.Cb;

import io.realm.Realm;

public interface Callback {
    void execute(Realm realm, Cb cb);
}
