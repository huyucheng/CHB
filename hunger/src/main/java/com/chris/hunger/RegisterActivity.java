package com.chris.hunger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by guozi on 2016/3/14.
 * update 2016/3/17.
 */
public class RegisterActivity extends Activity {

    EditText et_register;
    Button btn_register;
    //    String str;
    String phone = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiity_register);
        btn_register=(Button) findViewById(R.id.btn_register);
        et_register = (EditText) findViewById(R.id.et_register);

        //监听EditText内容变化
        //给et_register追加ChangedListener
        et_register.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            //当用户输入正确的手机号，按钮变为可点击并变色
            //方法二
//            Log.d("TAG", "afterTextChanged--------------->");
//            if(et_register.getText().toString().length()==13){
//                btn_register.setEnabled(true);
//                Log.d("TAG", "afterTextChanged------111111--------->");
//            }
//            else{
//                btn_register.setEnabled(false);
//                Log.d("TAG", "afterTextChanged-------222222-------->");
//            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub
            Log.d("TAG", "beforeTextChanged--------------->");

        }

        //
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //手机号码自动加空格
            //方法一
            int length = s.toString().length();
            if (count == 1) {

                if (length == 3 || length == 8) {
                    et_register.setText(s + " ");
                    et_register.setSelection(et_register.getText().toString().length());
                }
            }

            //当用户输入正确的手机号，按钮变为可点击并变色
            //方法一
            if(length==13){
                //验证手机号码的正则表达式
                if(!isMobileNO(removeAllSpace(s.toString()))){

                    //弹出警告
                    showDialog();

                    Toast.makeText(RegisterActivity.this,"号码格式不正确，请输入正确的手机号",Toast.LENGTH_SHORT).show();

                }else{

                    btn_register.setEnabled(true);

                    //去掉手机号码中间的空格
                    //trim()只能剪掉自字符串两边的空格
                    phone=removeAllSpace(et_register.getText().toString());

                    Log.d("TAG", "onTextChanged"+phone+"号码长度："+phone.length());
                }

            }
            else{
                btn_register.setEnabled(false);
                Log.d("TAG", "onTextChanged-------222222-------->");
            }

            //设置后不能编辑
//            if(length>13){
//                et_register.setEnabled(false);
//            }

            //手机号码自动加空格
            //方法二
//            if (s == null || s.length() == 0) return;
//            StringBuil der sb = new StringBuilder();
//            for (int i = 0; i < s.length(); i++) {
//                if (i != 3 && i != 8 && s.charAt(i) == ' ') {
//                    continue;
//                } else {
//                    sb.append(s.charAt(i));
//                    if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
//                        sb.insert(sb.length() - 1, ' ');
//                    }
//                }
//            }
//            if (!sb.toString().equals(s.toString())) {
//                int index = start + 1;
//                if (sb.charAt(start) == ' ') {
//                    if (before == 0) {
//                        index++;
//                    } else {
//                        index--;
//                    }
//                } else {
//                    if (before == 1) {
//                        index--;
//                    }
//                }
//                et_register.setText(sb.toString());
//                et_register.setSelection(index);
            /*
            editText.setSelection(position);//position为int，指的是光标的位置，
            设置成EditText输入框中字符的长度，光标则为最后了
             */
//            }
        }
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            Log.d("TAG", "onTextChanged--------------->");
//            str = et_register.getText().toString();
//            try {
//                if(start==10){
//                    btn_register.setBackgroundColor(0xaaFF00);
//
//                    Log.d("TAG", "beforeTextChanged--------------->"+s);
//                }
//                if ((heighText.getText().toString())!=null)
//                Integer.parseInt(str);//Integer.parseInt(String)遇到一些不能被转换为整型的字符时，会抛出异常
//
//            } catch (Exception e) {
//                // TODO: handle exception
//                showDialog();
//            }
//
//        }
    };

    //弹出警告
    private void showDialog(){
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("消息").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("号码格式不正确，请输入正确的手机号");
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

    //验证手机号码的正则表达式
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    //去掉手机号码中间的空格
    public String removeAllSpace(String str)
    {
        String tmpstr=str.replace(" ","");
        return tmpstr;
    }

    public void iv_password_reset(View v){
        finish();

    }

    public void btn_register(View v) {

//        String country="86";
//        //获取语音验证码
//        SMSSDK.getVoiceVerifyCode(country, phone);
//        Toast.makeText(RegisterActivity.this,"正在获取语音验证码，请保持手机畅通",Toast.LENGTH_LONG);
//        SMSSDK.getVerificationCode(country, phone);
//        Log.d("tag", "233333333333333--------------->");

//
        //跳转到OKRegisterActivity
        Intent intent = new Intent(RegisterActivity.this, OKRegisterActivity.class);
        intent.putExtra("phone_number",phone);
        Log.d("tag",phone+"得到了手机号");
        startActivity(intent);

        finish();
    }

//    protected void onDestroy() {
//        super.onDestroy();
//        SMSSDK.unregisterAllEventHandler();
//    }
}
