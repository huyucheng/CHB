package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.chris.hunger.view.SelectPicPopupWindow;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class UserInfoActivity extends Activity {

    public static final String URL = "http://192.168.253.1:8080/asf-logo.png";//雨哥服务器接口
    //自定义的弹出框类
    SelectPicPopupWindow menuWindow;

//    private HttpUtils http;
    private String userImageResult;

    private static final int PHOTO_CARMERA = 1;
    private static final int PHOTO_PICK = 2;
    private static final int PHOTO_CUT = 3;

    // 创建一个以当前系统时间为名称的文件，防止重复
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 使用系统当前日期加以调整作为照片的名称
    private String getPhotoFileName() {
//        Date date = new Date(System.currentTimeMillis());
//        SimpleDateFormat sdf = new SimpleDateFormat("'PNG'_yyyyMMdd_HHmmss");
        return /*sdf.format(date) */ "a.png";
    }

    private ImageView ivAddressForward2;
    private ImageView ivCameraUserimage;
    private TextView tvUsername;
    private TextView tvPhoneUser;
    private TextView tvWeixinUser;
    private TextView tvQqUser;
    private TextView tvWeiboUser;
    private TextView tvLoginpswUser;
    private TextView tvZfbpswUser;
    private String imageName;

    private void assignViews() {
        ivAddressForward2 = (ImageView) findViewById(R.id.iv_address_forward2);
        ivCameraUserimage = (ImageView) findViewById(R.id.iv_camera_userimage);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        tvPhoneUser = (TextView) findViewById(R.id.tv_phone_user);
        tvWeixinUser = (TextView) findViewById(R.id.tv_weixin_user);
        tvQqUser = (TextView) findViewById(R.id.tv_qq_user);
        tvWeiboUser = (TextView) findViewById(R.id.tv_weibo_user);
        tvLoginpswUser = (TextView) findViewById(R.id.tv_loginpsw_user);
        tvZfbpswUser = (TextView) findViewById(R.id.tv_zfbpsw_user);
        ivAddressForward2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        assignViews();
        Intent intent=getIntent();
        tvUsername.setText(intent.getStringExtra("name"));
        tvPhoneUser.setText(intent.getStringExtra("phone"));
        Log.d("###",intent.getStringExtra("name")+"___________"+intent.getStringExtra("phone"));
        tvUsername.setTextColor(Color.GRAY);
        if("success".equals(intent.getStringExtra("loginValue"))){

            PrefUtils.setString(UserInfoActivity.this, "userId", intent.getStringExtra("userId"));
            Log.d("tag", "用户Id是" + intent.getStringExtra("userId"));
            PrefUtils.setString(UserInfoActivity.this, "loginValue", "success");
            Log.d("###",PrefUtils.getString(getApplicationContext(),"loginValue",""));
            PrefUtils.setString(UserInfoActivity.this, "name", intent.getStringExtra("name"));
            PrefUtils.setString(UserInfoActivity.this,"password",intent.getStringExtra("password"));
            PrefUtils.setString(UserInfoActivity.this,"phone",intent.getStringExtra("phone"));
            Log.d("tag","pref设置成功");
        }
        //实例化SelectPicPopupWindow,实现拍照小窗口监听事件
        menuWindow = new SelectPicPopupWindow(UserInfoActivity.this, itemsOnClick);

        ivCameraUserimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示窗口
                menuWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.ll_user),
                        Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.5f;
                getWindow().setAttributes(lp);
            }
        });

        menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);

            }
        });
        String path = Environment.getExternalStorageDirectory()+"/"+getPhotoFileName() ;//获得SDCard目录

        Log.d("tag",path);
        Bitmap bmpDefaultPic;
        bmpDefaultPic = BitmapFactory.decodeFile(path, null);
        ivCameraUserimage.setImageBitmap(bmpDefaultPic);
//        http = new HttpUtils(10000);
    }

    //为弹出窗口实现监听类
    private OnClickListener itemsOnClick = new OnClickListener() {

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.tv_take_photo:
                    // 调用拍照
                    startCamera(menuWindow);
                    break;
                case R.id.tv_pick_photo:
                    // 调用相册
                    startPick(menuWindow);
                    break;
                default:
                    break;
            }

        }

    };

    // 调用系统相机
    protected void startCamera(SelectPicPopupWindow menuWindow) {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("camerasensortype", 2); // 调用前置摄像头
        intent.putExtra("autofocus", true); // 自动对焦
        intent.putExtra("fullScreen", false); // 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的存储路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        Log.d("tag", tempFile + "aaaaaaaaaaaaaaaa");
        startActivityForResult(intent, PHOTO_CARMERA);
        Log.d("tag", tempFile + "bbbbbbbbbbbbbbbbbb");

    }

    // 调用系统相册
    protected void startPick(SelectPicPopupWindow menuWindow) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, PHOTO_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("tag", data + "-----------------");
        switch (requestCode) {
            case PHOTO_CARMERA:
                Log.d("tag", tempFile + "111111111111111111");
                startPhotoZoom(Uri.fromFile(tempFile), 300);
                Log.d("tag", tempFile + "999999999999999999999");
                break;
            case PHOTO_PICK:
                if (null != data) {
                    Log.d("tag", data + "444444444444");
                    startPhotoZoom(data.getData(), 300);
                }
                break;
            case PHOTO_CUT:
                Log.d("tag", data + "==============");
                if (null != data) {
                    Log.d("tag", data + "55555555555555");
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // 调用系统裁剪
    private void startPhotoZoom(Uri uri, int size) {
        Log.d("tag",   "tttttttttttt");
        Intent intent = new Intent("com.android.camera.action.CROP");
        Log.d("tag",   intent+"kkkkkkkkkkkkk");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以裁剪
        intent.putExtra("crop", true);
        // aspectX,aspectY是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX,outputY是裁剪图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        // 设置是否返回数据
        intent.putExtra("return-data", true);
        Log.d("tag",intent+ "ooooooooooooo");
        startActivityForResult(intent, PHOTO_CUT);
        Log.d("tag", "yyyyyyyyyyyy");
    }

    // 将裁剪后的图片,上传到服务器，成功后显示在ImageView上
    private void setPicToView(Intent data) {
        Log.d("tag", "mmmmmmmmmmmmmm");
//        upLoadImage();
        Log.d("tag", "pppppppppppppp");
//        if (userImageResult.equals("true")) {
            Bundle bundle = data.getExtras();
//            if (null != bundle) {
                final Bitmap bmp = bundle.getParcelable("data");
                ivCameraUserimage.setImageBitmap(bmp);
                saveCropPic(bmp);
//                Log.i("UserInfoActivity", tempFile.getAbsolutePath());
//            }
//        } else {
//            Toast.makeText(UserInfoActivity.this, "上传失败，检查一下服务器地址是否正确", Toast.LENGTH_SHORT).show();
//        }

    }

    // 上传文件到服务器
    protected void upLoadImage() {
        String userId=PrefUtils.getString(UserInfoActivity.this,"userId","");
//        String passWord=PrefUtils.getString(UserInfoActivity.this, "name", "");
        Log.d("tag", userId + "執行到上傳圖片");
        RequestParams params = new RequestParams(GetURLUtil.UPLOADIMAGE+"userId="+userId);
        params.addQueryStringParameter("PhotoName",getPhotoFileName());
        Log.d("tag", params + "3333333333333");
        Log.d("tag",URL+"开始传输图片");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Log.d("tag",result+"拍照后返回的數據");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
        Log.d("tag", "******************************");
//        return userImageResult;
    }

    // 把裁剪后的图片保存到sdcard上
    private void saveCropPic(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileOutputStream fis = null;
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            fis = new FileOutputStream(tempFile);
            fis.write(baos.toByteArray());
            fis.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != baos) {
                    baos.close();
                }
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
