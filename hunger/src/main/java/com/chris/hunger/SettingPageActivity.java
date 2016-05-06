package com.chris.hunger;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.math.BigDecimal;

public class SettingPageActivity extends Activity {

    private ImageView ivAddressForward;
    private RelativeLayout rlCleanCache;
    private TextView tvCache;
    private RelativeLayout rlImageQuality;
    private TextView tvImageQuality;
    private RelativeLayout rlVersionUpdate;
    private TextView tvVersionUpdate;
    private TextView tvAboutChb;
    private Button btnExitChb;
    private File file;


    private void assignViews() {
        ivAddressForward = (ImageView) findViewById(R.id.iv_address_forward);
        rlCleanCache = (RelativeLayout) findViewById(R.id.rl_clean_cache);
        tvCache = (TextView) findViewById(R.id.tv_cache);
        rlImageQuality = (RelativeLayout) findViewById(R.id.rl_image_quality);
        tvImageQuality = (TextView) findViewById(R.id.tv_image_quality);
        rlVersionUpdate = (RelativeLayout) findViewById(R.id.rl_version_update);
        tvVersionUpdate = (TextView) findViewById(R.id.tv_version_update);
        tvAboutChb = (TextView) findViewById(R.id.tv_about_chb);
        btnExitChb = (Button) findViewById(R.id.btn_exit_chb);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        assignViews();
        //============================返回到上一页==============================================
        ivAddressForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //====================清除图片缓存:1,获取系统图片缓存，设置到文本框;2点击文本框，清除缓存=====================
        file = getCacheDir();
        try {
            tvCache.setText(getCacheSize(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        rlCleanCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (file != null && file.exists() && file.isDirectory()) {
                    for (File item : file.listFiles()) {
                        item.delete();
                        tvCache.setText("0.0M");
                    }
                }
            }
        });
        //==============================非WIFI下图片质量==================================
        rlImageQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(SettingPageActivity.this);
                builder.setTitle("非WIFI下图片质量");
                final String[] items = new String[]{
                        "普通（节省流量）",
                        "高清（最佳效果）"
                };
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvImageQuality.setText(items[which].substring(0,2));
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        //=======================================自动下载最新安装包=================================
        rlVersionUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Builder builder = new Builder(SettingPageActivity.this);
                builder.setTitle("自动下载最新安装包");
                final String[] items = new String[]{
                        "仅WIFI下载",
                        "从不"
                };
                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvVersionUpdate.setText(items[which]);
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

        //=====================关于吃货宝============================================
        tvAboutChb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingAboutCHBActivity.class);
                startActivity(intent);
            }
        });
        //=====================退出登录============================================
        //如果已经登录，则显示退出登录按钮，点击退出到登录界面
        Intent intent=getIntent();
        String flag=intent.getStringExtra("loginValue");
        if ("success".equals(flag)){

            btnExitChb.setVisibility(View.VISIBLE);
            btnExitChb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logoutIntent = new Intent(SettingPageActivity.this, XLoginMainActivity.class);
                    logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(logoutIntent);
//                finish();
                }
            });
        }else{
            btnExitChb.setVisibility(View.GONE);
        }


    }
    //   =========================清除图片缓存========================================
    //获取本App系统缓存文件大小
    public static String getCacheSize(File file) throws Exception {
        return getFormatSize(getFolderSize(file));
    }
    //格式化文件大小
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
    //获取文件大小
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }



}
