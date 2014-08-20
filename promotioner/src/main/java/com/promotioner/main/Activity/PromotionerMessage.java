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

    private String front_filepath = "";//身份证正面照片路径
    private String back_filepath = "";//身份证反面照片路径
    private String license_filepath = "";//营业执照照片路径
    private boolean have_front_img = false;
    private boolean have_back_img = false;
    private boolean have_license_img = false;
    //spinner
    private String[] radius={"500m","1000m","2000m","3000m"};
    private ArrayAdapter<String> adapter;

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
    @InjectView(R.id.add_finish)BootstrapButton add_finish;
    @InjectView(R.id.front_page)TextView front_page;
    @InjectView(R.id.back_page)TextView back_page;
    @InjectView(R.id.location)TextView location;
    @InjectView(R.id.spinner_radius)Spinner spinner;


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
        init();
    }

    @OnClick(R.id.add_finish)void addfinish()
    {
//        ArrayList<Image> arrayList = new ArrayList<Image>();
//        try{
//            arrayList.add(new Image(front_filepath,"file"));
//            arrayList.add(new Image(back_filepath,"file"));
//            arrayList.add(new Image(license_filepath,"file"));
//        }catch (Exception e){
//
//        }
//        PMessageUploadImage pMessageUploadImage = new PMessageUploadImage(arrayList);
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
        adapter = new ArrayAdapter<String>(this,R.layout.spinner_item_print,radius);


        //设置下拉列表的风格
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_print);

        //将adapter 添加到spinner中
        spinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("ssss",i+"");
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
            String Latitude = amapLocation.getLatitude()+"";
            String Longitude = amapLocation.getLongitude()+"";
            location.setText(amapLocation.getAddress());
            //移除定位请求
            mLocationManagerProxy.removeUpdates(this);
            // 销毁定位
            mLocationManagerProxy.destroy();
        }

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

