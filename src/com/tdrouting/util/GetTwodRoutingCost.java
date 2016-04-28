package com.tdrouting.util;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tdrouting.dto.Ipv6tdrouting;
import com.tdrouting.dto.Tdroutingcost;

public class GetTwodRoutingCost {
	public static List<Tdroutingcost> getTwodRoutingCost(String routerName) {
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/";
	    List<Tdroutingcost> costList = new ArrayList<Tdroutingcost>();
	    
	    HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);

        int status = 404;
		try {
			status = client.executeMethod(get);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (status == HttpStatus.SC_OK) {
        	String stringEntity = "";
			try {
				stringEntity = get.getResponseBodyAsString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                JSONObject json_object;
				try {
					json_object = new JSONObject(stringEntity);
					JSONObject ipv6_twod_routing_object = json_object.getJSONObject("ipv6-twod-routing");
					JSONArray twod_lists_array = ipv6_twod_routing_object.getJSONArray("twod-list");
	                for (int i = 0; i < twod_lists_array.length(); i++) 
	                {
	                	Tdroutingcost routingCost = new Tdroutingcost();
	            		JSONObject ipv6_route_object = twod_lists_array.getJSONObject(i);
	            		routingCost.setDstprefix(ipv6_route_object.getString("destination-prefix"));
	            		routingCost.setSrcprefix(ipv6_route_object.getString("source-prefix"));
	                	routingCost.setCost(ipv6_route_object.getInt("cost"));
	                	routingCost.setRoutername(routerName);
	                	costList.add(routingCost);
	                }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
            else {
            	try {
					System.out.println(get.getResponseBodyAsString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        get.releaseConnection();
        System.out.println(routerName+":"+costList.size());
		return costList;
	}
}