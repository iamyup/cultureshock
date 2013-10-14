
package com.cultureshock.buskingbook.framework;

import java.util.ArrayList;

import android.os.Bundle;

import com.cultureshock.buskingbook.main.BuskingMainActivity;
import com.cultureshock.buskingbook.object.TeamObject;


public class BaseActivity extends BuskingMainActivity {
	
	private static ArrayList<TeamObject> teamObject = new ArrayList<TeamObject>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

	public static ArrayList<TeamObject> getTeamObject() {
		return teamObject;
	}

	public static void setTeamObject(ArrayList<TeamObject> teamObject) {
		BaseActivity.teamObject = teamObject;
	}
	public static TeamObject searchTeam(String teamName)
	{
		for(int i = 0 ; i < teamObject.size() ; i++)
		{
			if(teamObject.get(i).getTeamName().equals(teamName))
			{
				return teamObject.get(i);
			}
		}
		return null;
	}
    	
  
}
