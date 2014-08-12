package com.promotioner.main.Activity;

import android.content.Intent;
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
    }
}
