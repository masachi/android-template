package ga.cv3sarato.android.im_sdk;

import ga.cv3sarato.android.im_sdk.cache.Cache;
import ga.cv3sarato.android.im_sdk.im_client.ImClient;
import ga.cv3sarato.android.im_sdk.type.Env;
import ga.cv3sarato.android.im_sdk.type.Platform;

import java.util.HashMap;

public class ImService {
    private ImConfig options;
    private HashMap<String, ImClient> clientMap;

    public ImService(ImConfig config) {
        if(config.getPlatform() == null) {
            config.setPlatform(Platform.APP);
        }
        if(config.getEnv() == null){
            config.setEnv(Env.Development);
        }

        if(config.getClient_id() == null) {
            throw new Error("No Client ID");
        }
        if(config.getOid() == null) {
            throw new Error("No Org ID");
        }
        this.options = config;
    }

    public ImConfig getOptions() {
        return options;
    }

    public void setOptions(ImConfig options) {
        this.options = options;
    }

    public ImClient createImClient(String uid, Cache cache) {
        if(clientMap == null) {
            clientMap = new HashMap<>();
        }
        if(clientMap.get(uid) != null) {
            return clientMap.get(uid);
        }
        else {
            this.options.setUid(uid);
            ImClient imClient = new ImClient(this.options, cache);
            this.clientMap.put(uid, imClient);
            return imClient;
        }
    }
}
