package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.Bean.LoginBean;
import com.chris.hunger.utils.GetURLUtil;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import cn.smssdk.utils.SMSLog;

/**
 * 新用户登录界面
 * Created by guozi on 2016/3/16.
 */
public class XLoginMainActivity extends Activity implements View.OnClickListener {

//    public static final String LOGIN_URL = "http://10.6.12.110:8080/chb/user/";

    //    private Button loginBtn;
    private EditText et_username, et_password;
    //    TextView tv_register_login, tv_forget_password;
    private String userName, passWord;

    //-----------短信验证码登录-----------
    String APPKEY = "10082851d5db8";
    String APPSECRET = "d8e91f215262be4138811f90c11b6366";

    //    String APPKEY = "1018c0a536a22";
//    String APPSECRET = "25ef3a8e92368a5c16f78535c5b45012";
    private LoginBean loginBean;
    private ImageView ivBack,iv_pwd;
    private TextView tvForgetPas;
    private boolean mbDisplayFlg = false;

//    private TextView tvSmsLogin;
    //-----------短信验证码登录-----------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        initView();
    }

    //初始化
    private void initView() {

        et_username = (EditText) findViewById(R.id.et_account_login);
        et_password = (EditText) findViewById(R.id.et_password_login);

        //密码显示和隐藏
        iv_pwd = (ImageView) findViewById(R.id.iv_pwd);

        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_sms_login).setOnClickListener(this);
        findViewById(R.id.tv_register_login).setOnClickListener(this);
        findViewById(R.id.tv_forgetpas_login).setOnClickListener(this);
        ivBack =(ImageView)findViewById(R.id.iv_login_forward);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(XLoginMainActivity.this,MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        //密码显示和隐藏监听
        iv_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("AndroidTest", "mbDisplayFlg = " + mbDisplayFlg);
                if (!mbDisplayFlg) {
                    // display password text, for example "123456"
                    iv_pwd.setImageResource(R.drawable.icon_show_pw);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                } else {
                    // hide password, display "."
                    iv_pwd.setImageResource(R.drawable.icon_hide_pw);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(et_password.getText().toString().length());
                }
                mbDisplayFlg = !mbDisplayFlg;
                et_password.postInvalidate();
            }

        });

        //获取用户注册的手机号和密码
        Intent intent=getIntent();
        String s=intent.getStringExtra("userName");
        et_username.setText(s);

        et_password.setText(intent.getStringExtra("passWord"));
//        Log.d("tag",s);
        //-----------短信验证码登录-----------
        //初始化
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        //配置信息
        SMSSDK.registerEventHandler(eh);
        //-----------短信验证码登录-----------
    }

    //事件监听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (matchLoginMsg()) {
                    // 如果用户名和密码均不为空
                    //向服务器发送请求
                    loginToServer();
                }
                break;

            case R.id.tv_sms_login:
                smsLogin();
                break;

            case R.id.tv_register_login:
                Intent intent1 = new Intent(XLoginMainActivity.this, RegisterActivity.class);
                startActivity(intent1);
                break;

            case R.id.tv_forgetpas_login:
                Intent intent2 = new Intent(XLoginMainActivity.this, ResetPasswordActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }

    }

    //对用户名和密码进行校验，如果都不为空返回true
    protected boolean matchLoginMsg() {
        // TODO Auto-generated method stub
        userName = et_username.getText().toString().trim();
        passWord = et_password.getText().toString().trim();

        Log.d("tag", "userName:" + userName);
        Log.d("tag", "passWord:" + passWord);

        if (userName.equals("")) {
            Toast.makeText(XLoginMainActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (passWord.equals("")) {
            Toast.makeText(XLoginMainActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //向服务器发送请求
    public void loginToServer() {
        //拼接成请求参数
        RequestParams params = new RequestParams(GetURLUtil.LOGIN_URL + "login.do?userName=" + userName + "&passWord=" + passWord);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //验证成功，跳转
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                Log.d("tag", "onSuccess:" + result);
//                int i=0;
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    i=Integer.parseInt(jsonObject.getString("success"));
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//                Intent intent=new Intent(XLoginMainActivity.this,UserInfoActivity.class);
//                startActivity(intent);
//
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
//                Log.d("tag", "onSuccess:" + result);
                parseData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("tag", "onError:错误");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                Log.d("tag", "onCancelled:取消");
            }

            @Override
            public void onFinished() {

            }
        });

    }
    protected void parseData(String result) {
        Gson gson = new Gson();

        loginBean = gson.fromJson(result, LoginBean.class);
//        Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
        String flag = loginBean.getStatus();
        Log.d("tag", "pppppppppppppp" + flag);
        if("success".equals(flag)){
            LoginBean.UserEntity user=loginBean.getUser();
            Intent intent=new Intent(XLoginMainActivity.this,UserInfoActivity.class);
            intent.putExtra("name",user.getUsername());
            intent.putExtra("password",user.getPassword());
            intent.putExtra("loginValue","success");
            intent.putExtra("userId",user.getId()+"");
            intent.putExtra("phone",user.getPhone()+"");
            Log.d("tag", "登录成功了ffffffff" + user.getId()+user.getUsername()+"________"+user.getPhone());

            startActivity(intent);
            finish();
        }else{
            Toast.makeText(XLoginMainActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();//3/22
        }

    }

    //-----------短信验证码登录-----------

    public void smsLogin() {
        //注册手机号
        RegisterPage registerPage = new RegisterPage();

        //注册回调事件
        registerPage.setRegisterCallback(new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // TODO Auto-generated method stub
                //判断结果是否已经完成
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //获取数据data
                    HashMap<String, Object> maps = (HashMap<String, Object>) data;
                    //国家
                    String country = (String) maps.get("country");
                    //手机号
                    String phone = (String) maps.get("phone");

                    Log.d("tag",country+phone);
                    submitUserInfo(country, phone);
                }

            }


        });
        //显示注册界面
        registerPage.show(XLoginMainActivity.this);


        //打开通信录好友列表页面
//        ContactsPage contactsPage = new ContactsPage();
//        contactsPage.show(XLoginMainActivity.this);
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                System.out.println("--------result" + event);
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(XLoginMainActivity.this, UserInfoActivity.class));

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();


                }

            } else {
//				((Throwable) data).printStackTrace();
                Toast.makeText(XLoginMainActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(XLoginMainActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }


        }

        ;
    };

    /**
     * 提交用户信息
     *
     * @param country
     * @param phone
     */

    public void submitUserInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickName = "chihuobao";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }


    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            mHandler.sendMessage(msg);
        }

    };


    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
        finish();
    }

    //-----------短信验证码登录-----------

}
