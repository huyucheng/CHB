package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.baseImpl.UserInfoSystemPager;


public class NoNetTakeOutActivity extends Activity {
    private LinearLayout llTitle;
    private TextView tvSearchStoreByaddress;
    private EditText etSearchStoreByaddress;
    private ImageView ivDeleteText;
    private ImageView searchStoreByname;
    private ImageView ivNoNetAddress;
    private TextView tvNoNetAddress;
    private Button btnLoadAgain;

    private void assignViews() {
        llTitle = (LinearLayout) findViewById(R.id.ll_title);
        tvSearchStoreByaddress = (TextView) findViewById(R.id.tv_search_store_byaddress);
        etSearchStoreByaddress = (EditText) findViewById(R.id.et_search_store_byaddress);
        ivDeleteText = (ImageView) findViewById(R.id.ivDeleteText);
        searchStoreByname = (ImageView) findViewById(R.id.search_store_byname);
        ivNoNetAddress = (ImageView) findViewById(R.id.iv_no_net_address);
        tvNoNetAddress = (TextView) findViewById(R.id.tv_no_net_address);
        btnLoadAgain = (Button) findViewById(R.id.btn_load_again);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_out_not_net_address);
        assignViews();
        etSearchStoreByaddress.setText("无网络");
        etSearchStoreByaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(NoNetTakeOutActivity.this,
                        SearchStoreByNameActivity.class);
                startActivity(intent5);
            }
        });
        tvSearchStoreByaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(NoNetTakeOutActivity.this,
                        SearchStoreByNameActivity.class);
                startActivity(intent5);
            }
        });
        btnLoadAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int netType = UserInfoSystemPager.getNetType(NoNetTakeOutActivity.this);
                if (netType == -1) {
                    Toast.makeText(NoNetTakeOutActivity.this, "网络异常，请重新设置", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(NoNetTakeOutActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
