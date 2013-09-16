package com.cultureshock.buskingbook.net;

public class Params {

    private String id;
    private String value;

    public Params() {

    }

    public Params(String id) {
        this.id = id;
        this.value = "";
    }

    public Params(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
