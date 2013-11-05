package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.LoginAlertPopup;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.list.MemberListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class TeamPageSettingFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private static TeamPageSettingFragment mInstance;
	private TextView mTeamname;
	private EditText mTeamNotice;
	private EditText mTeamInfo;
	private LinearLayout mTeamOut;
	private LinearLayout mTeamChangeData;
	private LinearLayout mTeamMemberList;
	public String teamname;
	private String notice;
	private String info;
	private ArrayList<TeamMemberObject> object ;
	private TeamObject ob2;
	
	private ImageView m_oBtnList;
	private ImageView m_oBtnHome;
	private MemberListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.team_page_setting, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
        Bundle bundle = getArguments();
        teamname = bundle.getString("object");
        setUi();
    }

    public void setUi()
    {
    	m_oBtnList = (ImageView) getActivity().findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        m_oBtnHome = (ImageView) getActivity().findViewById(R.id.myteam_btn);
        m_oBtnHome.setOnClickListener(this);
        mTeamOut = (LinearLayout)  getActivity().findViewById(R.id.team_page_team_out);
        mTeamOut.setOnClickListener(this);
        mTeamChangeData = (LinearLayout)  getActivity().findViewById(R.id.team_page_change_data);
        mTeamChangeData.setOnClickListener(this);
        
        mTeamMemberList = (LinearLayout)getActivity().findViewById(R.id.team_page_teammember_list);
		mTeamname = (TextView) getActivity().findViewById(R.id.team_page_teamname);
		mTeamNotice = (EditText)getActivity().findViewById(R.id.team_page_team_notice);
		mTeamInfo = (EditText)getActivity().findViewById(R.id.team_page_team_info);
		mTeamname.setText(teamname);
		for(int i = 0 ; i<MainActivity.getInstance().getTeamObject().size() ;i++)
		{
//			if(MainActivity.getInstance().getTeamObject().get(i).getTeamName().equals(teamname))
//			{
//				object = MainActivity.getInstance().getTeamObject().get(i).getTeamMember();
//			}
			if(MainActivity.getInstance().getTeamObject().get(i).getTeamName().equals(teamname))
			{
				ob2 = MainActivity.getInstance().getTeamObject().get(i);
				break;
			}
		}
		if(listView == null)
		{
			listView = new MemberListView(mContext);
			if(object != null)
			{
				listView.setListData(object);
			}
			mTeamMemberList.addView(listView);
		}
		else
		{
			if(object != null)
			{
				listView.setListData(object);
			}
			mTeamMemberList.addView(listView);
		}
		
		
    }
    public void back()
    {
    	Bundle o = new Bundle();
		o.putString("object",teamname );
		MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
    }
   
    public static TeamPageSettingFragment getInstance() {
        return mInstance;
    }

    public void setDataUI() {
        if (getView() == null) {
            return;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    @Override
    public synchronized void onClick(View v) 
    {
        switch (v.getId()) 
        {
	        case R.id.title_btn_menu:
	        {
	            MainActivity.getInstance().showMenu();
	            break;
	        }
		    case R.id.myteam_btn:
		    {
		    	//홈으로
		    	Bundle o = new Bundle();
	    		o.putString("object", teamname);
	    		MainActivity.getInstance().replaceFragment(TeamPageFragment.class, o, true);
		        break;
		    }
		    case R.id.team_page_team_out : 
		    {

				// TODO Auto-generated method stub
				
			
		    	break;
		    }
		    case R.id.team_page_change_data : 
		    {

				// TODO Auto-generated method stub
		    
    			if(mTeamInfo.getText().toString().equals(""))
    			{
    				info = ob2.getTeamComent();
    			}
    			else
    			{
    				info = mTeamInfo.getText().toString();
    			}
		    	if(mTeamNotice.getText().toString().equals(""))
    			{
    				notice = ob2.getNotice();
    			}
    			else
    			{
    				notice = mTeamNotice.getText().toString();
    			}
		    	
		    	requestChange();
			
		    	break;
		    }
		    
        }

    }
    public void requestChange()
   	{
   		GregorianCalendar calendar = new GregorianCalendar();
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_DATA_CHANGE);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		loginParams.add(new Params("teamname", LoginInfoObject.getInstance().getMyteam()));
   		loginParams.add(new Params("teamcoment", info));
   		loginParams.add(new Params("notice",notice));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		MainActivity.getInstance().startProgressDialog();
   	}	
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try {
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			GregorianCalendar calendar = new GregorianCalendar();
			if (object.getJSONObject("result").optString("type")
					.equals("ServiceType.MSG_TEAM_DATA_CHANGE")) {
				Toast.makeText(mContext, "변경되었습니다.", Toast.LENGTH_SHORT);
				for(int i = 0 ; i<MainActivity.getInstance().getTeamObject().size() ;i++)
				{
					if(MainActivity.getInstance().getTeamObject().get(i).getTeamName().equals(teamname))
					{
						
						MainActivity.getInstance().getTeamObject().get(i).setNotice(notice);
						MainActivity.getInstance().getTeamObject().get(i).setTeamComent(info);
					}
				}
				back();

			} 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			MainActivity.getInstance().stopProgressDialog();
		}

	}
	
}
