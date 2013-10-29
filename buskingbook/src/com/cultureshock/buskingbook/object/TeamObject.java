package com.cultureshock.buskingbook.object;

import java.util.ArrayList;

public class TeamObject {
	private String teamName;
	private ArrayList<TeamMemberObject> teamMember;
	private String teamThum; // ???미�? 주�?
	private int likeCount; // �?????��?
	private String teamComent;
	private String teamSong;
	private String teamMembers;
	private ArrayList<String> likeMans;

	public TeamObject() {

	}

	public TeamObject(String teamName) {
		super();
		this.teamName = teamName;
		setList();
	}

	public TeamObject(String teamName, String teamMembers) {
		this.teamName = teamName;
		this.teamMembers = teamMembers;
		setList();
	}

	public TeamObject(String teamName, String teamMembers, String teamSong,
			String teamComent, String teamThum) {
		super();
		this.teamName = teamName;
		this.teamMembers = teamMembers;
		this.teamThum = teamThum;
		this.likeCount = 0;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamMembers, String teamSong, String teamComent,
			String teamThum) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamMembers = teamMembers;
		this.teamThum = teamThum;
		this.likeCount = 0;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, String teamMembers, String teamThum,
			int likeCount, String teamComent, String teamSong) {
		super();
		this.teamName = teamName;
		this.teamMembers = teamMembers;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, String teamMembers, String teamThum,
			int likeCount, String teamComent, String teamSong,
			ArrayList<String> likeMans) {
		super();
		this.teamName = teamName;
		this.teamMembers = teamMembers;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		this.likeMans = likeMans;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum, String teamSong) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum, int likeCount) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum, String teamSong, int likeCount) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, String teamThum, int likeCount,
			String teamComent) {
		super();
		this.teamName = teamName;
		this.teamMember = new ArrayList<TeamMemberObject>();
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		setList();
	}

	public TeamObject(String teamName, String teamThum, int likeCount,
			String teamComent, String teamSong) {
		super();
		this.teamName = teamName;
		this.teamMember = new ArrayList<TeamMemberObject>();
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum, int likeCount, String teamComent) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		setList();
	}

	public TeamObject(String teamName, ArrayList<TeamMemberObject> teamMember,
			String teamThum, int likeCount, String teamComent, String teamSong) {
		super();
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.teamThum = teamThum;
		this.likeCount = likeCount;
		this.teamComent = teamComent;
		this.teamSong = teamSong;
		setList();
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public ArrayList<TeamMemberObject> getTeamMember() {
		return teamMember;
	}

	public void setTeamMember(ArrayList<TeamMemberObject> teamMember) {
		this.teamMember = teamMember;
	}

	public String getTeamThum() {
		return teamThum;
	}

	public void setTeamThum(String teamThum) {
		this.teamThum = teamThum;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public String getTeamComent() {
		return teamComent;
	}

	public void setTeamComent(String teamComent) {
		this.teamComent = teamComent;
	}

	public String getTeamSong() {
		return teamSong;
	}

	public void setTeamSong(String teamSong) {
		this.teamSong = teamSong;
	}

	public String getTeamMembers() {
		return teamMembers;
	}

	public void setTeamMembers(String teamMembers) {
		this.teamMembers = teamMembers;
	}

	public ArrayList<String> getLikeMans() {
		return likeMans;
	}

	public void setLikeMans(ArrayList<String> likeMans) {
		this.likeMans = likeMans;
	}

	public void setList() {
		if (teamMember == null) {
			teamMember = new ArrayList<TeamMemberObject>();
		}
		if (likeMans == null) {
			likeMans = new ArrayList<String>();
		}
	}

}
