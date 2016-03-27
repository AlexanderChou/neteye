package com.savi.user.Interceptor;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class UserInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 753248250402543584L;

	//拦截Action处理的拦截方法        
    @SuppressWarnings("unchecked")
	public String intercept(ActionInvocation invocation) throws Exception {        

            // 取得请求相关的ActionContext实例        
            ActionContext ctx=invocation.getInvocationContext();        
            Map session=ctx.getSession();  
            
            //取出名为user的session属性        
            String user=(String)session.get("userName");     

            if(user == null || StringUtils.isEmpty(user)){
            	return "reLogin";
            }
     
            return invocation.invoke();                     

    }  
}
