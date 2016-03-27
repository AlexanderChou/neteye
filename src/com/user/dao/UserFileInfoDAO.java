package com.user.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.Department;
import com.base.model.Resource;
import com.base.model.UserPojo;
import com.base.util.Constants;
import com.base.util.JDOMXML;

/**
 * <p>Title:数据库访问类 </p>
 * <p>Description: 添加用户时对用户信息的初始化</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: bjrongzhi</p>
 * @author 李宪亮
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class UserFileInfoDAO extends BaseHibernateDAO {

	/**
	 * 初始化用户的个人信息 这个文件的作用我认为是方便查询
	 * @param user
	 * @param savePath
	 */
	public void initUserInfoToXml(UserPojo user, String savePath) {
		 Document document = DocumentHelper.createDocument();
		 Element root = document.addElement("user");
		 //创建root节点 设置根节点信息
		 Department department = user.getDepartment();
		 if (department != null) {
			 root.addAttribute("deparment", user.getDepartment().getName());
		} else {
			root.addAttribute("deparment", "no department");
		}
		 root.addAttribute("name", user.getName());
		 root.addAttribute("realName", user.getRealName());
		 root.addAttribute("password", user.getPassword());
		 root.addAttribute("email", user.getEmail());
		 root.addAttribute("telephone", user.getTelephone());
		 
		 //创建子节点
		 Element popedoms = root.addElement("popedoms");
		 Element temp = popedoms.addElement("popedom");
		 temp.addAttribute("url", "mainPage.do");
		 //这里调用一个方法 返回一个资源list 
		 List<Resource> reList = getResourcesOfUser(user);
		 for (Resource resource : reList) {
			 Element popedom = popedoms.addElement("popedom");
			 popedom.addAttribute("url", resource.getUrl());
		 }
		 List <Resource> defaultList = getDefaultResources();
		 for (Resource resource : defaultList) {
			 Element popedom = popedoms.addElement("popedom");
			 popedom.addAttribute("url", resource.getUrl());
		 }
		 JDOMXML.saveXml(savePath, document);
	} 
	
	/**
	 * 得到该用户的所有资源列表
	 * @param user 用户
	 * @return
	 */
	private List<Resource> getResourcesOfUser(UserPojo user){
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		String sql = "SELECT * FROM resource WHERE id IN ( " +
                     "SELECT resource_id FROM resource_group_popedom " +
                     "WHERE resource_group_id IN ( " +
                     "SELECT resource_group_id FROM user_group_popedom " +
                     "WHERE user_group_id IN( " +
                     "SELECT group_id FROM user_popedom " +
                     "WHERE user_id = "+ user.getId() + ")))";
		List<Resource> rs = session.createSQLQuery(sql).addEntity(Resource.class).list();
		transaction.commit();
		return rs;
	}	
	
	/**
	 * 得到该用户的所有资源列表
	 * @param user 用户
	 * @return
	 */
	private List<Resource> getDefaultResources(){
		Session session = getSession();
		Transaction transaction = session.beginTransaction();
		List<Resource> defaultList=session.createCriteria(Resource.class).add(Restrictions.eq("flag", 0)).list();
		transaction.commit();
		return defaultList;
	}	
	/**
	 * 删除个人文件信息 /这个方法有bug，比如id为11的，会把id为1的也删除了
	 * @param userId
	 */
	public void deleteUserInfo(String userId){
		 String filePath = Constants.webRealPath + "file/user/";
		 File[] files = new File(filePath).listFiles();
		 for (File file : files) {
			//if (file.getName().indexOf(userId) != -1) 
			if(file.getName().substring(file.getName().lastIndexOf("_")+1).equals(userId)){
				File[] sfs = file.listFiles();
				for (File sf : sfs) {
					sf.delete();
				}
				file.delete();
			}
		}
	}
	
	/**
	 * 阅读相应的用户文件 得到用该用户的资源
	 * @param fileName
	 * @return
	 */
	public List<String> getUrlStr(String fileName) {
		List<String> list = new ArrayList<String>();
		Document document = JDOMXML.readXML(fileName);
		Element user = document.getRootElement();
		Element popedoms = user.element("popedoms");
		List<Element> ps = popedoms.elements("popedom");
		for (Element p : ps) {
			list.add(p.attributeValue("url"));
		}
		return list;
	}
	
	/**
	 * 阅读相应的用户文件 得到用该用户的资源
	 * @param userId
	 * @return
	 */
	public List<String> getUserPopedom(String userId){
		List<String> urls = new ArrayList<String>();
		String filePath = Constants.webRealPath + "file/user/";
		File file = new File(filePath);
		File[] files = file.listFiles();
		OUTBREAK:
		for (File f : files) {
			
			if (f.getName().split("_").length < 2) {
				continue;
			}
			
			//if (f.getName().split("_")[1].equals(userId)) {
			if(f.getName().substring(f.getName().lastIndexOf("_")+1).equals(userId)){
				File[] fs = f.listFiles();
				for (File file2 : fs) {
					if (file2.getName().indexOf(f.getName().split("_")[0]) != -1) {
						urls = getUrlStr(file2.getAbsolutePath());
						break OUTBREAK;
					}
				}
			}
		}
		return urls;
	}
}
