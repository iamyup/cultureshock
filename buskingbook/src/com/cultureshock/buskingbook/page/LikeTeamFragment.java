package com.cultureshock.buskingbook.page;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.IssueNewListView;
import com.cultureshock.buskingbook.list.LikeTeamListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;

public class LikeTeamFragment extends Fragment implements OnClickListener{
    private FragmentActivity mContext;
    private TextView m_oBtnTest;
    private ImageView m_oBtnList;
    private ViewPager m_oViewPager;
    private LinearLayout m_oLayoutList;
    private LinearLayout m_oLayoutNo;
    private LikeTeamListView m_oListLikeTeam;
    private ArrayList<Integer> ranking = new ArrayList<Integer>();
    private ArrayList<TeamObject> m_oLikeTeamObject = new ArrayList<TeamObject>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.like_team, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        MainActivity.getInstance().onBottom();
        m_oBtnTest = (TextView) getActivity().findViewById(R.id.test_main);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);

        m_oLayoutList = (LinearLayout)getActivity().findViewById(R.id.like_team_list);
        m_oLayoutNo = (LinearLayout)getActivity().findViewById(R.id.no_like_team);
        
        if(m_oListLikeTeam == null)
        {
        	m_oListLikeTeam = new LikeTeamListView(mContext);
        }
        setData();
        if(m_oLikeTeamObject.size() != 0)
        {
        	m_oLayoutNo.setVisibility(View.GONE);
        }
        else
        {
        	m_oLayoutNo.setVisibility(View.VISIBLE);
        }
        m_oListLikeTeam.setListData(m_oLikeTeamObject,ranking);
        m_oLayoutList.addView(m_oListLikeTeam);
        
    }
    public void setData()
    {
    	ArrayList<String> likeTeam = LoginInfoObject.getInstance().getLikeTeamList();
    	for(int i = 0 ; i<likeTeam.size() ; i++)
    	{
    		for(int j = 0 ;j<MainActivity.getTeamObject().size();j++)
    		{
    			if(likeTeam.get(i).equals(MainActivity.getTeamObject().get(j).getTeamName()))
    			{
    				m_oLikeTeamObject.add(MainActivity.getTeamObject().get(j));
    				ranking.add(j);
    				break;
    			}
    		}
    	}
    }
    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
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
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        // case R.id.test_main :
        // {
        // Bundle bundle = new Bundle();
        // bundle.putString("album_id", "");
        // MainActivity.getInstance().replaceFragment(MusicChartFragment.class,
        // bundle, true);
        // break;
        // }
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        }
    }
}
