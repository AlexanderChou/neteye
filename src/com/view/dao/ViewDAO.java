package com.view.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.base.model.View;
import com.base.util.HibernateUtil;
import com.view.dto.Router;
import com.view.dto.Link;

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
public class ViewDAO {
	public static List<Router> getDevices() throws Exception{
		int circleX = 400;
		int circleY = 300;
		int redius = 300;
		int n = 4;
		double angle = 2*3.1415926/n;
		ArrayList<Router> list = new ArrayList<Router>();
		Router r = new Router();
		r.setId(1);
		r.setName("beijing");
		r.setChineseName("北京");
		r.setIpv4("202.38.120.192");
		r.setIpv6("");
		int x = (int)(circleX - redius*Math.sin(angle*1)+12.5);
		int y = (int)(circleY - redius*Math.cos(angle*1)+12.5);
		r.setRX(x);
		r.setRY(y);
		list.add(r);
		Router r2 = new Router();
		r2.setId(2);
		r2.setName("wuhan");
		r2.setChineseName("武汉");
		r2.setIpv4("202.38.120.193");
		r2.setIpv6("");
		x = (int)(circleX - redius*Math.sin(angle*2)+12.5);
		y = (int)(circleY - redius*Math.cos(angle*2)+12.5);
		r2.setRX(x);
		r2.setRY(y);
		list.add(r2);
		Router r3 = new Router();
		r3.setId(3);
		r3.setName("guangzhou");
		r3.setChineseName("广州");
		r3.setIpv4("202.38.120.194");
		r3.setIpv6("");
		x = (int)(circleX - redius*Math.sin(angle*3)+12.5);
		y = (int)(circleY - redius*Math.cos(angle*3)+12.5);
		r3.setRX(x);
		r3.setRY(y);
		list.add(r3);
		Router r4 = new Router();
		r4.setId(4);
		r4.setName("shanghai");
		r4.setChineseName("上海");
		r4.setIpv4("202.38.120.195");
		r4.setIpv6("");
		x = (int)(circleX - redius*Math.sin(angle*4)+12.5);
		y = (int)(circleY - redius*Math.cos(angle*4)+12.5);
		r4.setRX(x);
		r4.setRY(y);
		list.add(r4);
		return list;
	}
	public static List<Link> getLinks() throws Exception{
		ArrayList<Link> list = new ArrayList<Link>();
		Link link = new Link();
		link.setId(1);
		link.setName("北京--上海");
		link.setSrcName("beijing");
		link.setDestName("shanghai");
		link.setSrcIP("202.38.120.192");
		link.setDestIP("202.38.120.195");
		link.setSrcInfIP("166.111.143.233");
		link.setDestInfIP("166.111.143.234");
		link.setSrcIPv6("");
		link.setDestIPv6("");
		link.setSrcInfIPv6("");
		link.setDestInfIPv6("");
		link.setSrcIfIndex(101);
		link.setDestIfIndex(102);
		link.setSrcSpeed(1.5);
		link.setDestSpeed(1.5);
		link.setSrcFlow(1000);
		link.setDestFlow(1000);
		list.add(link);
		Link link2 = new Link();
		link2.setId(2);
		link2.setName("北京--武汉");
		link2.setSrcName("beijing");
		link2.setDestName("wuhan");
		link2.setSrcIP("202.38.120.192");
		link2.setDestIP("202.38.120.193");
		link2.setSrcInfIP("166.111.143.231");
		link2.setDestInfIP("166.111.143.232");
		link2.setSrcIPv6("");
		link2.setDestIPv6("");
		link2.setSrcInfIPv6("");
		link2.setDestInfIPv6("");
		link2.setSrcIfIndex(103);
		link2.setDestIfIndex(104);
		link2.setSrcSpeed(1.5);
		link2.setDestSpeed(1.5);
		link2.setSrcFlow(1000);
		link2.setDestFlow(1000);
		list.add(link2);
		Link link3 = new Link();
		link3.setId(3);
		link3.setName("上海--武汉");
		link3.setSrcName("shanghai");
		link3.setDestName("wuhan");
		link3.setSrcIP("202.38.120.195");
		link3.setDestIP("202.38.120.193");
		link3.setSrcInfIP("166.111.143.231");
		link3.setDestInfIP("166.111.143.232");
		link3.setSrcIPv6("");
		link3.setDestIPv6("");
		link3.setSrcInfIPv6("");
		link3.setDestInfIPv6("");
		link3.setSrcIfIndex(103);
		link3.setDestIfIndex(104);
		link3.setSrcSpeed(1.5);
		link3.setDestSpeed(1.5);
		link3.setSrcFlow(1000);
		link3.setDestFlow(1000);
		list.add(link3);
		return list;
	}
	
	/**
	 * 由视图名字得到该视图
	 * @param viewName
	 * @return
	 */
	//取得主视图
	public View getViewMain() {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		View view = (View)session.createCriteria(View.class).add(Restrictions.eq("homePage", true)).uniqueResult();
		transaction.commit();
		return view;
	}
	
	public View getViewByViewName(String viewName) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		View view = (View)session.createCriteria(View.class).add(Restrictions.eq("name", viewName)).uniqueResult();
		transaction.commit();
		return view;
	}
	public View getViewByViewId(Long viewId) {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		View view = (View)session.createCriteria(View.class).add(Restrictions.eq("id", viewId)).uniqueResult();
		transaction.commit();
		return view;
	}
	public List<View> getAllViewByViewName() {
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<View> view = session.createCriteria(View.class).list();
		transaction.commit();
		return view;
	}
	public static void deleteFile(String filePath){
		File f = new File(filePath);
		if(f.exists() && f.isDirectory()){
			File delFiles[]= f.listFiles();
			for(int i = 0;i<delFiles.length;i++){
				deleteFile(delFiles[i].getAbsolutePath());
			}
		}
		f.delete();
	}
}
