package com.fault.action;

import java.util.List;

import com.base.util.BaseAction;
import com.fault.dao.FaultListDao;
import com.fault.dto.FaultNode;

public class FaultDeviceList  extends BaseAction{
	private static final long serialVersionUID = 1L;
	private FaultListDao faultlistdao = new FaultListDao();
	    private List<FaultNode> faultlistdo;  
		private Integer totalCount;
	    public String listDeviceEvent() throws Exception{
			String id = this.getRequest().getParameter("deviceid");
			faultlistdo = faultlistdao.getDeviceEvent(id);
			setTotalCount(faultlistdo.size());
			return SUCCESS;
		}
		
		public List<FaultNode> getFaultlistdo() {
			return faultlistdo;
		}
		public void setFaultlistdo(List<FaultNode> faultlistdo) {
			this.faultlistdo = faultlistdo;
			
		}

		public void setTotalCount(Integer totalCount) {
			this.totalCount = totalCount;
		}

		public Integer getTotalCount() {
			return totalCount;
		}
	
	
}
