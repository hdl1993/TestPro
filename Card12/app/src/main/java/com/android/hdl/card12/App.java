package com.android.hdl.card12;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by HDL on 2017/2/13.
 */

public class App extends Application {

    private static UMShareAPI mShareAPI;
    @Override
    public void onCreate() {
        super.onCreate();
        mShareAPI = UMShareAPI.get(this);
        Config.DEBUG = true;
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }

    public static  UMShareAPI getUMShareAPI(){

        return mShareAPI;
    }
}
