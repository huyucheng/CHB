package com.chris.hunger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class Order_Comment_Activity extends Activity {


    private String shopid;
 // private String goodsid;
    private String orderid;
    private String userid;
    private String commenttime;
    private String content;
    private Integer rank;
    private String responseInfo;
    private Handler handler;
    private TextView merchant_name;
    private RatingBar whole_comment;
    private Button time_pick;
    private Button comment_commit;
    private EditText comment_text;
    private ImageView merchant_icon;
    private Integer rating=new Integer(3);
    private int  hourOfDay, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_comment);

        Intent intent=getIntent();
        Bundle bd=new Bundle();
        bd=intent.getBundleExtra("bd");
        String name=bd.getString("userid");
        Log.v("!!!Bundle","传送成功"+name);

        merchant_icon=(ImageView)this.findViewById(R.id.merchant_icon);
        merchant_name=(TextView)this.findViewById(R.id.merchant_name);
        merchant_icon=(ImageView)this.findViewById(R.id.merchant_icon);
        whole_comment=(RatingBar)this.findViewById(R.id.whole_commment);
        comment_text=(EditText)this.findViewById(R.id.edit_comment);
        Calendar calendar = Calendar.getInstance();
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        comment_commit =(Button)this.findViewById(R.id.comment_commit);
        comment_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating=(int)whole_comment.getRating();
                initData();
                getFromServer();

            }
        });

        merchant_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order_Comment_Activity.this.finish();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                responseInfo = (String) msg.obj;
                if(Integer.parseInt(responseInfo)!=0){
                //     Log.i("!!comment","success");
                    Toast.makeText(Order_Comment_Activity.this, "评价成功", Toast.LENGTH_SHORT).show();
                    Order_Comment_Activity.this.finish();
                }else{
                    Log.i("!!comment","fail");
                }
            }
        };

    }


    public void initData(){
        Intent intent =getIntent();
        Bundle bd=new Bundle();
        bd=intent.getBundleExtra("bd");

        shopid=bd.getString("shopid");
        orderid=bd.getString("orderid");
        userid=bd.getString("userid");
        commenttime=new Date().toString();
        content=comment_text.getText().toString();
        rank=(int)whole_comment.getRating();
    }

    /**
     * 使用xutils发送POST请求得到服务器返回的数据
     */
    public void getFromServer() {
        final String url =  "http://10.6.12.65/chb/order/commentInsert.do";//"http://10.6.12.21:8080/chb/order/commentInsert.do";//?&shopid=1&goodsid=2&userid=22&orderid=1&content=lalala&commenttime=1990-10-20 12:00:00&rank=3
        final RequestParams params = new RequestParams();//要传的数据
//        JSONObject json = new JSONObject();
        try {
            params.addBodyParameter("shopid", shopid);
            params.addBodyParameter("orderid", orderid);
            params.addBodyParameter("goodsid", "2");
            params.addBodyParameter("userid", userid);
            params.addBodyParameter("content", content);
            params.addBodyParameter("rank", rank.toString());//等级  评论星级

        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                Log.i("!!!connectfail",url+params.toString());
                Toast.makeText(Order_Comment_Activity.this,"fail",Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {

                Log.i("!!!connectsuccess",url+params.toString());

                Message msg = Message.obtain();
                try {
                    JSONObject json = new JSONObject((String) arg0.result);
                    msg.obj = json.getString("result");
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

}
