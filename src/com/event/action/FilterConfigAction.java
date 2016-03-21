package com.event.action;

import java.io.PrintWriter;
import java.util.List;

import com.base.model.FilterConfig;
import com.base.util.BaseAction;
import com.event.dao.FilterConfigDao;

public class FilterConfigAction extends BaseAction {
	
	private boolean success;
	private boolean failure;
	private List<FilterConfig> filterConfigs;
	private FilterConfig filterConfig;
	private String eventmodel;
	private String eventtype;
	private String ip;
	private String FilterConfigids;
	


	private FilterConfigDao filterConfigDao = new FilterConfigDao();
	
	public String listFilterConfig() throws Exception {
		filterConfigs = filterConfigDao.getFilterConfigs();
	
	
		success = true;
		return SUCCESS;
	}
	public String addFilterConfig() throws Exception {

		String eventmodel = this.getRequest().getParameter("eventmodel");
		String eventtype = this.getRequest().getParameter("eventtype");
		String ip = this.getRequest().getParameter("ip");
		FilterConfig filterConfig = new FilterConfig();
		filterConfig.setEventmodel(eventmodel);
		filterConfig.setEventtype(eventtype);
		filterConfig.setIp(ip);
		filterConfigDao.save(filterConfig);
		success = true;
		return SUCCESS;

}

	  public String deleteFilterConfig() throws Exception {
			String[] FilterConfigids = this.getRequest().getParameter("FilterConfigids").trim().split(";");
			for (String FilterConfigid : FilterConfigids) {
				filterConfigDao.delete(Long.parseLong(FilterConfigid));
			}
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
			return null;
		}
	  
	  
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isFailure() {
		return failure;
	}
	public void setFailure(boolean failure) {
		this.failure = failure;
	}
	public List<FilterConfig> getFilterConfigs() {
		return filterConfigs;
	}
	public void setFilterConfigs(List<FilterConfig> filterConfigs) {
		this.filterConfigs = filterConfigs;
	}
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	public FilterConfigDao getFilterConfigDao() {
		return filterConfigDao;
	}
	public void setFilterConfigDao(FilterConfigDao filterConfigDao) {
		this.filterConfigDao = filterConfigDao;
	}
	public void setEventmodel(String eventmodel) {
		this.eventmodel = eventmodel;
	}
	public String getEventmodel() {
		return eventmodel;
	}
	public void setEventtype(String eventtype) {
		this.eventtype = eventtype;
	}
	public String getEventtype() {
		return eventtype;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public String getFilterConfigids() {
		return FilterConfigids;
	}
	public void setFilterConfigids(String filterConfigids) {
		FilterConfigids = filterConfigids;
	}
		
	
}
