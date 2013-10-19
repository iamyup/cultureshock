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
import com.cultureshock.buskingbook.framework.BaseActivity;
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
	private TextView mTeamNotice;
	private TextView mTeamInfo;
	private LinearLayout mTeamOut;
	private LinearLayout mTeamMemberList;
	private String teamname;
	private ArrayList<TeamMemberObject> object ;
	
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
        mTeamMemberList = (LinearLayout)getActivity().findViewById(R.id.team_page_teammember_list);
		mTeamname = (TextView) getActivity().findViewById(R.id.team_page_teamname);
		mTeamNotice = (TextView)getActivity().findViewById(R.id.team_page_team_notice);
		mTeamInfo = (TextView)getActivity().findViewById(R.id.team_page_team_info);
		mTeamname.setText(teamname);
		for(int i = 0 ; i<MainActivity.getInstance().getTeamObject().size() ;i++)
		{
			if(MainActivity.getInstance().getTeamObject().get(i).getTeamName().equals(teamname))
			{
				object = MainActivity.getInstance().getTeamObject().get(i).getTeamMember();
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
        }

    }
   
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		

	}
	
}
