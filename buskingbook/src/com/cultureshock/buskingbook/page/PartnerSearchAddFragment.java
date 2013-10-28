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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.list.PartnerSearchListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.PartnerSearchObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.AsyncImageLoader;

public class PartnerSearchAddFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private static PartnerSearchAddFragment mInstance;
    private TextView m_oBtnTest;
    private ImageView m_oBtnList;
    private RelativeLayout m_oBtnMessage;
    private AsyncImageLoader m_oAsyncImageLoader = new AsyncImageLoader();
    
    private ImageView mImg;
	private TextView mTeamname;
	private EditText mComent;
	private LinearLayout btnGo;
    private ArrayList<PartnerSearchObject> partnerSearchObject= new ArrayList<PartnerSearchObject>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.partner_search_add, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        mInstance = this;

        m_oBtnTest = (TextView) getActivity().findViewById(R.id.test_main);
        m_oBtnTest.setOnClickListener(this);
        m_oBtnList = (ImageView) getActivity()
                .findViewById(R.id.title_btn_menu);
        m_oBtnList.setOnClickListener(this);
        m_oBtnMessage = (RelativeLayout)getActivity().findViewById(R.id.firend_search_top);
        m_oBtnMessage.setOnClickListener(this);
        mImg = (ImageView)getActivity().findViewById(R.id.search_partner_img);
		mTeamname = (TextView) getActivity().findViewById(R.id.search_partner_text_name_teamname);
		mComent = (EditText) getActivity().findViewById(R.id.partner_add_edittext);
		mComent.setSelection(0);
		btnGo = (LinearLayout)getActivity().findViewById(R.id.btn_partner_search_add);
        btnGo.setOnClickListener(this);
        Drawable default1 = null;
        default1 =  mContext.getResources().getDrawable(R.drawable.default_pf);
    	m_oAsyncImageLoader.setImageDrawableAsync(mImg,LoginInfoObject.getInstance().getMyImg(),default1,default1,mContext);
    	mTeamname.setText(LoginInfoObject.getInstance().getName());
    }

    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
    }
   
    public static PartnerSearchAddFragment getInstance() {
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
        case R.id.firend_search_top:
        	break;
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        case R.id.btn_partner_search_add:
        	if(mComent.getText().toString().equals(""))
        	{
        		Toast.makeText(mContext, "글을 써주세요", Toast.LENGTH_SHORT).show();
        	}
        	else
        	{
        		requestAdd();
        	}
        	break;
        }
    }
    public void requestAdd()
	{
    	GregorianCalendar o = new GregorianCalendar();
    	String ampm=null;
    	if(o.get(Calendar.AM_PM)==1)
    	{
    		ampm="pm";
    	}
    	else
    	{
    		ampm="am";
    	}
    	String date = ""+ o.get(Calendar.YEAR) +". "+ (o.get(Calendar.MONTH)+1)+". " + o.get(Calendar.DATE)
    	+" "+ o.get(Calendar.HOUR) +":"+ o.get(Calendar.MINUTE)+ampm; 
    	
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_PARTNER_SEARCH_ADD);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("name", LoginInfoObject.getInstance().getName()));
		loginParams.add(new Params("teamname", LoginInfoObject.getInstance().getMyteam()));
		loginParams.add(new Params("coment", mComent.getText().toString()));
		loginParams.add(new Params("img", LoginInfoObject.getInstance().getMyImg()));
		loginParams.add(new Params("times", date));
		loginParams.add(new Params("regId", ""));
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			GregorianCalendar calendar = new GregorianCalendar();
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_PARTNER_SEARCH_ADD"))
			{
				Toast.makeText(mContext, "등록되었습니다!", Toast.LENGTH_SHORT).show();
				MainActivity.getInstance().replaceFragment(PartnerSearchFragment.class, null, false);
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
}
