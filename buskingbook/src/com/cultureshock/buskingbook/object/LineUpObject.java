package com.cultureshock.buskingbook.object;

public class LineUpObject {
	private String year; 
	private String month; 
	private String day; 
	private String time; 
	private String dayOfweek; 
	private String place;
	private String teamName; 
	private String joinCount;
	private String ranking;
	private String imgAddress;
	public LineUpObject()
	{
		
	}
	public LineUpObject(String year, String month, String day, String time,
			String dayOfweek, String place, String teamName) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
		this.dayOfweek = dayOfweek;
		this.place = place;
		this.teamName = teamName;
	}
	public LineUpObject(String year, String month, String day, String time,
			String dayOfweek, String place, String teamName, String joinCount) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
		this.dayOfweek = dayOfweek;
		this.place = place;
		this.teamName = teamName;
		this.joinCount = joinCount;
	}
	
	public LineUpObject(String year, String month, String day, String time,
			String dayOfweek, String place, String teamName, String joinCount,
			String ranking) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
		this.dayOfweek = dayOfweek;
		this.place = place;
		this.teamName = teamName;
		this.joinCount = joinCount;
		this.ranking = ranking;
	}
	
	public LineUpObject(String year, String month, String day, String time,
			String dayOfweek, String place, String teamName, String joinCount,
			String ranking, String imgAddress) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.time = time;
		this.dayOfweek = dayOfweek;
		this.place = place;
		this.teamName = teamName;
		this.joinCount = joinCount;
		this.ranking = ranking;
		this.imgAddress = imgAddress;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDayOfweek() {
		return dayOfweek;
	}
	public void setDayOfweek(String dayOfweek) {
		this.dayOfweek = dayOfweek;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getJoinCount() {
		return joinCount;
	}
	public void setJoinCount(String joinCount) {
		this.joinCount = joinCount;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	public String getImgAddress() {
		return imgAddress;
	}
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}
	

}
