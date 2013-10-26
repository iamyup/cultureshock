package com.cultureshock.buskingbook.object;

import java.util.ArrayList;


public class PartnerSearchObject {
	
	private String id;
	private String name;
	private String teamName;
	private String coment;
	private String img;
	private String date;
	private String regId;
	public PartnerSearchObject()
	{
		
	}
	public PartnerSearchObject(String id, String name, String teamName,
			String coment, String img, String date, String regId) {
		super();
		this.id = id;
		this.name = name;
		this.teamName = teamName;
		this.coment = coment;
		this.img = img;
		this.date = date;
		this.regId = regId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	
}
