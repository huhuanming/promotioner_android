package com.promotioner.main.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.promotioner.main.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PromotionerAdd extends PromotionMain {
    @OnClick(R.id.promotioner_add_button)void addbuttonOnclick(){
        Intent intent = new Intent();
        intent.setClass(PromotionerAdd.this,PromotionerMessage.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_promotioner);
        ButterKnife.inject(this);//为监听所用
        SharedPreferences TokenShared = getSharedPreferences("usermessage", 0);
        String tokenString = TokenShared.getString("token", "");
        Intent intent = new Intent();
        if(tokenString.equals(""))
        {
            intent.setClass(PromotionerAdd.this,PromotionerLogin.class);
            startActivity(intent);
            finish();
        }
    }
}
