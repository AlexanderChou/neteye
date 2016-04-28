package com.tdrouting.util;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AddTwodRoutingCost {
	@SuppressWarnings("deprecation")
	public static void addTwodRoutingCost(String routerName, String destination_prefix, String source_prefix, int cost) {
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/";
		
		PostMethod post = new PostMethod(url);

		
		JSONObject ipv6_twod_routing_object = new JSONObject();	
    	JSONArray twod_lists_array = new JSONArray();
    	JSONObject ipv6_route_object = new JSONObject();
    	try {
			ipv6_route_object.put("destination-prefix", destination_prefix);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			ipv6_route_object.put("source-prefix", source_prefix);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	try {
			ipv6_route_object.put("cost", cost);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	twod_lists_array.put(ipv6_route_object);
        try {
			ipv6_twod_routing_object.put("twod-list", twod_lists_array);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		post.addRequestHeader("Content-Type","application/json");
        post.setRequestBody(ipv6_twod_routing_object.toString());
        post.setDoAuthentication(true);

        try {
			client.executeMethod(post);
			System.out.println(post.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        post.releaseConnection();	           
	}
}
