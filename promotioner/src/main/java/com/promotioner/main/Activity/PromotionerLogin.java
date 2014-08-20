package com.promotioner.main.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.promotioner.main.ApiManager.PromotionerApiManager;
import com.promotioner.main.Model.LoginBackData;
import com.promotioner.main.R;
import com.promotioner.main.Utils.ConstantUtils;
import com.promotioner.main.Utils.ToastUtils;
import com.promotioner.main.View.Button.BootstrapButton;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import rx.android.concurrency.AndroidSchedulers;
import rx.util.functions.Action0;
import rx.util.functions.Action1;

/**
 * Created by chen on 14-8-15.
 */
public class PromotionerLogin extends PromotionMain {

    private Handler handler;
    private SharedPreferences.Editor editor;
    private SharedPreferences mshared;

    @InjectView(R.id.login_phonenumber)EditText userphonenumber;
    @InjectView(R.id.login_password)EditText userpassword;
    @InjectView(R.id.login_button)BootstrapButton button;
    @InjectView(R.id.login_progressbar)SmoothProgressBar progressBar;

    @OnClick(R.id.login_button)void loginbutton(){
        String username = userphonenumber.getText().toString().trim();
        String password = userpassword.getText().toString().trim();
        if(username.equals(""))
        {
            ToastUtils.setToast(PromotionerLogin.this, "请输入用户名!");
        }
        else if(password.equals(""))
        {
            ToastUtils.setToast(PromotionerLogin.this,"请输入密码!");
        }
//        else if(!ValidationUtils.isMobileNO(username))
//        {
//            ToastUtils.setToast(PromotionerLogin.this,"请输入正确的电话号码!");
//        }
        else {
            Login(username,password);
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_promotioner);
        ButterKnife.inject(this);//为监听所用
        //显示actionbar上面的返回键
        ActionBar actionBar = this.getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub

                switch(msg.what)
                {
                    case ConstantUtils.MSG_SUCCESS:
                        LoginBackData loginBackData = (LoginBackData)msg.obj;
                        mshared = getSharedPreferences("usermessage", 0);
                        editor = mshared.edit();
                        editor.putString("token", loginBackData.token);
                        editor.putString("key", loginBackData.key);
                        editor.commit();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent();
                        intent.setClass(PromotionerLogin.this,PromotionerAdd.class);
                        startActivity(intent);
                        PromotionerLogin.this.finish();
                        break;
                    case ConstantUtils.MSG_FALTH:
                        progressBar.setVisibility(View.GONE);
                        ToastUtils.setToast(PromotionerLogin.this,"用户名或者密码有误!");
                        break;
                }
                super.handleMessage(msg);
            }

        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:
                PromotionerLogin.this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Login(String username, String password)
    {
        PromotionerApiManager.getLoginBackData(username,password).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<LoginBackData>() {
                    @Override
                    public void call(LoginBackData loginBackData) {
                        // 获取一个Message对象，设置what为1
                        Message msg = Message.obtain();
                        msg.obj = loginBackData;
                        msg.what = ConstantUtils.MSG_SUCCESS;
                        // 发送这个消息到消息队列中
                        handler.sendMessage(msg);
                    }
                },new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        // 获取一个Message对象，设置what为1
                        Message msg = Message.obtain();
                        msg.obj = null;
                        msg.what = ConstantUtils.MSG_FALTH;
                        // 发送这个消息到消息队列中
                        handler.sendMessage(msg);
                    }
                },new Action0() {
                    @Override
                    public void call() {

                    }
                });
    }
}
