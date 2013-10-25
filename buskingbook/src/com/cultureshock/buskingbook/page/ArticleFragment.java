package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.framework.BaseActivity;
import com.cultureshock.buskingbook.list.IssueNewListView;
import com.cultureshock.buskingbook.list.LikeTeamListView;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.ArticleObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class ArticleFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private static ArticleFragment mInstance;
    private ImageView m_oBtnList;
    private String teamname; //아티클 팀네임 이름
    private ImageView m_oTitleImge;
    private TextView m_oTitle;
    private TextView m_oTitleCalendar;
    private TextView m_oSubTitle;
    private TextView m_oIntroQ;
    private TextView m_oQ1;
    private TextView m_oQ2;
    private TextView m_oQ3;
    private TextView m_oQ4;
    private TextView m_oQ5;
    private TextView m_oIntroA;
    private TextView m_oA1;
    private TextView m_oA2;
    private TextView m_oA3;
    private TextView m_oA4;
    private TextView m_oA5;
    private ArticleObject articleObject;
    private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;
        
        
        Bundle bundle = getArguments();
        if(bundle !=null)
        {
			if( bundle.getString("object")!= null)
			{
				  teamname = bundle.getString("object");
			}
        }
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        m_oTitleImge = (ImageView) getActivity().findViewById(R.id.main_article_img);
        m_oTitle = (TextView)getActivity().findViewById(R.id.main_article_title);
        m_oTitleCalendar = (TextView)getActivity().findViewById(R.id.main_article_calendar);
        m_oSubTitle = (TextView)getActivity().findViewById(R.id.main_article_intro);
        m_oIntroQ = (TextView)getActivity().findViewById(R.id.main_article_start);
        m_oQ1 = (TextView)getActivity().findViewById(R.id.main_article_q1);
        m_oQ2 = (TextView)getActivity().findViewById(R.id.main_article_q2);
        m_oQ3 = (TextView)getActivity().findViewById(R.id.main_article_q3);
        m_oQ4 = (TextView)getActivity().findViewById(R.id.main_article_q4);
        m_oQ5 = (TextView)getActivity().findViewById(R.id.main_article_q5);
        m_oIntroA= (TextView)getActivity().findViewById(R.id.main_article_start_a);
        m_oA1 = (TextView)getActivity().findViewById(R.id.main_article_a1);
        m_oA2 = (TextView)getActivity().findViewById(R.id.main_article_a2);
        m_oA3 = (TextView)getActivity().findViewById(R.id.main_article_a3);
        m_oA4 = (TextView)getActivity().findViewById(R.id.main_article_a4);
        m_oA5 = (TextView)getActivity().findViewById(R.id.main_article_a5);
        
        requestAricle();
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
    public void requestAricle()
	{
		GregorianCalendar calendar = new GregorianCalendar();
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_ARTICLE);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("teamname",""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
    @Override
    public synchronized void onClick(View v) {
        switch (v.getId()) {
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        }
    }

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_ARTICLE"))
			{
				String img = object.getJSONObject("data").optString("img","");
				String title = object.getJSONObject("data").optString("title","");
				String date = object.getJSONObject("data").optString("date","");
				String subTitle = object.getJSONObject("data").optString("subTitle","");
				String introQ = object.getJSONObject("data").optString("introQ","");
				String introA = object.getJSONObject("data").optString("introA","");
				String q1 = object.getJSONObject("data").optString("q1","");
				String q2 = object.getJSONObject("data").optString("q2","");
				String q3 = object.getJSONObject("data").optString("q3","");
				String q4 = object.getJSONObject("data").optString("q4","");
				String q5 = object.getJSONObject("data").optString("q5","");
				String a1 = object.getJSONObject("data").optString("a1","");
				String a2 = object.getJSONObject("data").optString("a2","");
				String a3 = object.getJSONObject("data").optString("a3","");
				String a4 = object.getJSONObject("data").optString("a4","");
				String a5 = object.getJSONObject("data").optString("a5","");
				articleObject = new ArticleObject(img,title,date,subTitle,introQ,introA,q1,q2,q3,q4,q5,a1,a2,a3,a4,a5);
				setData();
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			
			MainActivity.getInstance().stopProgressDialog() ;
			
		}
	}
	public void setData()
	{
		if(articleObject!=null)
		{
		Drawable default1 = null;
    	default1 =  mContext.getResources().getDrawable(R.drawable.default_a);
    	m_oAsyncImageLoader.setImageDrawableAsync(m_oTitleImge,articleObject.getImg(),default1,default1,mContext);
    
    	 m_oTitle.setText(articleObject.getTitle());
         m_oTitleCalendar.setText(articleObject.getDate());
         m_oSubTitle.setText(articleObject.getSubTitle());
         m_oIntroQ.setText(articleObject.getIntroQ());
         m_oQ1.setText(articleObject.getQ1());
         m_oQ2.setText(articleObject.getQ2());
         m_oQ3.setText(articleObject.getQ3());
         m_oQ4.setText(articleObject.getQ4());
         m_oQ5.setText(articleObject.getQ5());
         m_oIntroA.setText(articleObject.getIntroA());
         m_oA1.setText(articleObject.getA1());
         m_oA2.setText(articleObject.getA2());
         m_oA3.setText(articleObject.getA3());
         m_oA4.setText(articleObject.getA4());
         m_oA5.setText(articleObject.getA5());
		}
	}
}
