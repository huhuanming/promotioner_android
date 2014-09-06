package com.promotioner.main.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.promotioner.main.Model.PMessageBackData;
import com.promotioner.main.Model.PMessageData;
import com.promotioner.main.R;
import com.promotioner.main.Utils.BitmapUtils;
import com.promotioner.main.Utils.MapUtils;
import com.promotioner.main.Utils.ShareUtils;
import com.promotioner.main.Utils.ToastUtils;
import com.promotioner.main.Utils.TokenUtils.AccessToken;
import com.promotioner.main.Utils.UploadImage.Image;
import com.promotioner.main.Utils.UploadImage.PMessageUploadImage;
import com.promotioner.main.View.Button.BootstrapButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by chen on 14-8-11.
 */
public class PromotionerMessage extends PromotionMain implements AMapLocationListener {

    private String front_filepath = "";//身份证正面照片路径
    private String back_filepath = "";//身份证反面照片路径
    private String license_filepath = "";//营业执照照片路径
    private boolean have_front_img = false;
    private boolean have_back_img = false;
    private boolean have_license_img = false;
    private boolean have_location = false;
    //spinner
    private String[] radiuss={"500m","1000m","2000m","3000m"};
    private String radius = "500";
    private ArrayAdapter<String> adapter;

    //上传所需四个点
    private String northY = "";
    private String southY = "";
    private String eastX = "";
    private String westX = "";

    private String Latitude = "1";
    private String Longitude = "1";

    private static final int ADD_IDCARD_BACK = 2;  //身份证反面
    private static final int ADD_IDCARD_FRONT = 1;  //身份证正面
    private static final int ADD_BUSINESS_LICENSE = 3;  //营业执照
    private LocationManagerProxy mLocationManagerProxy;

    @InjectView(R.id.merchants_name)EditText merchants_name;
    @InjectView(R.id.store_name)EditText store_name;
    @InjectView(R.id.bank_account)EditText bank_account;
    @InjectView(R.id.phonenumber)EditText phonenumber;
    @InjectView(R.id.add_idcard_front)ImageView add_idcard_front;
    @InjectView(R.id.add_idcard_back)ImageView add_idcard_back;
    @InjectView(R.id.add_business_license)ImageView add_business_license;
    @InjectView(R.id.get_location)LinearLayout get_location;
    @InjectView(R.id.add_finish)BootstrapButton add_finish;
    @InjectView(R.id.front_page)TextView front_page;
    @InjectView(R.id.back_page)TextView back_page;
    @InjectView(R.id.location)TextView location;
    @InjectView(R.id.spinner_radius)Spinner spinner;
    @InjectView(R.id.add_finish_progressbar)SmoothProgressBar progressbar;


    @OnClick(R.id.add_idcard_front)void addidcardfront(){
        front_filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(front_filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_IDCARD_FRONT);
    }
    @OnClick(R.id.add_idcard_back)void addidcardback(){
        back_filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(back_filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_IDCARD_BACK);
    }

    @OnClick(R.id.add_business_license)void addbusinesslicense(){
        license_filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(license_filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_BUSINESS_LICENSE);
    }

    @OnClick(R.id.get_location)void getlocation()
    {
        location.setText(R.string.startlocation);
        have_location = false;
        init();
    }

    @OnClick(R.id.add_finish)void addfinish()
    {
        if(merchants_name.getText().toString().trim().equals(""))
        {
            ToastUtils.setToast(PromotionerMessage.this,"请填写商家名称!");
        }else if(store_name.getText().toString().trim().equals(""))
        {
            ToastUtils.setToast(PromotionerMessage.this,"请填写店铺名称!");
        }else if(!have_front_img)
        {
            ToastUtils.setToast(PromotionerMessage.this,"请添加身份证正面照片!");
        }else if(!have_back_img)
        {
            ToastUtils.setToast(PromotionerMessage.this,"请添加身份证反面照片!");
        }else if(!have_license_img)
        {
            ToastUtils.setToast(PromotionerMessage.this,"请添加营业执照照片!");
        }else if(bank_account.getText().toString().trim().equals(""))
        {
            ToastUtils.setToast(PromotionerMessage.this,"请填写银行账号!");
        }else if(!have_location)
        {
            ToastUtils.setToast(PromotionerMessage.this,"请定出自己的位置方便上传!");
        }else if(phonenumber.getText().toString().trim().equals(""))
        {
            ToastUtils.setToast(PromotionerMessage.this,"请填写手机号码!");
        }
        else{
            progressbar.setVisibility(View.VISIBLE);
            AccessToken accessToken = new AccessToken(ShareUtils.getToken(PromotionerMessage.this),ShareUtils.getKey(PromotionerMessage.this));
            PMessageData pMessageData = new PMessageData();
            pMessageData.setAccess_token(accessToken.accessToken());
            pMessageData.setSupervisor_name(merchants_name.getText().toString());
            pMessageData.setRestaurant_name(store_name.getText().toString());
            pMessageData.setBack_account(bank_account.getText().toString());
            pMessageData.setAddress(location.getText().toString());
            pMessageData.setRadius(radius);
            pMessageData.setPhone_number(phonenumber.getText().toString());
            pMessageData.setCoordinate_x1(westX);
            pMessageData.setCoordinate_x2(eastX);
            pMessageData.setCoordinate_y1(northY);
            pMessageData.setCoordinate_y2(southY);
            pMessageData.setLatitude(Latitude);
            pMessageData.setLongitude(Longitude);

            ArrayList<Image> arrayList = new ArrayList<Image>();
            try{
                arrayList.add(new Image(front_filepath,"file"));
                arrayList.add(new Image(back_filepath,"file"));
                arrayList.add(new Image(license_filepath,"file"));
            }catch (Exception e){

            }
            new PMessageUploadImage(pMessageData,arrayList,new PMessageUploadImage.PMmessageImageBackInterface() {
                @Override
                public void onSuccess(PMessageBackData pMessageBackData) {
                    progressbar.setVisibility(View.GONE);
                    ToastUtils.setToast(PromotionerMessage.this,"创建成功!");
                    PromotionerMessage.this.finish();
                }

                @Override
                public void onFailth(int code) {
                    progressbar.setVisibility(View.GONE);
                    switch (code)
                    {
                        case 401:
                            ToastUtils.setToast(PromotionerMessage.this,"信息已经过期，请重新登录!");
                            ShareUtils.deleteTokenKey(PromotionerMessage.this);
                            PromotionerMessage.this.finish();
                            setResult(401);
                            break;
                        case 404:
                            ToastUtils.setToast(PromotionerMessage.this,"信息已经过期，请重新登录!");
                            ShareUtils.deleteTokenKey(PromotionerMessage.this);
                            PromotionerMessage.this.finish();
                            setResult(401);
                            break;
                        case 501:
                            ToastUtils.setToast(PromotionerMessage.this,"上传出错，请再重新上传一次!");
                            break;
                        default:
                            ToastUtils.setToast(PromotionerMessage.this,"网络错误，请再重新上传一次!");
                            break;
                    }
                }
            });

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_promotioner);
        ButterKnife.inject(this);//为监听所用
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);


        //将可选内容与ArrayAdapter连接起来
        adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_print,radiuss);


        //设置下拉列表的风格
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_print);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i)
                {

                    case 0:
                        radius = "500";
                        setLocationLength();
                        break;
                    case 1:
                        radius = "1000";
                        setLocationLength();
                        break;
                    case 2:
                        radius = "2000";
                        setLocationLength();
                        break;
                    case 3:
                        radius = "3000";
                        setLocationLength();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case ADD_IDCARD_FRONT:
                    try{
                        have_front_img = true;
                        add_idcard_front.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(front_filepath), 20)));
                        front_page.setVisibility(View.GONE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_IDCARD_BACK:
                    try{
                        have_back_img = true;
                        add_idcard_back.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(back_filepath), 20)));
                        back_page.setVisibility(View.GONE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_BUSINESS_LICENSE:
                    try{
                        have_license_img = true;
                        //调整控件长宽
                        add_business_license.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        add_business_license.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(license_filepath), 20)));

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:
                PromotionerMessage.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //高德地图信息
    @Override
    public void onProviderEnabled(String arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onLocationChanged(Location arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            Latitude = amapLocation.getLatitude()+"";
            Longitude = amapLocation.getLongitude()+"";
            location.setText(amapLocation.getAddress());

            //移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();

            have_location = true;
            setLocationLength();
        }

    }

    private void setLocationLength()
    {
        northY = MapUtils.northLon(Latitude, Longitude, radius);
        southY = MapUtils.southLon(Latitude,Longitude,radius);
        eastX = MapUtils.eastLat(Latitude,Longitude,radius);
        westX = MapUtils.westLat(Latitude,Longitude,radius);
    }

    /**
     * 初始化定位
     */
    private void init() {

        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        //在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60*1000, 15, this);


    }

    @Override
    protected void onPause() {
        super.onPause();
        //移除定位请求
        mLocationManagerProxy.removeUpdates(this);
        // 销毁定位
        mLocationManagerProxy.destroy();
    }
}

