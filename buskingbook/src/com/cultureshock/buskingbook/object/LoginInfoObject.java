package com.cultureshock.buskingbook.object;

import java.util.ArrayList;


public class LoginInfoObject {
	private String id;
	private String pwd;
	private String name;
	private String phone;
	private String myImg;
	private String myteam;
	private ArrayList<String> likeTeamList = new ArrayList<String>() ;
	
	private boolean checkLogin = false;
	private static LoginInfoObject poLoginThis = new LoginInfoObject();
	public static LoginInfoObject getInstance()
	{
		return poLoginThis;
	}
	public LoginInfoObject()
	{
		
	}
	public LoginInfoObject(String id, String pwd)
	{
		this.id = id;
		this.pwd = pwd;
	}
	public LoginInfoObject(String id, String pwd, String name,String phone)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
	}
	public LoginInfoObject(String id, String pwd, String name ,String phone, String myImg)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
	}
	public LoginInfoObject(String id, String pwd, String name, String phone , String myImg, ArrayList<String> likeTeamList)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
		this.likeTeamList = likeTeamList;
	}
	public LoginInfoObject(String id, String pwd, String name, String phone , String myImg, ArrayList<String> likeTeamList,String myTeam)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
		this.likeTeamList = likeTeamList;
		this.myteam = myTeam;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMyImg() {
		return myImg;
	}
	public void setMyImg(String myImg) {
		this.myImg = myImg;
	}
	public ArrayList<String> getLikeTeamList() {
		return likeTeamList;
	}
	public void setLikeTeamList(ArrayList<String> likeTeamList) {
		this.likeTeamList = likeTeamList;
	}
	public String getMyteam() {
		return myteam;
	}
	public void setMyteam(String myteam) {
		this.myteam = myteam;
	}
	public boolean isLogin()
	{
		if(this.id == null || this.id.equals(""))
		{
			checkLogin = false;
		}
		else 
		{
			checkLogin = true;
		}
		return checkLogin;
	}

	public void setLogin(String id,String pwd,String name)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		checkLogin = true;
	}
	public void setLogin(String id,String pwd,String name, String phone)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		checkLogin = true;
	}
	public void setLogin(String id,String pwd,String name, String phone, String myImg)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
		checkLogin = true;
	}
	public void setLogin(String id,String pwd,String name, String phone, String myImg, ArrayList<String> likeTeamList)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
		this.likeTeamList = likeTeamList;
		checkLogin = true;
	}
	public void setLogin(String id,String pwd,String name, String phone, String myImg, ArrayList<String> likeTeamList,String myTeam)
	{
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.phone = phone;
		this.myImg = myImg;
		this.likeTeamList = likeTeamList;
		this.myteam = myTeam;
		checkLogin = true;
	}
	public void setLogout()
	{
		this.id = "";
		this.pwd = "";
		this.name = "";
		this.phone = "";
		this.myImg = "";
		this.likeTeamList.clear();
		checkLogin = false;
	}
	
}
