package com.chris.hunger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.readStreamUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SettingAboutCHBActivity extends Activity implements GetURLUtil {

    protected static final int CODE_UPDATE_DIALOG = 0;
    protected static final int CODE_URL_ERROR = 1;
    protected static final int CODE_NET_ERROR = 2;
    protected static final int CODE_JSON_ERROR = 3;

    // 服务器返回的信息
    private String mVersionName;// 版本名
    private int mVersionCode;// 版本号
    private String mDesc;// 版本描述
    private String mDownLoadUrl;// 下载地址

    private ImageView ivAddressForward;
    private ImageView ivSettingAboutChb;
    private TextView tvProverb;
    private TextView tvVersionCode;
    private TextView tvCheckVersion;
    private TextView tvWelcome;
    private TextView tvMessage;
    private TextView tvMes;
    private ProgressBar progressDowloadSetting;

    private void assignViews() {
        ivAddressForward = (ImageView) findViewById(R.id.iv_address_forward);
        ivSettingAboutChb = (ImageView) findViewById(R.id.iv_setting_about_chb);
        tvProverb = (TextView) findViewById(R.id.tv_proverb);
        tvVersionCode = (TextView) findViewById(R.id.tv_versionCode);
        tvCheckVersion = (TextView) findViewById(R.id.tv_checkVersion);
        tvWelcome = (TextView) findViewById(R.id.tv_welcome);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvMes = (TextView) findViewById(R.id.tv_mes);
        progressDowloadSetting = (ProgressBar) findViewById(R.id.progress_dowload_setting);
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDailog();
                    break;
                case CODE_URL_ERROR:
                    Toast.makeText(SettingAboutCHBActivity.this, "url错误", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case CODE_NET_ERROR:
                    Toast.makeText(SettingAboutCHBActivity.this, "网络错误", Toast.LENGTH_SHORT)
                            .show();
                    break;
                case CODE_JSON_ERROR:
                    Toast.makeText(SettingAboutCHBActivity.this, "数据解析错误",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_about_chb);
        assignViews();

        //跳转至前一页
        ivAddressForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingAboutCHBActivity.this,SettingPageActivity.class);
                startActivity(intent);
            }
        });
        tvWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingAboutCHBActivity.this,GuideActivity.class);
                startActivity(intent);
            }
        });
        tvVersionCode.setText("version: " + getVersionName());

        //点击检查更新，判断是否需要更新
        tvCheckVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //检查是否需要更新
                checkVerson();
            }
        });
    }
    //获取版本号,设置版本信息
    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;// 获取包的信息
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
    //获取版本码,设置版本信息
    private int getVersionCode() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;// 获取包的信息
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            int versionCode = packageInfo.versionCode;
            return versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //检查最新版本，判断是否需要更新
    private void checkVerson() {
//        final long startTime = System.currentTimeMillis();
        // 启动子线程异步加载数据
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                HttpURLConnection conn = null;
                try {
                    // CHB/updateCHB.json
                    URL url = new URL(GetURLUtil.ADDRESS_BaseURL + "CHB/updateCHB.json");
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");// 设置请求方法
                    conn.setConnectTimeout(5000);// 设置连接超时
                    conn.setReadTimeout(5000);// 设置响应超时, 连接上了,
                    conn.connect();// 连接服务器
                    int responseCode = conn.getResponseCode();// 获取响应码
                    if (responseCode == 200) {
                        InputStream inputStream = conn.getInputStream();
                        byte[] result=null;
                        try{

                            result = readStreamUtil.readStream(inputStream);
                        }catch (Exception e){

                        }
                        String json = new String(result);
                         System.out.println("网络返回:" + json);
                        // 解析json
                        JSONObject jo = new JSONObject(json);
                        mVersionName = jo.getString("versionName");
                        mVersionCode = jo.getInt("versionCode");
                        mDesc = jo.getString("description");
                        mDownLoadUrl = jo.getString("downLoadUrl");

                        if (mVersionCode > getVersionCode()) {// 判断是否有更新
                            // 服务器的VersionCode大于本地的VersionCode
                            // 说明有更新, 弹出升级对话框
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            Toast.makeText(SettingAboutCHBActivity.this,"您当前已为最新版本",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (MalformedURLException e) {
                    // url错误的异常
                    msg.what = CODE_URL_ERROR;
                    e.printStackTrace();
                } catch (IOException e) {
                    // 网络错误异常
                    msg.what = CODE_NET_ERROR;
                    e.printStackTrace();
                } catch (JSONException e) {
                    // json解析失败
                    msg.what = CODE_JSON_ERROR;
                    e.printStackTrace();
                } finally {
                    mHandler.sendMessage(msg);
                    if (conn != null) {
                        conn.disconnect();// 关闭网络连接
                    }
                }
            }
        }.start();
    }
    /**
     * 升级对话框
     */
    protected void showUpdateDailog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("最新版本:" + mVersionName);
        builder.setMessage("新增功能，是否更新");
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("立即更新");
                download();
            }
        });

        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        // 设置取消的监听, 用户点击返回键时会触发
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                return;
            }
        });

        builder.show();
    }

    /**
     * 下载apk文件
     */
    protected void download() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            progressDowloadSetting.setVisibility(View.VISIBLE);// 显示进度
            String target = Environment.getExternalStorageDirectory()
                    + "/update.apk";
            // XUtils
            HttpUtils utils = new HttpUtils();
            utils.download(mDownLoadUrl, target, new RequestCallBack<File>() {

                // 下载文件的进度, 该方法在主线程运行
                @Override
                public void onLoading(long total, long current,
                                      boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    int currentProgress = (int)(current/total)*100;
                    progressDowloadSetting.setProgress(currentProgress);
                    System.out.println("下载进度:" + current + "/" + total);
                }

                // 下载成功,该方法在主线程运行
                @Override
                public void onSuccess(ResponseInfo<File> arg0) {
                    System.out.println("下载成功");

                    // 跳转到系统安装页面
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(arg0.result),
                            "application/vnd.android.package-archive");
                    startActivityForResult(intent, 0);// 如果用户取消安装的话,
                    // 会返回结果,回调方法onActivityResult
                }

                @Override
                public void onFailure(com.lidroid.xutils.exception.HttpException e, String s) {
                    Toast.makeText(SettingAboutCHBActivity.this, "下载失败!",
                            Toast.LENGTH_SHORT).show();
                }

                // 下载失败,该方法在主线程运行

            });
        } else {
            Toast.makeText(SettingAboutCHBActivity.this, "没有找到sdcard!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // 如果用户取消安装的话,回调此方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDowloadSetting.setVisibility(View.INVISIBLE);
        return;
    }

}
