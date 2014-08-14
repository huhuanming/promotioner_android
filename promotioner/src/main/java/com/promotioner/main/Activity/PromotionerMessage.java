package com.promotioner.main.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.promotioner.main.R;
import com.promotioner.main.Utils.BitmapUtils;
import com.promotioner.main.View.Button.BootstrapButton;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chen on 14-8-11.
 */
public class PromotionerMessage extends PromotionMain implements AMapLocationListener {

    private String filepath;//照片路径
    private static final int ADD_IDCARD_BACK = 2;  //身份证反面
    private static final int ADD_IDCARD_FRONT = 1;  //身份证正面
    private static final int ADD_BUSINESS_LICENSE = 3;  //营业执照
    private LocationManagerProxy mLocationManagerProxy;

    @InjectView(R.id.merchants_name)EditText merchants_name;
    @InjectView(R.id.store_name)EditText store_name;
    @InjectView(R.id.bank_account)EditText bank_account;
    @InjectView(R.id.add_idcard_front)ImageView add_idcard_front;
    @InjectView(R.id.add_idcard_back)ImageView add_idcard_back;
    @InjectView(R.id.add_business_license)ImageView add_business_license;
    @InjectView(R.id.get_location)LinearLayout get_location;
    @InjectView(R.id.choose_scope)RelativeLayout choose_scope;
    @InjectView(R.id.add_finish)BootstrapButton add_finish;
    @InjectView(R.id.front_page)TextView front_page;
    @InjectView(R.id.back_page)TextView back_page;
    @InjectView(R.id.location)TextView location;

    @OnClick(R.id.add_idcard_front)void addidcardfront(){
        filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_IDCARD_FRONT);
    }
    @OnClick(R.id.add_idcard_back)void addidcardback(){
        filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_IDCARD_BACK);
    }

    @OnClick(R.id.add_business_license)void addbusinesslicense(){
        filepath = "/mnt/sdcard/DCIM/Camera/"
                + System.currentTimeMillis() + ".png";
        final File file = new File(filepath);
        final Uri imageuri = Uri.fromFile(file);
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(intent, ADD_BUSINESS_LICENSE);
    }

    @OnClick(R.id.get_location)void getlocation()
    {
        location.setText(R.string.startlocation);
        init();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_promotion);
        ButterKnife.inject(this);//为监听所用
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


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
                        add_idcard_front.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(filepath), 20)));
                        front_page.setVisibility(View.GONE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_IDCARD_BACK:
                    try{
                        add_idcard_back.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(filepath), 20)));
                        back_page.setVisibility(View.GONE);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_BUSINESS_LICENSE:
                    try{
                        //调整控件长宽
                        add_business_license.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                        add_business_license.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(filepath), 20)));

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
        Log.e("ssssssss","ddd");
        if (amapLocation!=null&&amapLocation.getAMapException().getErrorCode() == 0) {
            // 定位成功回调信息，设置相关消息
            String Latitude = amapLocation.getLatitude()+"";
            String Longitude = amapLocation.getLongitude()+"";
            location.setText(amapLocation.getAddress());
//            //移除定位请求
//            mLocationManagerProxy.removeUpdates(this);
//            // 销毁定位
//            mLocationManagerProxy.destroy();
        }

    }

    /**
     * 初始化定位
     */
    private void init() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(this);
        mLocationManagerProxy.setGpsEnable(false);
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

