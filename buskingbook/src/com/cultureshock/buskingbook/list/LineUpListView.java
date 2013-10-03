package com.cultureshock.buskingbook.list;

import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.util.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LineUpListView extends ListView implements HttpClientNet.OnResponseListener
{
	private Context mContext;
	private IntromTeamListAdapter listAdapter;
	ViewHolder holder;
	private ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();
	public LineUpListView(Context context) {
		super(context);
		mContext = context;
		initListView();
	}

	public LineUpListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		initListView();
	}
	
		
	public void initListView()
	{
        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT));
        setFadingEdgeLength(0);
        setDividerHeight(0);
        
	}
	public void setListData(ArrayList<LineUpObject> arrayData) 
	{
		this.lineUpArr = arrayData;
		listAdapter = new IntromTeamListAdapter(lineUpArr);
		setAdapter(listAdapter);;
	}

	
	public void recycle() 
	{
		if (listAdapter != null)
			listAdapter.recycle();
    }

	class IntromTeamListAdapter extends ArrayAdapter<LineUpObject> 
	{
		private LinearLayout mLayoutCalendar;
		private TextView mCalendar;
		private ImageView mImg;
		private TextView mTeamname;
		private TextView mTime;
		private TextView mPlace;
		private ImageView mLike;
		private TextView mRanking;
		private ImageView mLikeImg;
		private TextView mLikeCount;
		private ImageView mTeamInfo;
		
		private LinearLayout mNoDataLayout;
		private LinearLayout mDataLayout;
		
	    private List<WeakReference<View>> mRecycleList = new ArrayList<WeakReference<View>>();
		
		public void recycle() {
	        for (WeakReference<View> ref : mRecycleList) {
	            Util.recursiveRecycle(ref.get());
	        }
	    }

		public IntromTeamListAdapter(List<LineUpObject> objects) {
			super(mContext, 0, objects);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_main_lineup, null);
				mLayoutCalendar = (LinearLayout)convertView.findViewById(R.id.item_lineup_calendar_layout);
				mCalendar = (TextView)convertView.findViewById(R.id.item_lineup_calendar);
				mImg = (ImageView)convertView.findViewById(R.id.item_lineup_image);
				mTeamname = (TextView) convertView.findViewById(R.id.item_lineup_teamname);
				mTime = (TextView) convertView.findViewById(R.id.item_lineup_time);
				mPlace = (TextView) convertView.findViewById(R.id.item_lineup_place);
				mLike = (ImageView) convertView.findViewById(R.id.item_lineup_like);
				
				mRanking = (TextView) convertView.findViewById(R.id.item_lineup_bottom_ranking);
				mLikeImg = (ImageView) convertView.findViewById(R.id.item_lineup_bottom_likecount_img);
				mLikeCount= (TextView) convertView.findViewById(R.id.item_lineup_bottom_likecount);
				mTeamInfo = (ImageView) convertView.findViewById(R.id.item_lineup_bottom_likecount_img);
				
				mNoDataLayout = (LinearLayout)convertView.findViewById(R.id.item_lineup_nolineup);
				mDataLayout = (LinearLayout)convertView.findViewById(R.id.item_lineup_time_layout);
				holder = new ViewHolder();
				holder.layoutCalendar = mLayoutCalendar;
				holder.calendar = mCalendar;
				holder.img = mImg;
				holder.teamname = mTeamname;
				holder.time= mTime;
				holder.place = mPlace;
				holder.like = mLike;
				holder.ranking = mRanking;
				holder.likeImg = mLikeImg;
				holder.likeCount = mLikeCount;
				holder.teamInfo = mTeamInfo;
				holder.noDataLayout = mNoDataLayout;
				holder.dataLayout = mDataLayout;
				convertView.setTag(holder);
				
		        mRecycleList.add(new WeakReference<View>(convertView));
				
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			LineUpObject itemObject = getItem(position);
			convertView.setTag(R.id.imageId, position);
			if(position == 0 )
			{
				holder.layoutCalendar.setVisibility(View.VISIBLE);
				holder.calendar.setText(itemObject.getYear() + ". " + itemObject.getMonth() + ". " + itemObject.getDay() + "  " + itemObject.getDayOfweek());
			}
			else
			{
				LineUpObject beforeObject = getItem(position-1);
				if(itemObject.getYear().equals(beforeObject.getYear()) &&
						itemObject.getMonth().equals(beforeObject.getMonth()) &&
						itemObject.getDay().equals(beforeObject.getDay()))
				{
					holder.layoutCalendar.setVisibility(View.GONE);
				}
				else
				{
					holder.layoutCalendar.setVisibility(View.VISIBLE);
					holder.calendar.setText(itemObject.getYear() + ". " + itemObject.getMonth() + ". " + itemObject.getDay() + "  " + itemObject.getDayOfweek());
				}
			}
			
			holder.teamname.setText(itemObject.getTeamName());
			holder.time.setText(itemObject.getTime());
			holder.place.setText(itemObject.getPlace());
			holder.ranking.setText(itemObject.getRanking());
			holder.likeCount.setText(itemObject.getJoinCount());
			return convertView;
		}
	}
	

	class ViewHolder 
	{
		private LinearLayout layoutCalendar;
		private TextView calendar;
		private ImageView img;
		private TextView teamname;
		private TextView time;
		private TextView place;
		private ImageView like;
		private TextView ranking;
		private ImageView likeImg;
		private TextView likeCount;
		private ImageView teamInfo;
		
		private LinearLayout noDataLayout;
		private LinearLayout dataLayout;
	}

	public void notifyData()
	{
		listAdapter.notifyDataSetChanged(); 
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		
	}
	

}
