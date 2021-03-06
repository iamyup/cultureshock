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
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.page.Main_LineUp_Page;
import com.cultureshock.buskingbook.page.TeamPageFragment;
import com.cultureshock.buskingbook.page.TimePosterFragment;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;
import com.cultureshock.buskingbook.util.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
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

public class LineUpListView extends ListView implements
		HttpClientNet.OnResponseListener {
	private Context mContext;
	private IntromTeamListAdapter listAdapter;
	ViewHolder holder;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private ArrayList<LineUpObject> lineUpArr = new ArrayList<LineUpObject>();
	private TeamObject m_oSelectTeam;
	private boolean checkToday = false;
	private int year;
	private int month;
	private int day;
	private String week;
	private Handler m_oHandler ;
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

	public void setHandler(Handler handler)
	{
		this.m_oHandler = handler;
	}
	public void checkToday() {
		GregorianCalendar calendar = new GregorianCalendar();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH) + 1;
		day = calendar.get(Calendar.DATE);
		int wod = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		String[] weeks = { "일", "월", "화", "수", "목", "금", "토" };
		week = weeks[wod];
		for (int i = 0; i < lineUpArr.size(); i++) {
			if (year == Integer.parseInt(lineUpArr.get(i).getYear())
					&& month == Integer.parseInt(lineUpArr.get(i).getMonth())
					&& day == Integer.parseInt(lineUpArr.get(i).getDay())) {
				checkToday = true;
			}
		}

	}

	public void initListView() {
		setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		setFadingEdgeLength(0);
		setDividerHeight(0);

	}

	public void setListData(ArrayList<LineUpObject> arrayData) {
		this.lineUpArr = arrayData;
		checkToday();
		listAdapter = new IntromTeamListAdapter(lineUpArr);
		setAdapter(listAdapter);
	}

	public void recycle() {
		if (listAdapter != null)
			listAdapter.recycle();
	}

	class IntromTeamListAdapter extends ArrayAdapter<LineUpObject> {
		private LinearLayout mLayoutCalendar;
		private TextView mCalendar;
		private TextView mCalendarWeek;
		private ImageView mImg;
		private TextView mTeamname;
		private TextView mTime;
		private TextView mPlace;
		private LinearLayout mLike;
		private TextView mRanking;
		private ImageView mLikeImg;
		private TextView mLikeCount;
		private LinearLayout mTeamInfo;
		private TextView mFirstCalendar;
		// private TextView mFirstCalendarWeek;

		private RelativeLayout mNoDataLayout;
		private LinearLayout mDataLayout;
		private LinearLayout mBottomLayout;
		private LinearLayout mTodayLayout;
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
				mLayoutCalendar = (LinearLayout) convertView
						.findViewById(R.id.item_lineup_calendar_layout);
				mCalendar = (TextView) convertView
						.findViewById(R.id.item_lineup_calendar);
				mCalendarWeek = (TextView) convertView
						.findViewById(R.id.item_lineup_calendar_day_of_week);
				mImg = (ImageView) convertView
						.findViewById(R.id.item_lineup_image);
				mTeamname = (TextView) convertView
						.findViewById(R.id.item_lineup_teamname);
				mTime = (TextView) convertView
						.findViewById(R.id.item_lineup_time);
				mPlace = (TextView) convertView
						.findViewById(R.id.item_lineup_place);
				mRanking = (TextView) convertView
						.findViewById(R.id.item_lineup_bottom_ranking);
				mLike = (LinearLayout) convertView
						.findViewById(R.id.like_select);
				mLikeImg = (ImageView) convertView
						.findViewById(R.id.item_lineup_bottom_likecount_img);
				mLikeCount = (TextView) convertView
						.findViewById(R.id.item_lineup_bottom_likecount);
				mTeamInfo = (LinearLayout) convertView
						.findViewById(R.id.team_page);
				mBottomLayout = (LinearLayout) convertView
						.findViewById(R.id.item_lineup_bottom);
				mNoDataLayout = (RelativeLayout) convertView
						.findViewById(R.id.item_lineup_nolineup);
				mDataLayout = (LinearLayout) convertView
						.findViewById(R.id.item_lineup_time_layout);
				mFirstCalendar = (TextView) convertView
						.findViewById(R.id.item_lineup_calendar_2);
				mTodayLayout = (LinearLayout) convertView
						.findViewById(R.id.today_layout_real);

				holder = new ViewHolder();
				holder.firstCalendar = mFirstCalendar;
				holder.layoutCalendar = mLayoutCalendar;
				holder.calendarWeek = mCalendarWeek;
				holder.firstLayout = mTodayLayout;
				holder.calendar = mCalendar;
				holder.img = mImg;
				holder.teamname = mTeamname;
				holder.time = mTime;
				holder.place = mPlace;
				holder.like = mLike;
				holder.ranking = mRanking;
				holder.likeImg = mLikeImg;
				holder.likeCount = mLikeCount;
				holder.teamInfo = mTeamInfo;
				holder.noDataLayout = mNoDataLayout;
				holder.dataLayout = mDataLayout;
				holder.bottomLayout = mBottomLayout;
				convertView.setTag(holder);

				mRecycleList.add(new WeakReference<View>(convertView));
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			convertView.setTag(R.id.imageId, position);
			holder.img.setTag(R.id.imageId, position);
			holder.like.setTag(R.id.imageId, position);
			holder.teamInfo.setTag(R.id.imageId, position);
			LineUpObject itemObject = getItem(position);
			convertView.setTag(R.id.imageId, position);
			if (position == 0) {
				
				if (checkToday) {
					holder.firstLayout
							.setBackgroundResource(R.drawable.lineup_back);

					holder.dataLayout.setVisibility(View.VISIBLE);
					holder.bottomLayout.setVisibility(View.VISIBLE);
					holder.noDataLayout.setVisibility(View.GONE);
					holder.layoutCalendar.setVisibility(View.VISIBLE);
					holder.calendar.setText("오늘의공연");
					holder.calendar.setTextColor(0xff01d0d2);
					holder.calendarWeek.setVisibility(View.GONE);
					holder.calendarWeek.setText("");
				} else {
					holder.firstLayout.setBackgroundColor(0xffffffff);

					// holder.firstCalendar.setText(year+"."+month+"."+day+" ");
					holder.layoutCalendar.setVisibility(View.VISIBLE);
					holder.noDataLayout.setVisibility(View.VISIBLE);
					holder.dataLayout.setVisibility(View.VISIBLE);
					holder.bottomLayout.setVisibility(View.VISIBLE);
					holder.calendar.setText(itemObject.getYear() + "."
							+ itemObject.getMonth() + "." + itemObject.getDay()
							+ " ");
					holder.calendarWeek.setVisibility(View.VISIBLE);
					holder.calendarWeek.setText(itemObject.getDayOfweek());

					holder.calendar.setTextColor(0xff000000);
				}

			} else {
//				m_oHandler.sendMessage(
//				m_oHandler.obtainMessage(Main_LineUp_Page.POSITION_ANOTHER));
//				// holder.firstLayout.setBackgroundResource(R.drawable.lineup_back);
				holder.firstLayout.setBackgroundColor(0xffffffff);
				// holder.calendar.setTextColor(0xff01d0d2);
				holder.calendar.setTextColor(0xff000000);

				holder.noDataLayout.setVisibility(View.GONE);
				holder.calendarWeek.setVisibility(View.VISIBLE);
				holder.calendarWeek.setTextColor(0xff000000);
				LineUpObject beforeObject = getItem(position - 1);

				if (itemObject.getYear().equals(beforeObject.getYear())
						&& itemObject.getMonth()
								.equals(beforeObject.getMonth())
						&& itemObject.getDay().equals(beforeObject.getDay())) {
					holder.layoutCalendar.setVisibility(View.GONE);
				} else {
					holder.layoutCalendar.setVisibility(View.VISIBLE);
					holder.calendar.setText(itemObject.getYear() + "."
							+ itemObject.getMonth() + "." + itemObject.getDay()
							+ " ");
					holder.calendarWeek.setText(itemObject.getDayOfweek());
				}
			}
			Drawable default1 = null;
			default1 = mContext.getResources().getDrawable(
					R.drawable.loading_new_2);

			holder.teamname.setText(itemObject.getTeamName());
			holder.time.setText(itemObject.getTime());
			holder.place.setText(itemObject.getPlace());

			for (int i = 0; i < MainActivity.getTeamObject().size(); i++) {
				if (itemObject.getTeamName().equals(
						MainActivity.getTeamObject().get(i).getTeamName())) {
					m_oAsyncImageLoader.setImageDrawableAsync(holder.img,
							MainActivity.getTeamObject().get(i).getTeamThum(),
							default1, default1, mContext);
					holder.ranking.setText("TOP" + (i + 1));
					holder.likeCount.setText(MainActivity.getTeamObject()
							.get(i).getLikeCount()
							+ "");
					break;
				}
			}
			for (int i = 0; i < LoginInfoObject.getInstance().getLikeTeamList()
					.size(); i++) {
				if (LoginInfoObject.getInstance().getLikeTeamList().get(i)
						.equals(itemObject.getTeamName())) {
					holder.likeImg.setBackgroundResource(R.drawable.heart_o);
					break;
				}
			}
			holder.teamInfo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int pos = (Integer) v.getTag(R.id.imageId);
					Bundle o = new Bundle();
					o.putString("object", getItem(pos).getTeamName());
					MainActivity.getInstance().replaceFragment(
							TeamPageFragment.class, o, true);
				}
			});
			holder.img.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					int pos = (Integer) v.getTag(R.id.imageId);
					TeamObject itemObject = null;
					Bundle o = new Bundle();
					o.putString("object", getItem(pos).getTeamName());
					o.putString("year", getItem(pos).getYear());
					o.putString("month", getItem(pos).getMonth());
					o.putString("day", getItem(pos).getDay());
					o.putString("week", getItem(pos).getDayOfweek());
					o.putString("mapX", getItem(pos).getMapX());
					o.putString("mapY", getItem(pos).getMapY());
					o.putString("time", getItem(pos).getTime());
					o.putString("title", getItem(pos).getTitle());
					o.putString("content", getItem(pos).getContent());
					MainActivity.getInstance().replaceFragment(
							TimePosterFragment.class, o, true);
				}
			});
			holder.like.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (LoginInfoObject.getInstance().isLogin()) {
						int pos = (Integer) v.getTag(R.id.imageId);
						TeamObject itemObject = null;
						if (MainActivity.searchTeam(getItem(pos).getTeamName()) != null) {
							itemObject = MainActivity.searchTeam(getItem(pos)
									.getTeamName());
							int check = 0;
							for (int i = 0; i < itemObject.getLikeMans().size(); i++) {
								if (itemObject
										.getLikeMans()
										.get(i)
										.equals(LoginInfoObject.getInstance()
												.getId())) {
									Toast.makeText(mContext, "이미 좋아하는 팀입니다.",
											Toast.LENGTH_SHORT).show();
									check = 1;
									break;
								}
							}
							if (check != 1) {
								// 통신이후 좋아요 카운트 하나 늘리면됨
								m_oSelectTeam = itemObject;
								requestTeamLikeUp();
							}
						} else {
							// 팀이 없습니다
						}
					} else {
						new LoginAlertPopup(mContext);
					}
				}
			});

			return convertView;
		}
	}

	class ViewHolder {
		private LinearLayout layoutCalendar;
		private TextView calendar;
		private TextView calendarWeek;
		private ImageView img;
		private TextView teamname;
		private TextView time;
		private TextView place;
		private LinearLayout like;
		private TextView ranking;
		private ImageView likeImg;
		private TextView likeCount;
		private LinearLayout teamInfo;

		private LinearLayout bottomLayout;
		private RelativeLayout noDataLayout;
		private LinearLayout dataLayout;
		private LinearLayout firstLayout;
		private TextView firstCalendar;
	}

	public void notifyData() {
		listAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try {
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_TEAM_LIKE_UP")) {
				String result = object.getJSONObject("data").optString(
						"result", "");
				if (result.equals("true")) {
					Toast.makeText(mContext, "좋아요", Toast.LENGTH_LONG).show();
					// 개인데이터에 내가 좋아요 눌른 팀 체크
					LoginInfoObject.getInstance().getLikeTeamList()
							.add(m_oSelectTeam.getTeamName());
					// 로컬 카운트 + 1 , 그 팀 이 직접가지고 있는 라이크 눌른 인원들에 대한 아이디 추가
					for (int i = 0; i < MainActivity.getTeamObject().size(); i++) {
						if (m_oSelectTeam.getTeamName().equals(
								MainActivity.getTeamObject().get(i)
										.getTeamName())) {
							MainActivity
									.getTeamObject()
									.get(i)
									.setLikeCount(
											MainActivity.getTeamObject().get(i)
													.getLikeCount() + 1);
							MainActivity.getTeamObject().get(i).getLikeMans()
									.add(LoginInfoObject.getInstance().getId());
							notifyData();
							break;

						}
					}

				} else {
					Toast.makeText(mContext, "좋아요 실패했습니다", Toast.LENGTH_LONG)
							.show();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((MainActivity) mContext).stopProgressDialog();
		}
	}

	public String setLikeTeam() {
		String likeTeam = "";
		for (int i = 0; i <= LoginInfoObject.getInstance().getLikeTeamList()
				.size(); i++) {
			if (LoginInfoObject.getInstance().getLikeTeamList().size() == 0) {
				likeTeam = m_oSelectTeam.getTeamName();
				break;
			}
			if (i == LoginInfoObject.getInstance().getLikeTeamList().size() - 1) {
				likeTeam += LoginInfoObject.getInstance().getLikeTeamList()
						.get(i)
						+ "," + m_oSelectTeam.getTeamName();
				break;
			}
			likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i)
					+ ",";
		}
		return likeTeam;
	}

	public String setLikeMan() {
		String str = "";
		for (int i = 0; i <= m_oSelectTeam.getLikeMans().size(); i++) {
			if (m_oSelectTeam.getLikeMans().size() == 0) {
				str = LoginInfoObject.getInstance().getId();
				break;
			}
			if (i == m_oSelectTeam.getLikeMans().size() - 1) {
				str += m_oSelectTeam.getLikeMans().get(i) + ","
						+ LoginInfoObject.getInstance().getId();
				break;
			}
			str += m_oSelectTeam.getLikeMans().get(i) + ",";
		}
		return str;
	}

	public void requestTeamLikeUp() {
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(
				ServiceType.MSG_TEAM_LIKE_UP);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", m_oSelectTeam.getTeamName()));
		loginParams.add(new Params("likecount",
				(m_oSelectTeam.getLikeCount() + 1) + ""));
		loginParams
				.add(new Params("id", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("liketeam", setLikeTeam()));
		loginParams.add(new Params("likeman", setLikeMan()));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		((MainActivity) mContext).startProgressDialog();
	}

}
