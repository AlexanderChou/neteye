package com.user.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.Department;
import com.base.model.Resource;
import com.base.model.UserPojo;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
/**
 * 添加用户时对用户信息的初始化
 * @author 李宪亮
 *
 */
@SuppressWarnings("unchecked")
public class FileForUserDAO {

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
		 
		 //这里调用一个方法 返回一个资源list 
		 List<Resource> reList = getResourcesOfUser(user);
		 for (Resource resource : reList) {
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
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select * from resource where resource_group_id in ( select resource_group_id from popedom where user_group_id in (select group_id from user_popedom where user_id = " + user.getId() + " ))";
		List<Resource> rs = session.createSQLQuery(sql).addEntity(Resource.class).list();
		transaction.commit();
		return rs;
	}	
	/**
	 * 删除个人文件信息
	 * @param userId
	 */
	public void deleteUserInfo(String userId){
		 String filePath = Constants.webRealPath + "file/user/";
		 File[] files = new File(filePath).listFiles();
		 for (File file : files) {
			if (file.getName().indexOf(userId) != -1) {
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
			
			if (f.getName().split("_")[1].equals(userId)) {
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
