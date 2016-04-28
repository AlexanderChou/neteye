package com.tdrouting.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONException;
import org.json.JSONObject;
//import net.sf.json.JSONObject;

public class SetSupportTwodRoute {
	@SuppressWarnings("deprecation")
	public static void setSupportTwodRoute(String routerName){
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/";

		PutMethod put = new PutMethod(url);
		put.addRequestHeader("Content-Type","application/json");
		
		JSONObject ipv6_twod_routing_object_set = new JSONObject();
    	try {
			ipv6_twod_routing_object_set.put("enabled", true);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	JSONObject ipv6_twod_routing_object = new JSONObject();
    	try {
			ipv6_twod_routing_object.put("ipv6-twod-routing", ipv6_twod_routing_object_set);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		put.setRequestBody(ipv6_twod_routing_object.toString());
        put.setDoAuthentication(true);

        int status = 404;
        try {
        	status = client.executeMethod(put);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(status);
			e.printStackTrace();
		} finally {
			put.releaseConnection();
		}
	}
}
