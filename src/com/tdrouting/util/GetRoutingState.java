package com.tdrouting.util;
import java.util.List;
import java.util.ArrayList;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tdrouting.dto.Ipv6routing;

public class GetRoutingState {
	public static List<Ipv6routing> getRoutingState(String routername) throws Exception {
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routername+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-routing-state/";
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
	    List<Ipv6routing> routingTable = new ArrayList<Ipv6routing>();
	    
	    GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);
		try {    	
			int status = client.executeMethod(get);    
            String stringEntity = get.getResponseBodyAsString();

            if (status == 200) {
                JSONObject ipv6_routing_state_object = new JSONObject(stringEntity);
                JSONObject ipv6_routes_object = ipv6_routing_state_object.getJSONObject("ipv6-routing-state");	                
                
                JSONArray ipv6_routes_array = ipv6_routes_object.getJSONArray("ipv6-route");
                for (int i = 0; i < ipv6_routes_array.length(); i++) {
            		JSONObject ipv6_route_object = ipv6_routes_array.getJSONObject(i);
            		Ipv6routing v6routing = new Ipv6routing();
            		v6routing.setRoutername(routername);
                	v6routing.setDstprefix(ipv6_route_object.getString("destination-prefix"));
                	v6routing.setOutinterface(ipv6_route_object.getString("outgoing-interface"));
                	try {
                		v6routing.setNexthop(ipv6_route_object.getString("next-hop-address"));
                	} catch(Exception e) {
                		v6routing.setNexthop("");
                	}
                	v6routing.setFlag(ipv6_route_object.getInt("flag"));
                	v6routing.setRoutername(routername);
                	routingTable.add(v6routing);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
	    } finally {
	        get.releaseConnection();
	    }
		return routingTable;
	}
}