package com.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>Title:业务逻辑类 </p>
 * <p>Description: 基本Action</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * @author 李宪亮
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
	/**
     * Convenience method to get the request
     * @return current request
     */
    protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * Convenience method to get the response
     * @return current response
     */
    protected HttpServletResponse getResponse() {
    	
        return ServletActionContext.getResponse();
    }

    /**
     * Convenience method to get the session. This will create a session if one doesn't exist.
     * @return the session from the request (request.getSession()).
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }
    
    private Page page;
    
	//当前记录数
	private int recordIndex;
	
	//每页个数
	private int pageSize = 15; 

	public int getRecordIndex() {
		return recordIndex;
	}

	public void setRecordIndex(int recordIndex) {
		this.recordIndex = recordIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(Page page) {
		this.page = page;
	}
    
	public Page getPage() {
		if (page == null) {
			page=new Page();
		}
		page.setRecordIndex(recordIndex);
		page.setPageSize(pageSize);
		return page;
	}
    
}
