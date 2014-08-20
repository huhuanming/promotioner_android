package com.promotioner.main.Utils.UploadImage;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by chen on 14-8-13.
 */
public class PMessageUploadImage {

    private ArrayList<String> photosHash = new ArrayList<String>();
    private ArrayList<Image> imageArray;
    private int num = 0;
    public PMessageUploadImage(ArrayList<Image> imageArray){
        this.imageArray = imageArray;
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
                filepath = jsonObject.getString("hash");
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
                    JSONArray photosJson = new JSONArray(photosHash);
                    //图片返回信息加入到param上传，在这里调用上传信息方法
                }
            }
        }
    }
}
