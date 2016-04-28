package com.tdrouting.util;

import java.util.List;
import java.util.ArrayList;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import com.tdrouting.dto.Interface;

public class GetIetfInterfaceInfo {
	public static List<Interface> getIetfInterfaceInfo(String routername) throws Exception {
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/network-topology:network-topology/topology/topology-netconf/node/"+routername+"/yang-ext:mount/ietf-interfaces:interfaces/";
		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
	    List<Interface> interfaceList = new ArrayList<Interface>();
		
	    GetMethod get = new GetMethod(url);  
        get.setDoAuthentication(true);
	    try {
	    	int status = client.executeMethod(get);  		       
            String stringEntity = get.getResponseBodyAsString();
            if (status == 200)
            {
                JSONObject jsonObj = new JSONObject(stringEntity);
                JSONObject ietfInterfacesObj = jsonObj.getJSONObject("interfaces");	                
                JSONArray ietfInterfacesArray = ietfInterfacesObj.getJSONArray("interface");

                for (int i = 0; i < ietfInterfacesArray.length(); i++) {
                	Interface iface = new Interface();
            		JSONObject ietfInterfaceObj = ietfInterfacesArray.getJSONObject(i);
            		iface.setName(ietfInterfaceObj.getString("name"));
            		iface.setRoutername(routername);
            		try {
                		JSONObject ipv6Obj = ietfInterfaceObj.getJSONObject("ietf-ip:ipv6");
                		JSONArray ipv6AddressArray = ipv6Obj.getJSONArray("address");
            			JSONObject ipv6AddressObj = ipv6AddressArray.getJSONObject(0);
            			String str = ipv6AddressObj.getString("ip");
            			iface.setIpv6(str);
            		} catch(Exception e) {
            			iface.setIpv6("");
                	}
            		try {
                		JSONObject ipv4Obj = ietfInterfaceObj.getJSONObject("ietf-ip:ipv4");
                		JSONArray ipv4AddressArray = ipv4Obj.getJSONArray("address");         	
            			JSONObject ipv4AddressObj = ipv4AddressArray.getJSONObject(0);
            			String str = ipv4AddressObj.getString("ip");
            			iface.setIpv4(str);
            		} catch(Exception e) {
            			iface.setIpv4("");
                	}
            		interfaceList.add(iface);
	            }// end of loop 'for'
            }
        } catch (Exception e) {
        	e.printStackTrace();
			// TODO: handle exception
        } finally {
        	get.releaseConnection();
        }
	return interfaceList;
	}
}