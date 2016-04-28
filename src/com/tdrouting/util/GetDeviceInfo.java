package com.tdrouting.util;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
/*
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tdrouting.dto.Router;

public class GetDeviceInfo {
	
	public static List<Router> getDeviceInfo() throws Exception {
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/operational/network-topology:network-topology/topology/topology-netconf/";
		
        List<Router> routerList = new ArrayList<Router>();
        GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);
		try {
			int status = client.executeMethod(get);  
	       
            String stringEntity = get.getResponseBodyAsString();

            if (status == HttpStatus.SC_OK) {
                JSONObject topoList_object = new JSONObject(stringEntity);
                JSONArray topoList_array = topoList_object.getJSONArray("topology");
	            
                JSONObject topo_object = topoList_array.getJSONObject(0);
                JSONArray topo_array = topo_object.getJSONArray("node");
            	for (int j = 0; j < topo_array.length(); j++) {
            		Router router = new Router();
            		JSONObject node_object = topo_array.getJSONObject(j);
                	router.setName(node_object.getString("node-id"));
                	if (router.getName().equals("controller-config"))
                		continue;
                	router.setNetconfipv4(node_object.getString("netconf-node-topology:host"));
                	router.setNetconfport(node_object.getInt("netconf-node-topology:port"));
                	router.setNetconfstatus(node_object.getString("netconf-node-topology:connection-status"));
                	routerList.add(router);
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
	    } finally {
	    	get.releaseConnection();
	    }
		return routerList;
	}
}
