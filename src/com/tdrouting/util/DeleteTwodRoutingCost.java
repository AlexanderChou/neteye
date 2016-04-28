package com.tdrouting.util;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.json.JSONArray;
import org.json.JSONObject;

public class DeleteTwodRoutingCost {
	public static void deleteTwodRoutingCost(String routerName, String destination_prefix, String source_prefix )
	{
		destination_prefix = destination_prefix.replace("/", "%2F");
		source_prefix = source_prefix.replace("/", "%2F");
		
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routerName+"/yang-ext:mount/ietf-ipv6-twod-routing:ipv6-twod-routing/twod-list/"
		+ destination_prefix + "/" + source_prefix + "/";

		DeleteMethod del = new DeleteMethod(url);
		del.setDoAuthentication(true);
		try {
			client.executeMethod(del);
			System.out.println(del.getResponseBodyAsString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		del.releaseConnection();
	}
}