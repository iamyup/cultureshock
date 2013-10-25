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
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.list.IssueNewListView;
import com.cultureshock.buskingbook.list.LikeTeamListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;

public class ArticleFragment extends Fragment implements OnClickListener{
    private FragmentActivity mContext;
    private static ArticleFragment mInstance;
    private ImageView m_oBtnList;
    private String teamname; //아티클 팀네임 이름
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.like_team, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
        Bundle bundle = getArguments();
        teamname = bundle.getString("object");
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);

    }
    public static ArticleFragment getInstance() {
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
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        }
    }
}
