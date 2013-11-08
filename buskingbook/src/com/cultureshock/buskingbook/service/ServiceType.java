package com.cultureshock.buskingbook.service;

import java.util.Hashtable;

public class ServiceType {

	public static final String ROOT_DOMAIN = "http://kangshinhyun.cafe24.com/";

    public static final int MSG_LOGIN = 1;
    public static final int MSG_TOP12 = 2; // 안씀
    public static final int MSG_TIME_TABLE = 3;
    public static final int MSG_TIME_TABLE_SEND = 4;
    public static final int MSG_JOIN = 5;
    public static final int MSG_JOIN_DOUBLE_ID = 6;
    public static final int MSG_TEAM_JOIN = 7;
    public static final int MSG_TEAM_JOIN_DOUBLE_ID = 8;
    public static final int MSG_TEAM_SELECT = 9;
    public static final int MSG_TEAM_LIKE_UP = 10;
    public static final int MSG_MYTEAM_UP = 11;
    public static final int MSG_GCM_ID_JOIN = 12;
    public static final int MSG_GCM_GO_EVENT= 13;
    public static final int MSG_ARTICLE= 14;
    public static final int MSG_PARTNER_SEARCH= 15;
    public static final int MSG_PARTNER_SEARCH_ADD= 16;
    public static final int MSG_MESSAGE_ADD= 17;
    public static final int MSG_MESSAGE_READ= 18;
    public static final int MSG_MESSAGE_DELETE= 19;
    public static final int MSG_TEAM_DATA_CHANGE= 20;
    public static final int MSG_GCM_GO_MESSAGE= 21;

    private static final String URL_LOGIN 				 = 	 ROOT_DOMAIN + "login.php";
    private static final String URL_TOP12				 =	 ROOT_DOMAIN + "top12.php";
    private static final String URL_TIME_TABLE		  	 =	 ROOT_DOMAIN + "time_table.php";
    private static final String URL_TIME_TABLE_SNED 	 =	 ROOT_DOMAIN + "time_table_send.php";
    private static final String URL_JOIN 				 =   ROOT_DOMAIN + "join.php";
    private static final String URL_JOIN_DOUBLE_ID 		 =   ROOT_DOMAIN + "join_double_id.php";
    private static final String URL_TEAM_JOIN 			 =	 ROOT_DOMAIN + "team_join.php";
    private static final String URL_TEAM_JOIN_DOUBLE_ID  = 	 ROOT_DOMAIN + "team_join_double_id.php";
    private static final String URL_TEAM_SELECT 		 =	 ROOT_DOMAIN + "team_select.php";
    private static final String URL_TEAM_LIKE_UP 		 = 	 ROOT_DOMAIN + "team_like_up.php";
    private static final String URL_MYTEAM_UP 			 = 	 ROOT_DOMAIN + "myteam_up.php";
    private static final String URL_GCM_ID_JOIN 		 = 	 ROOT_DOMAIN + "gcm_id_join.php";
    private static final String URL_GCM_GO_EVNET		 = 	 ROOT_DOMAIN + "gcm_go_event.php";
    private static final String URL_ARTICLE				 = 	 ROOT_DOMAIN + "article.php";
    private static final String URL_PARTNER_SEARCH	     = 	 ROOT_DOMAIN + "partner_search.php";
    private static final String URL_PARTNER_SEARCH_ADD	 = 	 ROOT_DOMAIN + "partner_search_add.php";
    private static final String URL_MESSAGE_ADD		 	 = 	 ROOT_DOMAIN + "message_add.php";
    private static final String URL_MESSAGE_READ		 = 	 ROOT_DOMAIN + "message_read.php";
    private static final String URL_MESSAGE_DELETE		 = 	 ROOT_DOMAIN + "message_delete.php";
    private static final String URL_TEAM_DATA_CHANGE	 = 	 ROOT_DOMAIN + "team_data_change.php";
    private static final String URL_GCM_GO_MESSAGE		 = 	 ROOT_DOMAIN + "gcm_go_message.php";

    private Hashtable<Integer, String> msgURLTbl = new Hashtable<Integer, String>();

    private static ServiceType msgState = new ServiceType();

    private ServiceType() {
        msgURLTbl.put(new Integer(MSG_LOGIN), URL_LOGIN);
        msgURLTbl.put(new Integer(MSG_TOP12), URL_TOP12);
        msgURLTbl.put(new Integer(MSG_TIME_TABLE), URL_TIME_TABLE);
        msgURLTbl.put(new Integer(MSG_TIME_TABLE_SEND), URL_TIME_TABLE_SNED);
        msgURLTbl.put(new Integer(MSG_JOIN), URL_JOIN);
        msgURLTbl.put(new Integer(MSG_JOIN_DOUBLE_ID), URL_JOIN_DOUBLE_ID);
        msgURLTbl.put(new Integer(MSG_TEAM_JOIN), URL_TEAM_JOIN);
        msgURLTbl.put(new Integer(MSG_TEAM_JOIN_DOUBLE_ID), URL_TEAM_JOIN_DOUBLE_ID);
        msgURLTbl.put(new Integer(MSG_TEAM_SELECT), URL_TEAM_SELECT);
        msgURLTbl.put(new Integer(MSG_TEAM_LIKE_UP), URL_TEAM_LIKE_UP);
        msgURLTbl.put(new Integer(MSG_MYTEAM_UP), URL_MYTEAM_UP);
        msgURLTbl.put(new Integer(MSG_GCM_ID_JOIN), URL_GCM_ID_JOIN);
        msgURLTbl.put(new Integer(MSG_GCM_GO_EVENT), URL_GCM_GO_EVNET);
        msgURLTbl.put(new Integer(MSG_ARTICLE), URL_ARTICLE);
        msgURLTbl.put(new Integer(MSG_PARTNER_SEARCH), URL_PARTNER_SEARCH);
        msgURLTbl.put(new Integer(MSG_PARTNER_SEARCH_ADD), URL_PARTNER_SEARCH_ADD);
        msgURLTbl.put(new Integer(MSG_MESSAGE_ADD), URL_MESSAGE_ADD);
        msgURLTbl.put(new Integer(MSG_MESSAGE_READ), URL_MESSAGE_READ);
        msgURLTbl.put(new Integer(MSG_MESSAGE_DELETE), URL_MESSAGE_DELETE);
        msgURLTbl.put(new Integer(MSG_TEAM_DATA_CHANGE), URL_TEAM_DATA_CHANGE);
        msgURLTbl.put(new Integer(MSG_GCM_GO_MESSAGE), URL_GCM_GO_MESSAGE);
    }

    public static ServiceType getInstance() {
        return msgState;
    }

    public String getUrl(int serviceType) {
        return msgURLTbl.get(serviceType);
    }
}
