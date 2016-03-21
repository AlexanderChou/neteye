package com.asset.action;

import java.io.PrintWriter;
import java.util.List;

import com.asset.dao.AssetUserDAO;
import com.base.model.AssetUser;
import com.base.util.BaseAction;

public class AssetUserManageAction extends BaseAction {
	private String totalCount;
	private boolean success;
	private boolean failure;
	private List<AssetUser> assetUsers;
	private List<AssetUser> assetUserNames;
	private AssetUser assetUser;

	private AssetUserDAO assetUserDAO = new AssetUserDAO();
	
	public String listAssetUser() throws Exception {
		String start = this.getRequest().getParameter("start");
		String limit = this.getRequest().getParameter("limit");
		assetUsers = assetUserDAO.getAssetUsers(start, limit);
		totalCount = String.valueOf(assetUserDAO.getAssetUsersCount());
	
		success = true;
		return SUCCESS;
	}
	public String listAssetUserName() throws Exception {
		
		setAssetUserNames(assetUserDAO.getAssetUserNames());
	
		success = true;
		return SUCCESS;
	}
		public String deleteAssetUser() throws Exception {
			String[] assetUserIds = this.getRequest().getParameter("assetUserIds").trim().split(";");
			for (String assetUserId : assetUserIds) {
				assetUserDAO.delete(Long.parseLong(assetUserId));
			}
			PrintWriter writer = this.getResponse().getWriter();
			writer.print("ok");
			writer.close();
			return null;
		}
		public String modifyAssetUser() throws Exception {
			String assetUserId = this.getRequest().getParameter("assetUserId");
			String userName = this.getRequest().getParameter("userName");
			String userEmail = this.getRequest().getParameter("userEmail");
			String userMobile = this.getRequest().getParameter("userMobile");
			String userTel = this.getRequest().getParameter("userTel");
			String userDepart = this.getRequest().getParameter("userDepart");
			AssetUser assetUser = assetUserDAO.getassetUserById(Long.parseLong(assetUserId));
			
			boolean assetUserNameIsHave = assetUserDAO.checkassetUserNameIsExist(userName);
//			if (!(assetUser.getUserName().equals(userName))){
//			if (assetUserNameIsHave) {
//				PrintWriter w = this.getResponse().getWriter();
//				w.print("same");
//				w.close();
//				return null;
//			} 
//			}
		    
		     	assetUser.setUserName(userDepart);
		     	assetUser.setUserEmail(userEmail);
		     	assetUser.setUserMobile(userMobile);
		     	assetUser.setUserName(userName);
		     	assetUser.setUserTel(userTel);
				assetUserDAO.save(assetUser);
				PrintWriter write = this.getResponse().getWriter();
				write.print("ok");
				write.close();
				return null;
			}
		
		public String addAssetUser() throws Exception {
//			boolean assetUserNameIsHave = assetUserDAO.checkassetUserNameIsExist(assetUser.getUserName());
//			if (assetUserNameIsHave) {
//				failure = true;
//				return SUCCESS;
//			}
			assetUserDAO.save(assetUser);
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
		public List<AssetUser> getAssetUsers() {
			return assetUsers;
		}
		public void setAssetUsers(List<AssetUser> assetUsers) {
			this.assetUsers = assetUsers;
		}
		public AssetUser getAssetUser() {
			return assetUser;
		}
		public void setAssetUser(AssetUser assetUser) {
			this.assetUser = assetUser;
		}
		public AssetUserDAO getAssetUserDAO() {
			return assetUserDAO;
		}
		public void setAssetUserDAO(AssetUserDAO assetUserDAO) {
			this.assetUserDAO = assetUserDAO;
		}
		public static void main(String[] args) {
			AssetUserDAO dao = new AssetUserDAO();
			List list = dao.getAssetUsers("0", "10");
	        
			System.out.println("list="+list);}
		public void setAssetUserNames(List<AssetUser> assetUserNames) {
			this.assetUserNames = assetUserNames;
		}
		public List<AssetUser> getAssetUserNames() {
			return assetUserNames;
		}
}
	