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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.utils.CodeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by guozi on 2016/3/10.
 */
public class ResetPasswordActivity extends Activity {

    private EditText etCode, et_account_password_reset;
    private ImageView imgCode;
    private TextView tvChange;
    private Button btn_next_step;
    private String phone = null;
    private String code = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        imgCode = (ImageView) findViewById(R.id.iv_identifying_code);
        et_account_password_reset = (EditText) findViewById(R.id.et_account_password_reset);
        etCode = (EditText) findViewById(R.id.et_identifying_code);
        tvChange = (TextView) findViewById(R.id.tv_change_identifying_code);
        btn_next_step = (Button) findViewById(R.id.btn_next_step);

        //监听EditText内容变化
        //给et_register追加ChangedListener
        et_account_password_reset.addTextChangedListener(textWatcher);

        //初始化一张验证码图片
        imgCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
        //获取生成的验证码
        code = CodeUtils.getInstance().code;
        Log.d("tag", "String code:" + code);

        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //生成验证码并显示在图片上
                imgCode.setImageBitmap(CodeUtils.getInstance().createBitmap());
                //获取生成的验证码
                code = CodeUtils.getInstance().code;
                Log.d("tag", "自动生成的code:" + code);
            }
        });

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

//            if(!isMobileNO(s.toString().trim())|!isEmail(s.toString().trim())){
//
//                Toast.makeText(ResetPasswordActivity.this, "手机或邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                Log.d("TAG", "onTextChanged-------2222222222222222-------->");
//            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

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

    /**
     * 验证邮箱格式
     */
    public static boolean isEmail(String emails) {
    /*
    合法E-mail地址：
    1. 必须包含一个并且只有一个符号“@”
    2. 第一个字符不得是“@”或者“.”
    3. 不允许出现“@.”或者.@
    4. 结尾不得是字符“@”或者“.”
    5. 允许“@”前的字符中出现“＋”
    6. 不允许“＋”在最前面，或者“＋@”

    正则表达式如下：
    -----------------------------------------------------------------------
    ^(\w+((-\w+)|(\.\w+))*)\+\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$
    -----------------------------------------------------------------------

    字符描述：
    ^ ：匹配输入的开始位置。
    \：将下一个字符标记为特殊字符或字面值。
    * ：匹配前一个字符零次或几次。
    + ：匹配前一个字符一次或多次。
    (pattern) 与模式匹配并记住匹配。
    x|y：匹配 x 或 y。
    [a-z] ：表示某个范围内的字符。与指定区间内的任何字符匹配。
    \w ：与任何单词字符匹配，包括下划线。
    $ ：匹配输入的结尾。
    */
        String check  = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(emails);
        if ( matcher.matches()) return true;
        else return false;
    }

    public void iv_password_reset(View v){
        finish();

    }

    //按钮监听
    public void btn_next_step(View v) {

        String str_count = et_account_password_reset.getText().toString().trim();
        String str_code = etCode.getText().toString().trim();

        Log.d("tag", "手机验证结果:" + isMobileNO(str_count));
        Log.d("tag","邮箱验证结果:"+isEmail(str_count));
        Log.d("tag", "手机邮箱--------------验证结果:" + !isMobileNO(str_count)+!isEmail(str_count));

//            if((!isMobileNO(str_count))||(!isEmail(str_count))){
        if(!isMobileNO(str_count)&!isEmail(str_count)){

            showDialog();

        }else{
            if (str_code.equalsIgnoreCase("")|!code.equalsIgnoreCase(str_code)) {
                //s.equals("")也可以
                Toast.makeText(ResetPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
            }
//            if (!code.equalsIgnoreCase(str_code)) {
//                Toast.makeText(ResetPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
////            etCode.setText("");
//            }
            if (code.equalsIgnoreCase(str_code) & (!isMobileNO(str_count)|!isEmail(str_count))) {

                Intent intent = new Intent(ResetPasswordActivity.this, OKResetPasswordActivity.class);
                phone=str_count;
                intent.putExtra("reset_phone", phone);
                startActivity(intent);

                finish();

            }

        }

    }

    //弹出警告
    private void showDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
        builder.setTitle("提示").setIcon(android.R.drawable.stat_notify_error);
        builder.setMessage("手机或邮箱格式不正确，请确认！");
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
}
