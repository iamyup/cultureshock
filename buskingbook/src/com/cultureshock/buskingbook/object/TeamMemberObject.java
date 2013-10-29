package com.cultureshock.buskingbook.object;

public class TeamMemberObject {
	private String name;
	private String instrument;

	public TeamMemberObject() {

	}

	public TeamMemberObject(String name) {
		this.name = name;
	}

	public TeamMemberObject(String name, String instrument) {
		super();
		this.name = name;
		this.instrument = instrument;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstrument() {
		return instrument;
	}

	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}

}
