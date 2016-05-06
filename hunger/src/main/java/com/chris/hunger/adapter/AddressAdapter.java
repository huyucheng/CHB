package com.chris.hunger.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chris.hunger.AddAddressActivity;
import com.chris.hunger.Order_Certain_Activity;
import com.chris.hunger.R;
import com.chris.hunger.entity.Address;
import com.chris.hunger.utils.GetURLUtil;
import com.chris.hunger.utils.PrefUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huyucheng on 2016/3/16.
 */
public class AddressAdapter extends BaseAdapter {

    private List<Address> list = new ArrayList<>();
    private Context context;
    private Intent intent;
    public AddressAdapter(Context context, List<Address> list,Intent intent) {
        this.context = context;
        this.list = list;
        this.intent=intent;
    }

    public void addItem(Address data) {
        list.add(data);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.adrsuccess_listview_items, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Address address= (Address)list.get(position);
        viewHolder.tv_detail_addrsuccess.setText(address.getAddress());
        viewHolder.tv_sex_addrsuccess.setText(address.getSex());
        viewHolder.tv_phone_addrsuccess.setText(address.getPhone());
        viewHolder.tv_name_addrsuccess.setText(address.getName());
        viewHolder.ll_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = intent;
                if (data.getIntExtra("orderFLAG", 0) == 1) {

                    Intent intent = new Intent(context, Order_Certain_Activity.class);

                    intent.putExtra("userid", PrefUtils.getString(context, "userId", ""));
                    intent.putExtra("FLAG", 1);
//                    intent.putExtra("address", address.getAddress());
                    PrefUtils.setString(context, "orderAddress", address.getAddress());
//                    PrefUtils.setString(context,"orderSex",address.getSex());
                    PrefUtils.setString(context,"orderPhone",address.getPhone());
                    PrefUtils.setString(context,"orderName",address.getName());
                    context.startActivity(intent);
//                    Log.d("tag", "已经进入地址item了。。。。。。。。。。。。。。");
                }
            }
        });
        viewHolder.ll_address.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("您要删除该地址吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDataFromServer(address.getId()+"");
                        Log.i("!!!!", "www" + list.get(position).getId().toString());
                        list.remove(position);
                        Toast.makeText(context, "删除订单成功!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });

        viewHolder.iv_edit_addrsuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddAddressActivity.class);
//                intent.putExtra("userid", PrefUtils.getString(context,"userId",""));
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public TextView tv_name_addrsuccess;
        public TextView tv_sex_addrsuccess;
        public TextView tv_phone_addrsuccess;
        public LinearLayout ll_addrsuccess;
        public TextView tv_gpsAddress_addrsuccess;
        public TextView tv_detail_addrsuccess;
        public ImageView iv_edit_addrsuccess;
        public LinearLayout ll_address;
        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name_addrsuccess = (TextView) rootView.findViewById(R.id.tv_name_addrsuccess);
            this.tv_sex_addrsuccess = (TextView) rootView.findViewById(R.id.tv_sex_addrsuccess);
            this.tv_phone_addrsuccess = (TextView) rootView.findViewById(R.id.tv_phone_addrsuccess);
            this.ll_addrsuccess = (LinearLayout) rootView.findViewById(R.id.ll_addrsuccess);
            this.tv_gpsAddress_addrsuccess = (TextView) rootView.findViewById(R.id.tv_gpsAddress_addrsuccess);
            this.tv_detail_addrsuccess = (TextView) rootView.findViewById(R.id.tv_detail_addrsuccess);
            this.iv_edit_addrsuccess = (ImageView) rootView.findViewById(R.id.iv_edit_addrsuccess);
            this.ll_address=(LinearLayout)rootView.findViewById(R.id.ll_address);
        }

    }
    private void getDataFromServer(final String addressId) {
        HttpUtils utils = new HttpUtils();
        final String URL= GetURLUtil.ADDRESS_BaseURL+"deleteAddress.do?id="+addressId;
        // 使用xutils发送请求
        HttpHandler<String> send = utils.send(HttpRequest.HttpMethod.GET, URL,
                new RequestCallBack<String>() {
                    // 访问成功, 在主线程运行
                    @Override
                    public void onSuccess(ResponseInfo responseInfo) {
                        String result1 = (String) responseInfo.result;
                        Log.d("tag"," + result1+ URL");
                        try {
                            JSONObject jsonObject = new JSONObject(result1);
                        }catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    // 访问失败, 在主线程运行
                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, "当前网络不可用,请检查网络！", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }
        );
    }
}