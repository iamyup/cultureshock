package com.cultureshock.buskingbook.page;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cultureshock.buskingbook.R;
import com.cultureshock.buskingbook.component.ViewPagerAdapter;
import com.cultureshock.buskingbook.main.JoinActivity;
import com.cultureshock.buskingbook.main.LeftMenuFragment;
import com.cultureshock.buskingbook.main.MainActivity;
import com.cultureshock.buskingbook.net.HttpClientNet;
import com.cultureshock.buskingbook.net.Params;
import com.cultureshock.buskingbook.object.LoginInfoObject;
import com.cultureshock.buskingbook.object.TeamMemberObject;
import com.cultureshock.buskingbook.object.TeamObject;
import com.cultureshock.buskingbook.service.ServiceType;
import com.cultureshock.buskingbook.util.Util;

public class BuskerJoinFragment extends Fragment implements View.OnClickListener, HttpClientNet.OnResponseListener{
    private static BuskerJoinFragment m_instance ;
    public static BuskerJoinFragment getInstace()
    {
    	return m_instance;
    }
	private FragmentActivity mContext;
    /*
     * version 1
     */
    private ImageView m_oBtnList;
    private ImageView m_oDoubleCheckImg;
    private RelativeLayout m_oLayout1;
    private RelativeLayout m_oBtnImg;
    public ImageView m_oImgVersion1;
    private EditText m_oEditTextTeamname;
    private EditText m_oEditTextTeamInfo;
    private EditText m_oEditTextGenre;
    private LinearLayout m_oBtnNext;
    private boolean checkDouble = false; 
    private String m_oTeamName;
    private String m_oTeamInfo;
    private String m_oGenre;
    private String m_oTeamImgStr;
    private String m_oTeamMember;
    public static final String TEMP_PHOTO_FILE = "tmp_team1.jpg";
    public static Uri m_oImageCropUri;
    public static final int REQ_PICK_IMAGE_BUSKER = 12341;
    /*
     * version 2
     */
    private RelativeLayout m_oLayout2;
    private ImageView m_oImgVersion2;
    private TextView m_oTxtTeamName;
    private TextView m_oTxtGenre;
    private TextView m_oTxtTeamInfo;
    private TextView m_oTxtTeamMember;
    private RelativeLayout m_oBtnTeamMemberAdd;
    private LinearLayout m_oBtnBefore;
    private LinearLayout m_oBtnConfirm;
    private EditText m_oEditTextTeamMember;
    private ArrayList<TeamMemberObject> teamMember = new ArrayList<TeamMemberObject>();
    private boolean checkNet = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.busker_join, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        m_instance = this;
        MainActivity.getInstance().offBottom();
        setUi();
    }

    public void setUi()
    {
    	m_oBtnList = (ImageView) getActivity()
				.findViewById(R.id.busker_title_btn_menu);
		m_oBtnList.setOnClickListener(this);
    	 m_oDoubleCheckImg = (ImageView)getActivity().findViewById(R.id.double_check_img);
    	 m_oLayout1 = (RelativeLayout)getActivity().findViewById(R.id.step_1);
    	 m_oLayout2 = (RelativeLayout)getActivity().findViewById(R.id.step_2);
    	 m_oBtnImg = (RelativeLayout)getActivity().findViewById(R.id.busker_join_img_add);
    	 m_oImgVersion1 = (ImageView) getActivity().findViewById(R.id.busker_join_img_imageview);
    	 m_oEditTextTeamname = (EditText)getActivity().findViewById(R.id.busker_join_name_text);
    	 m_oEditTextTeamname.setOnFocusChangeListener(new OnFocusChangeListener() {
 			
 			@Override
 			public void onFocusChange(View v, boolean hasFocus) {
 				// TODO Auto-generated method stub
 				if(hasFocus==false)
 				{
 					if((m_oEditTextTeamname.getText().toString().equals("")))
 					{
 						//아이디 입력해주세요
 						
 						Toast.makeText(mContext, "팀명을 입력해주세요", Toast.LENGTH_SHORT).show();
 					}
 					else
 					{
 						requestDoubleCheck();
 					}
 				}
 				
 			}
 		});
    	 m_oEditTextTeamInfo = (EditText)getActivity().findViewById(R.id.busker_join_info_text);
    	 m_oEditTextGenre = (EditText)getActivity().findViewById(R.id.busker_join_genre_text);
    	 m_oBtnNext = (LinearLayout)getActivity().findViewById(R.id.busker_btn_join);
    	 
    	 m_oImgVersion2 = (ImageView) getActivity().findViewById(R.id.busker_join_image_2);
    	 m_oTxtTeamName = (TextView)getActivity().findViewById(R.id.busker_join_teamname_2);
    	 m_oTxtGenre = (TextView)getActivity().findViewById(R.id.busker_join_genre_2);
    	 m_oTxtTeamInfo = (TextView)getActivity().findViewById(R.id.busker_join_info_2);
    	 m_oTxtTeamMember = (TextView)getActivity().findViewById(R.id.busker_join_teamname_2);
    	 m_oBtnTeamMemberAdd = (RelativeLayout)getActivity().findViewById(R.id.busker_join_teammember_add);
    	 m_oBtnBefore = (LinearLayout)getActivity().findViewById(R.id.busker_btn_before);
    	 m_oBtnConfirm=  (LinearLayout)getActivity().findViewById(R.id.busker_btn_add_confirm);
    	 m_oEditTextTeamMember = (EditText)getActivity().findViewById(R.id.busker_join_team_member);
    	 
    	 m_oBtnTeamMemberAdd.setOnClickListener(this);
    	 m_oBtnBefore.setOnClickListener(this);
    	 m_oBtnConfirm.setOnClickListener(this);
    	 m_oBtnImg.setOnClickListener(this);
    	 m_oBtnNext.setOnClickListener(this);
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
    public void setVersion2Data()
    {
    	m_oTxtTeamName.setText(m_oTeamName);
    	m_oTxtTeamInfo.setText(m_oTeamInfo);
    	m_oTxtGenre.setText(m_oGenre);
    	m_oEditTextTeamMember.setText(LoginInfoObject.getInstance().getName());
    }
    public void requestJoin()
	{
		//회원가입 요청
		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_JOIN);
		loginService.setCheck(true);
		ArrayList<Params> loginParams = new ArrayList<Params>();
		loginParams.add(new Params("id", LoginInfoObject.getInstance().getId()));
		loginParams.add(new Params("name", m_oTeamName));
		loginParams.add(new Params("mans", m_oTeamMember));
		loginParams.add(new Params("song", m_oGenre));
		loginParams.add(new Params("coment", m_oTeamInfo));
		
		if(m_oImageCropUri != null)
		{
			
			loginParams.add(new Params("thum", m_oImageCropUri.getPath()));
		}
		loginService.setParam(loginParams);
		loginService.doAsyncExecute(this);
		MainActivity.getInstance().startProgressDialog();
	}
    public void requestDoubleCheck()
   	{
   		//회원가입 요청
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_TEAM_JOIN_DOUBLE_ID);
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		loginParams.add(new Params("name",m_oEditTextTeamname.getText().toString() ));
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		MainActivity.getInstance().startProgressDialog();
   	}
    public void requestMyTeamUp()
   	{
   		//회원가입 요청
   		HttpClientNet loginService = new HttpClientNet(ServiceType.MSG_MYTEAM_UP);
   		
   		ArrayList<Params> loginParams = new ArrayList<Params>();
   		loginParams.add(new Params("name", LoginInfoObject.getInstance().getName()));
   		loginParams.add(new Params("myteam", m_oTeamName));
   		
   		loginService.setParam(loginParams);
   		loginService.doAsyncExecute(this);
   		MainActivity.getInstance().startProgressDialog();
   	}
    
    @Override
    public synchronized void onClick(View v) 
    {
        switch (v.getId()) 
        {
	        case R.id.busker_title_btn_menu: {
				MainActivity.getInstance().showMenu();
				break;
			}
	        case R.id.busker_join_img_add:
	        {
	        	
	        	Intent intent = new Intent(Intent.ACTION_PICK);
			    intent.setType("image/*");
			    intent.putExtra("crop", "true");
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, getTempUri());
			    intent.putExtra("output", m_oImageCropUri);
			    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
			    getActivity().startActivityForResult(intent, REQ_PICK_IMAGE_BUSKER);
	            break;
	        }
	        case R.id.busker_btn_join: 
	        {
	        	//버젼1 다음버튼
	        	if(!checkDouble)
				{
					Toast.makeText(mContext, "중복체크를 다시해주세요", Toast.LENGTH_SHORT).show();
					//중복체크해주세요
				}
	        	else if(m_oEditTextTeamInfo.getText().toString().equals(""))
	        	{
	        		Toast.makeText(mContext, "팀 소개글을 입력해주세요", Toast.LENGTH_SHORT).show();
	        	}
	        	else if(m_oEditTextGenre.getText().toString().equals(""))
	        	{
	        		Toast.makeText(mContext, " 장르를 입력해주세요", Toast.LENGTH_SHORT).show();
	        	}
	        	else
	        	{
	        		m_oTeamName = m_oEditTextTeamname.getText().toString();
	        		m_oTeamInfo = m_oEditTextTeamInfo.getText().toString();
	        		m_oGenre =m_oEditTextGenre.getText().toString();
	        		m_oTeamMember = LoginInfoObject.getInstance().getName();
//	        		m_oTeamMember = m_oEditTextTeamMember.getText().toString();
//		        	String member[] = m_oEditTextTeamMember.getText().toString().split(",");
//		        	for(int i = 0 ; i< member.length ; i++)
//		        	{
//		        		teamMember.add(new TeamMemberObject(member[i],""));
//		        	}
		        	requestJoin();
		            break;
//	        		m_oTeamName = m_oEditTextTeamname.getText().toString();
//		            m_oTeamInfo = m_oEditTextTeamInfo.getText().toString();
//		            m_oGenre = m_oEditTextGenre.getText().toString();
//		            // 이미지 주소 넣기 m_oTeamImgStr;
//		            m_oLayout1.setVisibility(View.GONE);
//		            m_oLayout2.setVisibility(View.VISIBLE);
//		            setVersion2Data();
	        	}
	        	
	            break;
	        }
	        case R.id.busker_join_teammember_add: 
	        {
	        	//버젼2 맴버추가
	            break;
	        }
	        case R.id.busker_btn_before: 
	        {
	        	//버젼2 이전버튼
	        	 m_oLayout1.setVisibility(View.VISIBLE);
		         m_oLayout2.setVisibility(View.GONE);
	            break;
	        }
	        case R.id.busker_btn_add_confirm: 
	        {
	        	//버젼2 확인
	        	m_oTeamMember = m_oEditTextTeamMember.getText().toString();
	        	String member[] = m_oEditTextTeamMember.getText().toString().split(",");
	        	for(int i = 0 ; i< member.length ; i++)
	        	{
	        		teamMember.add(new TeamMemberObject(member[i],""));
	        	}
	        	requestJoin();
	            break;
	        }
        }

    }

	@Override
	public void onResponseReceived(String resContent) {
		// TODO Auto-generated method stub
		try{
			Object o = resContent;
			JSONObject object = new JSONObject(resContent);
			if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_TEAM_JOIN_DOUBLE_ID"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					//중복되는아이디가 없을시
					m_oDoubleCheckImg.setBackgroundResource(R.drawable.check_double);
					Toast.makeText(mContext, "사용 가능한 팀명 입니다.", Toast.LENGTH_SHORT).show();
					checkDouble = true;
				}
				else
				{
					m_oDoubleCheckImg.setBackgroundResource(R.drawable.check_double_no);
					Toast.makeText(mContext, "중복된 팀명이 있습니다.", Toast.LENGTH_SHORT).show();
					checkDouble = false;
					//얼랏 중복되니 다시 입력바랍니다.
				}
			}
			
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG.TEAM_JOIN"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					//성공
					checkNet = true;
					requestMyTeamUp();
//					finish();
				}
				else
				{
					//실패
					Toast.makeText(mContext, "팀 등록에 실패하였습니다.", Toast.LENGTH_SHORT).show();
					MainActivity.getInstance().replaceFragment(MainHomeFragment.class, null, false);
					LeftMenuFragment.getInstance().loginSatatus();
//					finish();
				}
			}
			else if(object.getJSONObject("result").optString("type").equals("ServiceType.MSG_MYTEAM_UP"))
			{
				String result = object.getJSONObject("data").optString("result","");
				if(result.equals("true"))
				{
					checkNet = false;
					Toast.makeText(mContext, "팀 등록되었습니다.", Toast.LENGTH_SHORT).show();
					LoginInfoObject.getInstance().setMyteam(m_oTeamName);
					MainActivity.getTeamObject().add(new TeamObject(m_oTeamName,teamMember,m_oTeamMember,m_oGenre,m_oTeamInfo,m_oTeamImgStr));
					MainActivity.getInstance().onBackPressed();
					MainActivity.getInstance().checkDim();
					LeftMenuFragment.getInstance().loginSatatus();
					
				}
			}
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}
		finally
		{
			if(!checkNet)
			{
				MainActivity.getInstance().stopProgressDialog() ;
			}
		}

	}
	@Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        clearUiResource();
        if(getActivity() != null)
        {
	        Util.recursiveRecycle(((ViewGroup) getActivity().findViewById(R.id.content_frame)), false);
			Util.unbindDrawables(((ViewGroup) getActivity().findViewById(R.id.content_frame)));
	        ((ViewGroup) getActivity().findViewById(R.id.content_frame)).removeAllViews();
        }
		System.gc();
    }
    public void clearUiResource()
    {
    	m_oBtnList = null;
    	 m_oDoubleCheckImg  = null;
    	 m_oLayout1  = null;
    	 m_oLayout2  = null;
    	 m_oBtnImg  = null;
    	 m_oImgVersion1  = null;
    	 m_oEditTextTeamname  = null;
    	 
    	 m_oEditTextTeamInfo  = null;
    	 m_oEditTextGenre  = null;
    	 m_oBtnNext  = null;
    	 m_oImgVersion2  = null;
    	 m_oTxtTeamName = null;
    	 m_oTxtGenre  = null;
    	 m_oTxtTeamInfo  = null;
    	 m_oTxtTeamMember  = null;
    	 m_oBtnTeamMemberAdd  = null;
    	 m_oBtnBefore  = null;
    	 m_oBtnConfirm = null;
    	 m_oEditTextTeamMember = null;
    }
    public void imageSet(Bitmap bitmap)
    {
         m_oImgVersion1.setImageBitmap(bitmap);

    }
    private Uri getTempUri() {
        return Uri.fromFile(getProfileImage());
    }

    private File getProfileImage() {
        File file = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE);
        try {
            file.createNewFile();
        } catch (IOException e) {
            
        }
        return file;
    }

}
