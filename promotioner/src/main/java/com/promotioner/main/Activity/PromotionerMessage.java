package com.promotioner.main.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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
public class PromotionerMessage extends PromotionMain {

    private String filepath;//照片路径
    private static final int ADD_IDCARD_BACK = 2;  //身份证反面
    private static final int ADD_IDCARD_FRONT = 1;  //身份证正面
    private static final int ADD_BUSINESS_LICENSE = 3;  //营业执照

    @InjectView(R.id.merchants_name)EditText merchants_name;
    @InjectView(R.id.store_name)EditText store_name;
    @InjectView(R.id.bank_account)EditText bank_account;
    @InjectView(R.id.add_idcard_front)ImageView add_idcard_front;
    @InjectView(R.id.add_idcard_back)ImageView add_idcard_back;
    @InjectView(R.id.add_business_license)ImageView add_business_license;
    @InjectView(R.id.get_location)LinearLayout get_location;
    @InjectView(R.id.choose_scope)RelativeLayout choose_scope;
    @InjectView(R.id.add_finish)BootstrapButton add_finish;

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
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_IDCARD_BACK:
                    try{
                        add_idcard_back.setBackgroundDrawable(new BitmapDrawable(BitmapUtils.genRoundCorner(BitmapUtils.revitionImageSize(filepath), 20)));
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case ADD_BUSINESS_LICENSE:
                    try{
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
}

