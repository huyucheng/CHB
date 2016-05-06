package com.chris.hunger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.utils.GetURLUtil;

import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * Created by guozi on 2016/3/14.
 */
public class OKRegisterActivity extends Activity {

//    public static final String REGISTER_URL = "http://10.6.12.67:8080/chb/user/";

    private TextView tv_register_phone,tv_new_password_register;
    private EditText et_register_code,et_new_password_register,et_confirm_password_register;
    private Button btn_register_again,btn_register_ok;
    private String country="86";
    private String phone;
    private String new_pwd,cfm_pwd;
    private View ivBack;

    //-----------短信验证码登录-----------
    String APPKEY = "10082851d5db8";
    String APPSECRET = "d8e91f215262be4138811f90c11b6366";

//	String APPKEY = "1018c0a536a22";
//	String APPSECRET = "25ef3a8e92368a5c16f78535c5b45012";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_ok);

        tv_register_phone=(TextView) findViewById(R.id.tv_register_phone);
        et_register_code=(EditText) findViewById(R.id.et_register_code);
        et_new_password_register=(EditText) findViewById(R.id.et_new_password_register);
        et_confirm_password_register=(EditText) findViewById(R.id.et_confirm_password_register);
        ivBack =(ImageView)findViewById(R.id.iv_password_reset);

        tv_new_password_register = (TextView) findViewById(R.id.tv_new_password_register);//提示密码不一致的TextView

        //追加ChangedListener
        et_confirm_password_register.addTextChangedListener(textWatcher);

        //获取Intent中的手机号
        Intent intent=getIntent();
        phone=intent.getStringExtra("phone_number");
        Log.d("tag",phone+"得到了手机号99999999999");
        tv_register_phone.setText(phone);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.d("tag", "Intent中的手机号：" + phone);

        //-----------短信验证码登录-----------
        //初始化
        SMSSDK.initSDK(this, APPKEY, APPSECRET);
        //配置信息
        SMSSDK.registerEventHandler(eh);
        //-----------短信验证码登录-----------

        String country="86";
        //获取语音验证码
        SMSSDK.getVoiceVerifyCode(country, phone);
//        Toast.makeText(RegisterActivity.this, "正在获取语音验证码，请保持手机畅通", Toast.LENGTH_LONG);

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            //新密码
            new_pwd=et_new_password_register.getText().toString().trim();
            //确认密码
            cfm_pwd=et_confirm_password_register.getText().toString().trim();

            //判断两次输入的密码是否相同
            if(!cfm_pwd.equals(new_pwd)){
                tv_new_password_register.setVisibility(View.VISIBLE);//设置可见

            }else{
                tv_new_password_register.setVisibility(View.GONE);//设置消失
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    //监听事件
    //重新获取
    public void btn_register_again(View v){
        //获取语音验证码
        SMSSDK.getVoiceVerifyCode(country, phone);
    }

    //完成注册
    public void btn_register_ok(View v){
        //判断两次输入的密码是否相同
        new_pwd = et_new_password_register.getText().toString().trim();
        cfm_pwd = et_confirm_password_register.getText().toString().trim();
        if(!new_pwd.equals(cfm_pwd)|new_pwd.equals("")|cfm_pwd.equals("")){

            showWarningDialog();

        }else{

            //提交短信验证码
            SMSSDK.submitVerificationCode(country, phone, et_register_code.getText().toString());

        }
    }

        //弹出警告
    private void showWarningDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(OKRegisterActivity.this);
        builder.setTitle("提示").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("两次输入的密码不一致，请确认您的密码！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        dialog = builder.create();
        dialog.setCancelable(false);//3/22
        dialog.show();
    }

    //向服务器发送请求
    public void registerInfoToServer() {
        //拼接成请求参数
        String userName = phone;
        String passWord = cfm_pwd;
        Log.d("tag", "----------向服务器发送请求-----------:" + phone+","+cfm_pwd);
        RequestParams params = new RequestParams(GetURLUtil.REGISTER_URL + "registerUser.do?userName=" + userName + "&passWord=" + passWord);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                Log.d("tag", "----------注册-----------onSuccess:成功" + result);

                //验证成功，跳转到登录界面
                Intent intent = new Intent(OKRegisterActivity.this, XLoginMainActivity.class);
                intent.putExtra("userName", phone);
                intent.putExtra("passWord", cfm_pwd);
                startActivity(intent);

                finish();//3/22

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("tag", "----------注册-----------onError:错误" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                Log.d("tag", "----------注册-----------onCancelled:取消");
            }

            @Override
            public void onFinished() {

            }
        });

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.d("tag1", "event==========================" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
//                System.out.println("--------result" + event);
                Log.d("tag1", "result==========================" + result);

                Log.d("tag1", "SMSSDK.RESULT_COMPLETE==========================" + SMSSDK.RESULT_COMPLETE);
                Log.d("tag1", "SMSSDK.EVENT_GET_VERIFICATION_CODE==========================" + SMSSDK.EVENT_GET_VERIFICATION_CODE);
                Log.d("tag1", "SMSSDK.EVENT_GET_VERIFICATION_CODE==========================" + SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE);

                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
                    Toast.makeText(getApplicationContext(), "提交验证码成功", Toast.LENGTH_SHORT).show();

                    //向服务器发送请求
                    registerInfoToServer();

//                    startActivity(new Intent(OKRegisterActivity.this, LoginTestActivity.class));

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();


                }

            } else {
//				((Throwable) data).printStackTrace();
                Toast.makeText(OKRegisterActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;

                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
//                        Toast.makeText(OKRegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }


        }

        ;
    };

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
}
