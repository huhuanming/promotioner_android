package com.promotioner.main.Utils.UploadImage;

import android.os.AsyncTask;
import android.util.Log;

import com.promotioner.main.ApiManager.PromotionerApiManager;
import com.promotioner.main.Model.PMessageBackData;
import com.promotioner.main.Model.PMessageData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-13.
 */
public class PMessageUploadImage {

    private ArrayList<String> photosHash = new ArrayList<String>();
    private ArrayList<Image> imageArray;
    private int num = 0;
    private PMmessageImageBackInterface pMmessageImageBackInterface;
    private PMessageData pMessageData;

    public static abstract interface PMmessageImageBackInterface{
        public abstract void onSuccess(PMessageBackData pMessageBackData);
        public abstract void onFailth(int code);
    }


    public PMessageUploadImage(PMessageData pMessageData, ArrayList<Image> imageArray,
                               PMmessageImageBackInterface pMmessageImageBackInterface){
        this.imageArray = imageArray;
        this.pMmessageImageBackInterface = pMmessageImageBackInterface;
        this.pMessageData = pMessageData;
        num = 0;
        new ImageArrayUploadTask().execute(imageArray.get(0));
    }
    public class ImageArrayUploadTask extends AsyncTask<Image, Float, String> {
        private final static String qiniu_url = "http://up.qiniu.com/";

        private String filepath = "";
        private Image image = null;

        @Override
        protected String doInBackground(Image... params) {
            image  = params[0];
            UploadImage uploadImage = new UploadImage();

            String respose = uploadImage.post(qiniu_url,image);
            try {
                JSONObject jsonObject = new JSONObject(respose);
                Log.e("aaaaaa",jsonObject.toString());
                filepath = jsonObject.getString("key");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            JSONArray photosJson = new JSONArray(photosHash);
            return filepath;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onProgressUpdate(Float... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        //doinbackground方法调用完成后执行此方法
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if (result != null && !result.equals("")) {
                photosHash.add(result);
                num++;
                if(imageArray.size() > num)
                {
                    new ImageArrayUploadTask().execute(imageArray.get(num));
                }
                else
                {
                    PromotionerApiManager.getPMessageBackData(pMessageData.getAccess_token(),
                            pMessageData.getRestaurant_name(),pMessageData.getSupervisor_name(),
                            pMessageData.getBack_account(),pMessageData.getPhone_number(),
                            photosHash.get(2),photosHash.get(0),photosHash.get(1),
                            pMessageData.getAddress(),pMessageData.getRadius(),
                            pMessageData.getLongitude(),pMessageData.getLatitude(),
                            pMessageData.getCoordinate_x1(),pMessageData.getCoordinate_x2(),
                            pMessageData.getCoordinate_y1(),pMessageData.getCoordinate_y2())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<PMessageBackData>() {
                                @Override
                                public void call(PMessageBackData pMessageBackData) {
                                    pMmessageImageBackInterface.onSuccess(pMessageBackData);
                                }
                            },new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    retrofit.RetrofitError e = (retrofit.RetrofitError)throwable;
                                    pMmessageImageBackInterface.onFailth(e.getResponse().getStatus());
                                }
                            });
                }
            }
        }
    }
}
