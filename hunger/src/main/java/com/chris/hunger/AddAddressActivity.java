package com.chris.hunger;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chris.hunger.utils.GetURLUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddAddressActivity extends Activity {

    private static final String ACTION_ADD_SUCCESS = "com.sunshine.chbaddress.action.ADD_ADDRESS_SUCCESS";
    private ImageView ivAddressForward2;
    private EditText etNameAddaddress;
    private RadioGroup rgSexAddaddress;
    private RadioButton rbMaleAddaddress;
    private RadioButton rbFamaleAddaddress;
    private EditText etPhoneAddaddress;
    private EditText etGpsAddaddress;
    private EditText etDetailaddAddaddress;
    private Button btnOkAddaddress;
    private String nameAddress;
    private String phoneAddress;
    private String gpsAddress;
    private String detailAddress;
    private String sexAddress;
    private int addAddrResult;
    private Intent intent;

    private void assignViews() {
        ivAddressForward2 = (ImageView) findViewById(R.id.iv_address_forward2);
        etNameAddaddress = (EditText) findViewById(R.id.et_name_addaddress);
        rgSexAddaddress = (RadioGroup) findViewById(R.id.rg_sex_addaddress);
        rbMaleAddaddress = (RadioButton) findViewById(R.id.rb_male_addaddress);
        rbFamaleAddaddress = (RadioButton) findViewById(R.id.rb_famale_addaddress);
        etPhoneAddaddress = (EditText) findViewById(R.id.et_phone_addaddress);
        etGpsAddaddress = (EditText) findViewById(R.id.et_gps_addaddress);
        etDetailaddAddaddress = (EditText) findViewById(R.id.et_detailadd_addaddress);
        btnOkAddaddress = (Button) findViewById(R.id.btn_ok_addaddress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__address);
        assignViews();

        //跳转至新增地址的前一页
        ivAddressForward2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(AddAddressActivity.this, ShipAddressActivity.class);
//                startActivity(intent);


                finish();
            }
        });

        etGpsAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressActivity.this, PoiSearchActivity.class);
                Log.d("tag", "aa1111111");
                startActivityForResult(intent, 1);
                Log.d("tag", "bbb22222");
            }
        });
        initViews();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("tag", data + "kkkkkkkkkkkkkkk");
        if (data!=null){
           String address=data.getStringExtra("address");
           etGpsAddaddress.setText(address);
       }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //初始化新增地址页面的值，跳转至添加成功页面
    public void initViews(){
        /*
            点击确定按钮，提交信息新增地址信息到服务器。
            如果提交成功，收到服务器反馈信息，跳转至添加地址成功页面
            如果提交失败，显示输入信息有误，添加失败
         */
        btnOkAddaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果登陆成功，跳转至Http连接服务
                if (addAddressSuccess()) {// 不为空

                    RequestParams params = new RequestParams(GetURLUtil.ADDRESS_BaseURL+"addAddress.do");
                    params.addQueryStringParameter("sex",sexAddress);
                    params.addQueryStringParameter("phone",phoneAddress);
                    params.addQueryStringParameter("address",gpsAddress+detailAddress);
                    params.addQueryStringParameter("name",nameAddress);
                    params.addQueryStringParameter("id","4");
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                            Log.d("tag", "成功了");
                                    Intent intent1 = new Intent(AddAddressActivity.this, ShipAddressActivity.class);
                                    intent1.putExtra("sex",sexAddress);
                                    intent1.putExtra("phone",phoneAddress);
                                    intent1.putExtra("address",gpsAddress+detailAddress);
                                    intent1.putExtra("name",nameAddress);
//                                    intent1.putExtra("id",1);

                                    AddAddressActivity.this.setResult(1,intent1);
                                    finish();
                                    Log.d("tag", "bbbbb");
                                }


                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
//                            Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(AddAddressActivity.this, "输入信息有误，请重新填写", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onCancelled(CancelledException cex) {
                            Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
                        }


                        @Override
                        public void onFinished() {

                        }
                    });
                }else {
                    Toast.makeText(AddAddressActivity.this, "信息不全", Toast.LENGTH_SHORT).show();

                }
            }
        });

        rgSexAddaddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rbMaleAddaddress.getId()) {
                    sexAddress = rbMaleAddaddress.getText().toString();
//                    Log.d("tag", "mmmmmmmmmm"+sexSuccess);
                } else if (checkedId == rbFamaleAddaddress.getId()) {
                    sexAddress = rbFamaleAddaddress.getText().toString();
                }
            }
        });
    }

    //判断添加地址是否成功
    public boolean addAddressSuccess(){
        nameAddress = etNameAddaddress.getText().toString().trim();
//        sexAddress = getRadioButtonChecked();
        phoneAddress = etPhoneAddaddress.getText().toString().trim();
        gpsAddress = etGpsAddaddress.getText().toString().trim();
        detailAddress = etDetailaddAddaddress.getText().toString().trim();

        if(nameAddress==null){
            Toast.makeText(AddAddressActivity.this,"联系人姓名不得为空",Toast.LENGTH_SHORT)
                    .show();
            Log.d("Add", "联系人姓名不得为空1111111111111111111111111111111111111111111111111111");
            return false;
        }
       else if(sexAddress==null){
            Toast.makeText(AddAddressActivity.this, "性别不得为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
      else  if(phoneAddress==null){
            Toast.makeText(AddAddressActivity.this, "联系方式不能为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
       else if(gpsAddress==null){
            Toast.makeText(AddAddressActivity.this, "地址不能为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
       else if(detailAddress==null){
            Toast.makeText(AddAddressActivity.this, "用户详细地址不能为空", Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        return true;
    }


    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }
}
