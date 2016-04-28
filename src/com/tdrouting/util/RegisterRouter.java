package com.tdrouting.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PutMethod;

import com.tdrouting.dto.Router;

public class RegisterRouter {
	@SuppressWarnings("deprecation")
	public static void register(Router router)
	{
		String registerString = Defination.REGISTER;
		registerString = registerString.replace("$DEVICE_NAME", router.getName());
		registerString = registerString.replace("$IP_ADDRESS", router.getNetconfipv4());
		registerString = registerString.replace("$PORT", router.getNetconfport().toString());
		registerString = registerString.replace("$USERNAME", router.getNetconfuser());
		registerString = registerString.replace("$PASSWORD", router.getNetconfpasswd());

		HttpClient client = new HttpClient(); 
		client.getState().setCredentials(new AuthScope(Defination.SERVER_IP, Defination.PORT), new UsernamePasswordCredentials(Defination.USERNAME, Defination.PASSWORD));
		String url = "http://"+Defination.SERVER_IP+":"+Defination.PORT+"/restconf/config/opendaylight-inventory:nodes/node/controller-config/yang-ext:mount/config:modules/module/odl-sal-netconf-connector-cfg:sal-netconf-connector/"+router.getName();
		PutMethod put = new PutMethod(url);
		put.addRequestHeader("Content-Type","application/xml");
		put.setRequestBody(registerString);
        put.setDoAuthentication(true);
		try {
			int status = client.executeMethod(put);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			put.releaseConnection();
		}
	}
}