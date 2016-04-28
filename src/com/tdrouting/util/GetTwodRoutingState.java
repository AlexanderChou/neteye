package com.tdrouting.util;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import com.tdrouting.dto.Ipv6tdrouting;

public class GetTwodRoutingState {
	public static List<Ipv6tdrouting> getTwodRoutingState(String routerName) throws Exception {
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing-state/";
	    List<Ipv6tdrouting> v6tdrouting = new ArrayList<Ipv6tdrouting>();
	    
	    HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);  
			String stringEntity = get.getResponseBodyAsString();

            if (status == 200) {
                JSONObject ipv6_twod_routing_state_object = new JSONObject(stringEntity);
                JSONObject ipv6_twod_routes_object = ipv6_twod_routing_state_object.getJSONObject("ipv6-twod-routing-state");	                
                
                JSONArray ipv6_twod_routes_array = ipv6_twod_routes_object.getJSONArray("twod-route");
               
                
                for (int i = 0; i < ipv6_twod_routes_array.length(); i++) 
                {
                	Ipv6tdrouting routing = new Ipv6tdrouting();
            		JSONObject ipv6_twod_route_object = ipv6_twod_routes_array.getJSONObject(i);
            		routing.setRoutername(routerName);
                	routing.setDstprefix(ipv6_twod_route_object.getString("destination-prefix"));
                	routing.setSrcprefix(ipv6_twod_route_object.getString("source-prefix"));
                	routing.setOutinterface(ipv6_twod_route_object.getString("outgoing-interface"));
                	routing.setFlag(ipv6_twod_route_object.getInt("flag"));
                	try {
                		routing.setNexthop(ipv6_twod_route_object.getString("next-hop-address"));
                	} catch(Exception e) {
                		routing.setNexthop("");
                	}
                	v6tdrouting.add(routing);
                }
            }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
	    } finally {
	        get.releaseConnection();
	    }
		return v6tdrouting;
	}
}