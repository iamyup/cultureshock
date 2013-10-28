package com.cultureshock.buskingbook.object;

import java.util.ArrayList;


public class MessageObject {
	private String id;
	private String name;
	private String sendid;
	private String sendname;
	private String sendphone;
	private String coment;
	private String times;
	public MessageObject()
	{
		
	}
	public MessageObject(String id, String name, String sendid,
			String sendname, String sendphone, String coment) {
		super();
		this.id = id;
		this.name = name;
		this.sendid = sendid;
		this.sendname = sendname;
		this.sendphone = sendphone;
		this.coment = coment;
	}
	
	public MessageObject(String id, String name, String sendid,
			String sendname, String sendphone, String coment, String times) {
		super();
		this.id = id;
		this.name = name;
		this.sendid = sendid;
		this.sendname = sendname;
		this.sendphone = sendphone;
		this.coment = coment;
		this.times = times;
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
	public String getSendid() {
		return sendid;
	}
	public void setSendid(String sendid) {
		this.sendid = sendid;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getSendphone() {
		return sendphone;
	}
	public void setSendphone(String sendphone) {
		this.sendphone = sendphone;
	}
	public String getComent() {
		return coment;
	}
	public void setComent(String coment) {
		this.coment = coment;
	}
	public String getTimes() {
		return times;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	
	
}
