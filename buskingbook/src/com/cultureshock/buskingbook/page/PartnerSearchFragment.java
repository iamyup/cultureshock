package com.cultureshock.buskingbook.page;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.json.JSONArray;
import org.json.JSONObject;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ReadMessagePopup;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.list.LineUpListView;
import com.cultureshock.buskingbook.list.PartnerSearchListView;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LineUpObject;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.MessageObject;
import com.cultureshock.buskingbook.object.PartnerSearchObject;
import com.cultureshock.buskingbook.service.ServiceType;

public class PartnerSearchFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private FragmentActivity mContext;
    private static PartnerSearchFragment mInstance;
    private TextView m_oBtnTest;
    private ImageView m_oBtnList;
    private ImageButton m_oBtnWriter;
    private RelativeLayout m_oBtnMessage;
    private TextView m_oTextMessageNew;
    private LinearLayout m_oLayoutList;
    private PartnerSearchListView m_ListSearch;
    private ArrayList<PartnerSearchObject> partnerSearchObject= new ArrayList<PartnerSearchObject>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.partner_search, container, false);
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
        m_oBtnWriter = (ImageButton) getActivity()
                .findViewById(R.id.write_search);
        m_oBtnWriter.setOnClickListener(this);
        m_oBtnMessage = (RelativeLayout)getActivity().findViewById(R.id.firend_search_top);
        m_oBtnMessage.setOnClickListener(this);
        m_oTextMessageNew = (TextView)getActivity().findViewById(R.id.top_message_new_o);
        m_oLayoutList= (LinearLayout)getActivity().findViewById(R.id.firend_search_body);
      
        requestData();
    }

    public void setTitle(String txt)
    {
    	m_oBtnTest.setText(txt);
    }
   
    public static PartnerSearchFragment getInstance() {
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
        	requestReadMessage();
        	break;
        case R.id.title_btn_menu:
            MainActivity.getInstance().showMenu();
            break;
        case R.id.write_search:
        	MainActivity.getInstance().replaceFragment(PartnerSearchAddFragment.class, null, false);
//            MainActivity.getInstance().replaceFragment(PaperEditFragment.class, null, false);
            break;
        }
    }
    public void requestData()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_PARTNER_SEARCH);
		
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
    public void requestReadMessage()
	{
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MESSAGE_READ);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", LoginInfoObject.getInstance().getId()));
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
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_PARTNER_SEARCH"))
			{
				JSONArray todayTime = object.getJSONArray("data");
				for(int i=0; i<todayTime.length(); i++)
				{
					JSONObject jsonObject = todayTime.getJSONObject(i);
					String id = jsonObject.optString("id");
					String name = jsonObject.optString("name");
					String teamname = jsonObject.optString("teamname");
					String coment = jsonObject.optString("coment");
					String img = jsonObject.optString("img");
					String date = jsonObject.optString("times");
					String regId = jsonObject.optString("regId");
					partnerSearchObject.add(new PartnerSearchObject(id,name,teamname,coment,img,date,regId));
					
					
				}
				if(m_ListSearch == null)
		        {
		        	m_ListSearch = new PartnerSearchListView(mContext);
		        }
		        m_ListSearch.setListData(partnerSearchObject);
		        m_oLayoutList.addView(m_ListSearch);
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MESSGAE_READ"))
			{
				try{
				JSONArray todayTime = object.getJSONArray("data");
				ArrayList<MessageObject> k = new ArrayList<MessageObject>();
				for(int i=0; i<todayTime.length(); i++)
				{
					JSONObject jsonObject = todayTime.getJSONObject(i);
					String id = jsonObject.optString("id");
					String name = jsonObject.optString("name");
					String sendname = jsonObject.optString("sendname");
					String sendid = jsonObject.optString("sendid");
					String sendphone = jsonObject.optString("sendphone");
					String coment = jsonObject.optString("coment");
					String times = jsonObject.optString("times");
					k.add(new MessageObject(id,name,sendid,sendname,sendphone,coment,times));
				}
				new ReadMessagePopup(mContext, k);
				}
				catch(Exception e)
				{
					Toast.makeText(mContext, "메세지가 없습니다", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
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
