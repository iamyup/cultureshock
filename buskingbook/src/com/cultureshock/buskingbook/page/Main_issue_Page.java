package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.FirstStartActivity;
import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.list.IssueNewListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class Main_issue_Page extends LinearLayout implements
		View.OnClickListener, HttpClientNet.OnResponseListener {

	private Context mContext;
	private View v = null;
	private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
	private ArrayList<LineUpObject> lineUpObject = new ArrayList<LineUpObject>();
	Dialog dialog;
	// best
	private TeamObject bestTeam;
	private String teamBestTeam;
	private String bestranking;
	private String bestTeamTime;
	private LinearLayout mGoteamPage;
	private ImageView mImg;
	private TextView mTeamname;
	private LinearLayout mLike;
	private TextView mRanking;
	private ImageView mLikeImg;
	private TextView mLikeCount;
	private TextView mTime;
	// issue
	private TeamObject issueTeam;
	private String teamBestTeam2;
	private String bestranking2;
	private String bestTeamTime2;
	private LinearLayout mGoteamPage2;
	private ImageView mImg2;
	private TextView mTeamname2;
	private LinearLayout mLike2;
	private TextView mRanking2;
	private ImageView mLikeImg2;
	private TextView mLikeCount2;
	private TextView mTime2;
	private int issuecount;
	// new team
	private LinearLayout m_oLayoutNew;
	private int check1;
	private int check2;
	private boolean checkbestissue = false;
	private IssueNewListView listView;

	private LinearLayout m_oLayoutArticle;
	private ImageView m_oImgArticle;
	private TextView m_oTxtArticle;
	private String article_team;

	public Main_issue_Page(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		mContext = context;
		int teamallSize = MainActivity.getTeamObject().size();
		try {
			Random random = new Random();
			issuecount = random.nextInt(teamallSize);
		} catch (Exception e) {
			e.printStackTrace();
			issuecount = 1;
		}
		if (MainActivity.getTeamObject().size() == 0) {

		} else {
			bestTeam = MainActivity.getTeamObject().get(0);
			issueTeam = MainActivity.getTeamObject().get(issuecount);
		}
		initView();

	}

	public void show() {
		dialog.show();
	}

	public void dismissPopup() {
		dialog.dismiss();
	}

	private void initView() {
		// TODO Auto-generated method stub

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.main_issue, null);
		this.addView(v);
		// dialog = new Dialog(mContext, R.style.Dialog);
		// dialog.addContentView(this, new
		// LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		//
		// dialog.setCanceledOnTouchOutside(false);
		setui();
	}
	public void recycleResoure ()
	{
		mGoteamPage = null;
		mImg = null;
		mTeamname = null;
		mLike = null;
		mRanking = null;
		mLikeImg = null;
		mLikeCount = null;
		mTime = null;
		mGoteamPage2 = null;
		mImg2 = null;
		mTeamname2 = null;
		mLike2 = null;
		mRanking2 = null;
		mLikeImg2 = null;
		mLikeCount2 = null;
		mTime2 = null;
		m_oLayoutNew = null;
		m_oLayoutArticle = null;
		m_oImgArticle = null;
		m_oTxtArticle = null;
		if(listView != null)
			listView.recycle();
		
		
	}
	public void setui() {
		mImg = (ImageView) v.findViewById(R.id.busker_join_image_2);
		mTeamname = (TextView) v.findViewById(R.id.busker_join_teamname_2);
		mRanking = (TextView) v.findViewById(R.id.busker_join_ranking_2);
		mLike = (LinearLayout) v.findViewById(R.id.like_select);
		mLike.setOnClickListener(this);
		mLikeImg = (ImageView) v
				.findViewById(R.id.item_lineup_bottom_likecount_img);
		mLikeCount = (TextView) v
				.findViewById(R.id.item_lineup_bottom_likecount);
		mGoteamPage = (LinearLayout) v.findViewById(R.id.team_page);
		mGoteamPage.setOnClickListener(this);
		mTime = (TextView) v.findViewById(R.id.team_time_info);

		mImg2 = (ImageView) v.findViewById(R.id.busker_join_image_3);
		mTeamname2 = (TextView) v.findViewById(R.id.busker_join_teamname_3);
		mRanking2 = (TextView) v.findViewById(R.id.busker_join_ranking_3);
		mLike2 = (LinearLayout) v.findViewById(R.id.like_select_3);
		mLike2.setOnClickListener(this);
		mLikeImg2 = (ImageView) v
				.findViewById(R.id.item_lineup_bottom_likecount_img_3);
		mLikeCount2 = (TextView) v
				.findViewById(R.id.item_lineup_bottom_likecount_3);
		mGoteamPage2 = (LinearLayout) v.findViewById(R.id.team_page_3);
		mGoteamPage2.setOnClickListener(this);
		mTime2 = (TextView) v.findViewById(R.id.team_time_info_3);
		m_oLayoutNew = (LinearLayout) v.findViewById(R.id.lineup_list);

		m_oLayoutArticle = (LinearLayout) v.findViewById(R.id.issue_acticle);
		m_oLayoutArticle.setOnClickListener(this);
		m_oTxtArticle = (TextView) v.findViewById(R.id.issue_aricle);
		m_oImgArticle = (ImageView) v
				.findViewById(R.id.busker_join_article_img);
		// if(listView == null)
		// {
		// listView = new IssueNewListView(mContext);
		//
		// int sizelist = BaseActivity.getTeamObject().size();
		// ArrayList<TeamObject> o = new ArrayList<TeamObject>();
		//
		//
		// o.add(BaseActivity.getTeamObject().get(sizelist-1));
		// listView.setListData(o);
		// m_oLayoutNew.addView(listView);
		// }
		// else
		// {
		// int sizelist = BaseActivity.getTeamObject().size();
		// ArrayList<TeamObject> o = new ArrayList<TeamObject>();
		// for(int i = sizelist/2 ; i<sizelist; i++)
		// {
		// o.add(BaseActivity.getTeamObject().get(i));
		// }
		// listView.setListData(o);
		// m_oLayoutNew.addView(listView);
		// }
		try {
			dataUiset();
		} catch (Exception e) {
			e.printStackTrace();
		}
		requestAricle();
	}

	public void dataUiset() {
		for (int i = 0; i < LoginInfoObject.getInstance().getLikeTeamList()
				.size(); i++) {
			if (LoginInfoObject.getInstance().getLikeTeamList().get(i)
					.equals(bestTeam.getTeamName())) {
				mLikeImg.setBackgroundResource(R.drawable.heart_o);
				break;
			}
		}
		Drawable default1 = null;
		default1 = mContext.getResources().getDrawable(R.drawable.loading_new_2);
		m_oAsyncImageLoader.setImageDrawableAsync(mImg, bestTeam.getTeamThum(),
				default1, default1, mContext);
		mTeamname.setText(bestTeam.getTeamName());
		mRanking.setText("TOP " + 1);
		mLikeCount.setText(bestTeam.getLikeCount() + "");

		for (int i = 0; i < LoginInfoObject.getInstance().getLikeTeamList()
				.size(); i++) {
			if (LoginInfoObject.getInstance().getLikeTeamList().get(i)
					.equals(issueTeam.getTeamName())) {
				mLikeImg2.setBackgroundResource(R.drawable.heart_o);
				break;
			}
		}
		m_oAsyncImageLoader.setImageDrawableAsync(mImg2,
				issueTeam.getTeamThum(), default1, default1, mContext);
		mTeamname2.setText(issueTeam.getTeamName());
		mRanking2.setText("TOP " + (issuecount + 1));
		mLikeCount2.setText(issueTeam.getLikeCount() + "");

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.issue_acticle: {
			Bundle o = new Bundle();
			o.putString("object", article_team);
			MainActivity.getInstance().replaceFragment(ArticleFragment.class,
					null, false);
			break;
		}
		case R.id.like_select: {
			// TODO Auto-generated method stub
			if (LoginInfoObject.getInstance().isLogin()) {
				for (int i = 0; i < bestTeam.getLikeMans().size(); i++) {
					if (bestTeam.getLikeMans().get(i)
							.equals(LoginInfoObject.getInstance().getId())) {
						Toast.makeText(mContext, "이미 좋아하는 팀입니다.",
								Toast.LENGTH_SHORT).show();
						check1 = 1;
						break;
					}
				}
				if (check1 != 1) {
					// 통신이후 좋아요 카운트 하나 늘리면됨
					requestTeamLikeUp("best");
				}
			} else {
				new LoginAlertPopup(mContext);
			}

			break;

		}
		case R.id.team_page: {
			Bundle o = new Bundle();
			o.putString("object", bestTeam.getTeamName());
			MainActivity.getInstance().replaceFragment(TeamPageFragment.class,
					o, true);
			break;
		}
		case R.id.like_select_3: {
			// TODO Auto-generated method stub
			if (LoginInfoObject.getInstance().isLogin()) {
				for (int i = 0; i < issueTeam.getLikeMans().size(); i++) {
					if (issueTeam.getLikeMans().get(i)
							.equals(LoginInfoObject.getInstance().getId())) {
						Toast.makeText(mContext, "이미 좋아하는 팀입니다.",
								Toast.LENGTH_SHORT).show();
						check2 = 1;
						break;
					}
				}
				if (check2 != 1) {
					// 통신이후 좋아요 카운트 하나 늘리면됨
					requestTeamLikeUp("issue");
				}
			} else {
				new LoginAlertPopup(mContext);
			}

			break;

		}
		case R.id.team_page_3: {
			Bundle o = new Bundle();
			o.putString("object", issueTeam.getTeamName());
			MainActivity.getInstance().replaceFragment(TeamPageFragment.class,
					o, true);
			break;
		}
		}
	}

	public String setbLikeTeam() {
		String likeTeam = "";
		for (int i = 0; i <= LoginInfoObject.getInstance().getLikeTeamList()
				.size(); i++) {
			if (LoginInfoObject.getInstance().getLikeTeamList().size() == 0) {
				likeTeam = bestTeam.getTeamName();
				break;
			}
			if (i == LoginInfoObject.getInstance().getLikeTeamList().size() - 1) {
				likeTeam += LoginInfoObject.getInstance().getLikeTeamList()
						.get(i)
						+ "," + bestTeam.getTeamName();
				break;
			}
			likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i)
					+ ",";
		}
		return likeTeam;
	}

	public String setiLikeTeam() {
		String likeTeam = "";
		for (int i = 0; i <= LoginInfoObject.getInstance().getLikeTeamList()
				.size(); i++) {
			if (LoginInfoObject.getInstance().getLikeTeamList().size() == 0) {
				likeTeam = issueTeam.getTeamName();
				break;
			}
			if (i == LoginInfoObject.getInstance().getLikeTeamList().size() - 1) {
				likeTeam += LoginInfoObject.getInstance().getLikeTeamList()
						.get(i)
						+ "," + issueTeam.getTeamName();
				break;
			}
			likeTeam += LoginInfoObject.getInstance().getLikeTeamList().get(i)
					+ ",";
		}
		return likeTeam;
	}

	public String setbLikeMan() {
		String str = "";
		for (int i = 0; i <= bestTeam.getLikeMans().size(); i++) {
			if (bestTeam.getLikeMans().size() == 0) {
				str = LoginInfoObject.getInstance().getId();
				break;
			}
			if (i == bestTeam.getLikeMans().size() - 1) {
				str += bestTeam.getLikeMans().get(i) + ","
						+ LoginInfoObject.getInstance().getId();
				break;
			}
			str += bestTeam.getLikeMans().get(i) + ",";
		}
		return str;
	}

	public String setiLikeMan() {
		String str = "";
		for (int i = 0; i <= issueTeam.getLikeMans().size(); i++) {
			if (issueTeam.getLikeMans().size() == 0) {
				str = LoginInfoObject.getInstance().getId();
				break;
			}
			if (i == issueTeam.getLikeMans().size() - 1) {
				str += issueTeam.getLikeMans().get(i) + ","
						+ LoginInfoObject.getInstance().getId();
				break;
			}
			str += issueTeam.getLikeMans().get(i) + ",";
		}
		return str;
	}

	public void requestTeamLikeUp(String name) {
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(
				ServiceType.MSG_TEAM_LIKE_UP);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		if (name.equals("best")) {
			checkbestissue = false;
			loginParams.add(new Params("teamname", bestTeam.getTeamName()));
			loginParams.add(new Params("likecount",
					(bestTeam.getLikeCount() + 1) + ""));
			loginParams.add(new Params("id", LoginInfoObject.getInstance()
					.getId()));
			loginParams.add(new Params("liketeam", setbLikeTeam()));
			loginParams.add(new Params("likeman", setbLikeMan()));

		} else {
			checkbestissue = true;
			loginParams.add(new Params("teamname", issueTeam.getTeamName()));
			loginParams.add(new Params("likecount",
					(issueTeam.getLikeCount() + 1) + ""));
			loginParams.add(new Params("id", LoginInfoObject.getInstance()
					.getId()));
			loginParams.add(new Params("liketeam", setiLikeTeam()));
			loginParams.add(new Params("likeman", setiLikeMan()));
		}
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		((MainActivity) mContext).startProgressDialog();
	}

	public void requestAricle() {
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_ARTICLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname", ""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}

	public void localArticle(String img, String title, String subtile) {
		Drawable default1 = null;
		default1 = mContext.getResources().getDrawable(
				R.drawable.default_busker);

		m_oAsyncImageLoader.setImageDrawableAsync(m_oImgArticle, img, default1,
				default1, mContext);
		m_oTxtArticle.setText(title + "\n" + subtile);
		// m_oTxtArticle.setText("  지금 당장 드루와~\n우리는 5학년 1학기\n" +
		// "    그들의 이야기  " +
		// "    자유와 로맨틱  ");
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
					if (checkbestissue) {
						LoginInfoObject.getInstance().getLikeTeamList()
								.add(issueTeam.getTeamName());
						// 로컬 카운트 + 1 , 그 팀 이 직접가지고 있는 라이크 눌른 인원들에 대한 아이디 추가
						for (int i = 0; i < MainActivity.getTeamObject().size(); i++) {
							if (issueTeam.getTeamName().equals(
									MainActivity.getTeamObject().get(i)
											.getTeamName())) {
								MainActivity
										.getTeamObject()
										.get(i)
										.setLikeCount(
												MainActivity.getTeamObject()
														.get(i).getLikeCount() + 1);
								MainActivity
										.getTeamObject()
										.get(i)
										.getLikeMans()
										.add(LoginInfoObject.getInstance()
												.getId());
								dataUiset();
								break;

							}
						}
					} else {
						LoginInfoObject.getInstance().getLikeTeamList()
								.add(bestTeam.getTeamName());
						// 로컬 카운트 + 1 , 그 팀 이 직접가지고 있는 라이크 눌른 인원들에 대한 아이디 추가
						for (int i = 0; i < MainActivity.getTeamObject().size(); i++) {
							if (bestTeam.getTeamName().equals(
									MainActivity.getTeamObject().get(i)
											.getTeamName())) {
								MainActivity
										.getTeamObject()
										.get(i)
										.setLikeCount(
												MainActivity.getTeamObject()
														.get(i).getLikeCount() + 1);
								MainActivity
										.getTeamObject()
										.get(i)
										.getLikeMans()
										.add(LoginInfoObject.getInstance()
												.getId());
								dataUiset();
								break;

							}
						}
					}

				} else {
					Toast.makeText(mContext, "좋아요 실패했습니다", Toast.LENGTH_LONG)
							.show();
				}
			} else if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_ARTICLE")) {
				article_team = object.getJSONObject("data").optString(
						"teamname", "");
				String img = object.getJSONObject("data").optString("img", "");
				String title = object.getJSONObject("data").optString("title",
						"");
				String subTitle = object.getJSONObject("data").optString(
						"subTitle", "");
				localArticle(img, title, subTitle);
			}
			// else
			// if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TIME_TABLE"))
			// {
			// JSONArray todayTime = object.getJSONArray("data");
			// for(int i=0; i<todayTime.length(); i++)
			// {
			// JSONObject jsonObject = todayTime.getJSONObject(i);
			// String year = jsonObject.optString("year");
			// String month = jsonObject.optString("month");
			// String day = jsonObject.optString("day");
			// String time = jsonObject.optString("time");
			// String dayOfweek = jsonObject.optString("dayOfweek");
			// String place = jsonObject.optString("place");
			// String teamname = jsonObject.optString("teamname");
			// String joincount = jsonObject.optString("joincount");
			// if(teamname.equals(this.teamName))
			// {
			// lineUpObject.add(new
			// LineUpObject(year,month,day,time,dayOfweek,place,teamname,joincount));
			// }
			// }
			// if(lineUpObject.size() != 0)
			// {
			// String str =
			// lineUpObject.get(0).getMonth()+"/"+lineUpObject.get(0).getDay()
			// +" "
			// +lineUpObject.get(0).getTime()+"               @"+lineUpObject.get(0).getPlace();
			// mTimeInfo.setText(str);
			// }
			// else
			// {
			// mTimeInfo.setText("등록된 공연이 없습니다");
			//
			// }
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			((MainActivity) mContext).stopProgressDialog();
		}
	}
}
