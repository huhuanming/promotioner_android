package com.promotioner.main.ApiManager;

import com.promotioner.main.Model.LoginBackData;
import com.promotioner.main.Model.PMessageBackData;

import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by chen on 14-7-28.
 */
public class PromotionerApiInterface {
    public interface ApiManagerNoticeService {
        @POST("/uploadbaidupush")
        PMessageBackData getPMessageBackData(@Query("channel_id") String channel_id, @Query("push_id") String push_id, @Query("mac_address") String mac_address);
    }

    public interface ApiManagerLogin {
        @POST("/promotioners/login")
        LoginBackData getLoginBackData(@Query("username") String username, @Query("password") String password);
    }
}
