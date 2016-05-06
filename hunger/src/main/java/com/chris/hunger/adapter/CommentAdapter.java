package com.chris.hunger.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chris.hunger.Bean.CommentBean;
import com.chris.hunger.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.lidroid.xutils.BitmapUtils;

public class CommentAdapter extends BaseAdapter {

	private LayoutInflater inflater;
//	private BitmapUtils utils;
	private int layout;
	private List<CommentBean.ShopCommentListEntity> comments;
	public CommentAdapter(Context context, List<CommentBean.ShopCommentListEntity> comments, int layout) {
		if (comments == null) {
			this.comments = new ArrayList<>();
		} else {
			this.comments = comments;
		}
		this.layout=layout;
//		utils = new BitmapUtils(context);
//		utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return comments.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return comments.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(layout, null);
			holder = new ViewHolder();
			holder.userImg = (ImageView) convertView.findViewById(R.id.iv_user_image);
			holder.userName = (TextView) convertView.findViewById(R.id.tv_user_name);
			holder.commentTime = (TextView) convertView.findViewById(R.id.tv_comment_time);
			holder.commentInfo = (TextView) convertView.findViewById(R.id.tv_comment_info);
			holder.usetStar    = (RatingBar) convertView.findViewById(R.id.tv_user_star);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();

		}

		holder.userImg.setImageResource(R.drawable.ptr_bear3);

		holder.userName.setText(comments.get(position).getUserid()+"****");

		int rank=comments.get(position).getRank();

		holder.usetStar.setRating(rank);

		holder.commentInfo.setText(comments.get(position).getContent() + "");
//		Log.d("tag","");

		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//这边改变格式
		Date d = new Date(comments.get(position).getCommenttime());
		String time = s.format(d);//可以看改变格式的效果


		holder.commentTime.setText(time+"");
		return convertView;
	}
	static class ViewHolder {
		public ImageView userImg;
		public TextView userName;
		public TextView commentTime;
		public TextView commentInfo ;
		public RatingBar usetStar ;
	}

}

