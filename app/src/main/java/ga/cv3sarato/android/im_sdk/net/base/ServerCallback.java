package ga.cv3sarato.android.im_sdk.net.base;

import okhttp3.ResponseBody;
import retrofit2.Call;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ServerCallback<T> implements retrofit2.Callback<Response> {
//    private Callback callback;
        private BiFunction<String, Response, Object> callback;

//    public ServerCallback(Callback callback) {
//        this.callback = callback;
//    }

    public ServerCallback(BiFunction<String, Response, Object> callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if(response.isSuccessful() && response != null) {
            try {
                if(response.body().getCode() == 200) {
//                    callback.onResult(null, response.body());
                    callback.apply(null, response.body());
                }
                else{
//                    callback.onResult(response.body().getMessage(), null);
                    callback.apply(response.body().getMessage(), null);
                }
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        else {
//            callback.onResult(response.message(), null);
            callback.apply(response.message(), null);
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable throwable) {
//        callback.onResult(throwable.getMessage(), null);
        callback.apply(throwable.getMessage(), null);
    }

//    public static interface Callback {
//        void onResult(String message, Response response);
//    }
}
