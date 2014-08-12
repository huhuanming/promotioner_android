package com.promotioner.main.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.promotioner.main.R;
import com.promotioner.main.View.Button.BootstrapButton;

import butterknife.InjectView;

/**
 * Created by chen on 14-8-11.
 */
public class PromotionerMessage extends PromotionMain {

    @InjectView(R.id.merchants_name)EditText merchants_name;
    @InjectView(R.id.store_name)EditText store_name;
    @InjectView(R.id.bank_account)EditText bank_account;
    @InjectView(R.id.add_idcard_front)ImageView add_idcard_front;
    @InjectView(R.id.add_idcard_back)ImageView add_idcard_back;
    @InjectView(R.id.add_business_license)ImageView add_business_license;
    @InjectView(R.id.get_location)LinearLayout get_location;
    @InjectView(R.id.choose_scope)RelativeLayout choose_scope;
    @InjectView(R.id.add_finish)BootstrapButton add_finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_promotion);
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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

