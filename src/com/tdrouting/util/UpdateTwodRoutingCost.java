package com.tdrouting.util;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//import net.sf.json.JSONObject;

public class UpdateTwodRoutingCost {
	@SuppressWarnings("deprecation")
	public static void updateTwodRoutingCost(String routerName, String destination_prefix, String source_prefix, int cost) 
	{
		JSONObject ipv6_twod_routing_object = new JSONObject();
		JSONArray twod_lists_array = new JSONArray();	
    	JSONObject ipv6_route_object = new JSONObject();
    	try {
			ipv6_route_object.put("destination-prefix", destination_prefix);
			ipv6_route_object.put("source-prefix", source_prefix);
	    	ipv6_route_object.put("cost", cost);
	    	twod_lists_array.put(ipv6_route_object);
	        ipv6_twod_routing_object.put("twod-list", twod_lists_array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	destination_prefix = destination_prefix.replace("/", "%2F");
		source_prefix = source_prefix.replace("/", "%2F");
		
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/twod-list/"
		+ destination_prefix + "/" + source_prefix;
    	
		PutMethod put = new PutMethod(url);
		put.setDoAuthentication(true);
		put.addRequestHeader("Content-Type","application/json");
		put.setRequestBody(ipv6_twod_routing_object.toString());
		try {
			client.executeMethod(put);
			System.out.println(put.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		put.releaseConnection();
	}
}