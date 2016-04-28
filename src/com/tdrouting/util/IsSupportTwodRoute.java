package com.tdrouting.util;


import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONException;
import org.json.JSONObject;

public class IsSupportTwodRoute {
	public static boolean isSupportTwodRoute(String routerName) {
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));

		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/";
		GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);

		int status = 404;
		try {
			status = client.executeMethod(get);
		} catch (HttpException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
	       
        String stringEntity = "";
		try {
			stringEntity = get.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if (status == HttpStatus.SC_OK) {
        	JSONObject entityJObj = null;
			try {
				entityJObj = new JSONObject(stringEntity);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            JSONObject twodRouteStateListJObj = null;
			try {
				twodRouteStateListJObj = entityJObj.getJSONObject("ipv6-twod-routing");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
            
            get.releaseConnection();
            try {
				return twodRouteStateListJObj.getBoolean("enabled");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } 
        get.releaseConnection();
	    return false;   
	}
}