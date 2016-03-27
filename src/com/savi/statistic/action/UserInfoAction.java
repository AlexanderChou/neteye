package com.savi.statistic.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.savi.show.action.BaseAction;
import com.savi.statistic.dao.UserInfoDao;
import com.savi.statistic.dto.UserInfo;

@SuppressWarnings("serial")
public class UserInfoAction extends BaseAction{
	private List<UserInfo> userInfoList;
	private String userName;
	private String userMAC;
	private String userIP;
	private String startTime;
	private String endTime;
	private int totalCount;
	private String start;
	private String limit;
	private boolean isOnline;
	private boolean isNull;
	
	@JSON(serialize = false)
	public String listUserInfo(){
		Date d1 = new Date();
		long longtime1 = d1.getTime();
		System.out.println(longtime1);
		//Long startTime = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		UserInfoDao userInfoDao=new UserInfoDao();
		userInfoList=userInfoDao.getUserInfoOnline(start, limit);
		totalCount=userInfoDao.getUserInfoOnlineNum();
		Date d2 = new Date();
		long longtime2 = d2.getTime();
		System.out.println(longtime2-longtime1);
		return SUCCESS;
	}
	@JSON(serialize = false)
	public String getUserInfo(){
		Date d1 = new Date();
		long longtime1 = d1.getTime();
		System.out.println(longtime1);
		UserInfoDao userInfoDao=new UserInfoDao();
		Long startTimeLong=null;
		Long endTimeLong=null;
		SimpleDateFormat dateFormate=new SimpleDateFormat("yyyy-MM-dd");
		Date startDate=null;
		if(startTime!=null&&!startTime.equals("")){
			try {
				startDate = dateFormate.parse(startTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startTimeLong=startDate.getTime();
		}
		Date endDate=null;
		if(endTime!=null&&!endTime.equals("")){
			try {
				endDate = dateFormate.parse(endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			endTimeLong=endDate.getTime();
		}
		try {
			userName=java.net.URLDecoder.decode(userName,"UTF-8").trim();
			userIP=java.net.URLDecoder.decode(userIP,"UTF-8").trim();
			userMAC=java.net.URLDecoder.decode(userMAC,"UTF-8").trim();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		userInfoList=userInfoDao.getUserInfo(userName, userIP, userMAC, startTimeLong, endTimeLong,isOnline,isNull,start,limit);
		totalCount=userInfoDao.getUserInfoNum(userName, userIP, userMAC, startTimeLong, endTimeLong,isOnline,isNull);
		Date d2 = new Date();
		long longtime2 = d2.getTime();
		System.out.println(longtime2-longtime1);
		return SUCCESS;
	}
	public List<UserInfo> getUserInfoList() {
		return userInfoList;
	}
	public void setUserInfoList(List<UserInfo> userInfoList) {
		this.userInfoList = userInfoList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMAC() {
		return userMAC;
	}
	public void setUserMAC(String userMAC) {
		this.userMAC = userMAC;
	}
	public String getUserIP() {
		return userIP;
	}
	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public boolean getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	public boolean getIsNull() {
		return isNull;
	}
	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}	
}
