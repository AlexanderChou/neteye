package com.asset.action;

import java.io.PrintWriter;
import java.util.List;

import com.asset.dao.AssetDepartDAO;
import com.base.model.AssetDepart;
import com.base.util.BaseAction;

public class AssetDepartManageAction extends BaseAction {
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<AssetDepart> assetDeparts;
	private List<AssetDepart> assetDepartNames;
	private AssetDepart assetDepart;

	private AssetDepartDAO assetDepartDAO = new AssetDepartDAO();
	
	public String listAssetDepart() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		assetDeparts = assetDepartDAO.getAssetDeparts(start, limit);
		totalCount = String.valueOf(assetDepartDAO.getAssetDepartsCount());
	
		success = true;
		return SUCCESS;
	}
	public String listAssetDepartName() throws Exception {

		setAssetDepartNames(assetDepartDAO.getAssetDepartNames());
		
		
		success = true;
		return SUCCESS;
	}
		public String deleteAssetDepart() throws Exception {
			String[] assetDepartIds = this.getRequest().getParameter("assetDepartIds").trim().split(";");
			for (String assetDepartId : assetDepartIds) {
				assetDepartDAO.delete(Long.parseLong(assetDepartId));
			}
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
			return null;
		}
		public String modifyAssetDepart() throws Exception {
			String assetDepartId = this.getRequest().getParameter("assetDepartId");
			String name = this.getRequest().getParameter("name");
			String departcode = this.getRequest().getParameter("departcode");
			String address = this.getRequest().getParameter("address");
			AssetDepart assetDepart = assetDepartDAO.getassetDepartById(Long.parseLong(assetDepartId));
			
//			boolean assetDepartNameIsHave = assetDepartDAO.checkassetDepartNameIsExist(name);
//			if (!(assetDepart.getName().equals(name))){
//			if (assetDepartNameIsHave) {
//				PrintWriter w = this.getResponse().getWriter();
//				w.print("same");
//				w.close();
//				return null;
//			} 
		//	}
		     	assetDepart.setName(name);
		     	assetDepart.setDepartcode(departcode);
		     	assetDepart.setAddress(address);
				assetDepartDAO.save(assetDepart);
				PrintWriter write = this.getResponse().getWriter();
				write.print("ok");
				write.close();
				return null;
			}
		
		public String addAssetDepart() throws Exception {
			//boolean assetDepartNameIsHave = assetDepartDAO.checkassetDepartNameIsExist(assetDepart.getName());
//			if (assetDepartNameIsHave) {
//				failure = true;
//				return SUCCESS;
//			}
			assetDepartDAO.save(assetDepart);
			success = true;
			return SUCCESS;
		
	
	}
	

		public String getTotalCount() {
			return totalCount;
		}
		
		public void setTotalCount(String totalCount) {
			this.totalCount = totalCount;
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
		
		
		
		public List<AssetDepart> getAssetDeparts() {
			return assetDeparts;
		}


		public void setAssetDeparts(List<AssetDepart> assetDeparts) {
			this.assetDeparts = assetDeparts;
		}


		public AssetDepart getAssetDepart() {
			return assetDepart;
		}
		
		public void setAssetDepart(AssetDepart assetDepart) {
			this.assetDepart = assetDepart;
		}
		
		public static void main(String[] args) {
			AssetDepartDAO dao = new AssetDepartDAO();
			List list = dao.getAssetDeparts("0", "10");
	        
			System.out.println("list="+list);}
		public void setAssetDepartNames(List<AssetDepart> assetDepartNames) {
			this.assetDepartNames = assetDepartNames;
		}
		public List<AssetDepart> getAssetDepartNames() {
			return assetDepartNames;
		}
}
	