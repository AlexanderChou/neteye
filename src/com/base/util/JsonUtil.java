package com.base.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

public class JsonUtil {
	
	public static String columnToJsonData(){
		String column = null;
		return column;
	}

	/**
	 * 将分页的列表，及其分页的参数通过json的格式输出给客户端
	 * 
	 * @param page
	 *            分页对象
	 * @param data
	 *            列表数据，按照json格式组装
	 * @return
	 */

	public static String pageToJosnData(Page page, String data) {
		return "{success:true,msg:'',totalRecords:" + page.getTotalRecords()
				+ ",recordIndex:" + page.getRecordIndex() + ",pageSize:"
				+ page.getPageSize() + ",Data:" + data + "}";
	}

	/**
	 * 获得操作成功的Json数据<br>
	 * 
	 * @param message
	 * @return
	 */
	public static String handlerJsonData(boolean result, String message) {
		String tip = "";
		if (result) {
			tip = "操作成功提示!";
		} else {
			tip = "操作失败提示!";
		}
		return "{success:" + result + ",tip:'" + tip + "',msg:'" + message
				+ "'}";
	}

	/**
	 * 
	 * @param content
	 * @throws Exception
	 */
	public static void printMsgToClient(String content) throws Exception {
		HttpServletResponse response = (HttpServletResponse) ActionContext
				.getContext().get(ServletActionContext.HTTP_RESPONSE);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		content = content.replaceAll("\n", "");
		out.println(content);
		out.flush();
		out.close();
	}
}
