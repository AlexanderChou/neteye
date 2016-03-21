package com.event.dao;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.Device;
import com.base.model.EventPojo;
import com.base.model.IfInterface;
import com.base.util.Constants;
import com.base.util.EmailUtil;
import com.base.util.HibernateUtil;
import com.base.util.JDOMXML;
import com.base.util.MessageUtil;
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
 * <p>Title: 对收到事件进行处理的数据库操作类</p>
 * <p>Description: 对接收到的事件，要进行相应的分析和处理，为了提高性能，EventServer类采用了一次数据库连接，
 * 多次数据库操作的方式。 所以，该类提供的方法仅为原子方法，并未进行事务提交。
 * </p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class EventDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	public void delete(Long eventId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		EventPojo event = (EventPojo)session.get(EventPojo.class, eventId);
		if(event!=null){
			session.delete(event);
		}
		transaction.commit();
	}
	public Device getDeviceByCondition(String condition) throws Exception {
		Device device = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		List temp = session.createQuery("from Device " + condition).list(); 
		if(temp.size()>0) {
			device = (Device)temp.get(0);
		}
		return device;
	}
	public static IfInterface getIfInterfaceByCondition(String condition) throws Exception {
		IfInterface ifInterface = null;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		//Transaction transaction = session.beginTransaction();
		List temp = session.createQuery("from IfInterface " + condition).list(); 
		if(temp.size()>0) {
			ifInterface = (IfInterface)temp.get(0);
		Hibernate.initialize(ifInterface.getDevice());//空指针,放入判断内.debug.
		}
		//transaction.commit();
		return ifInterface;
	}

	public void createFilter(){
		List filters = new ArrayList();
	 	filters.add("testFilter");
	 	List IPs = new ArrayList();
	 	IPs.add("testIP");
        Document document = DocumentHelper.createDocument();
		Element root = document.addElement("event");
        Element results = root.addElement("filters");
        Element result = results.addElement("filter");
        for(int j=0;j<filters.size();j++){
        	result.addElement("module").addText(filters.get(j).toString());
        	result.addElement("eventType").addText(filters.get(j).toString());
        	Element ip = result.addElement("IPs");
        	for(int k=0;k<IPs.size();k++){
        		ip.addElement("IP").addText(IPs.get(k).toString());
        	}
        }
        JDOMXML.saveXml(Constants.webRealPath+"file/event/filters.xml", document);
	}
	public void sendEmain(String subject,String body){
		//读取xml文件
		String filePath = Constants.webRealPath+"file/event/email.xml";
		Document document = JDOMXML.readXML(filePath);
		Element datas = document.getRootElement().element("datas");
		
		List<Element> dataRecond = datas.elements("data");
		for (Element data : dataRecond) {
			Constants.SMTPSERVICE = data.element("smtp").getText();
			Constants.USEROFEMAIL = data.element("userName").getText();
			Constants.PWDOFEMAIL = data.element("userPasswd").getText();
			Constants.EMAILOFSEND = data.element("sendEmail").getText();
			Element receiveEmails = data.element("receiveEmails");
			List<Element> receives = receiveEmails.elements("receiveEmail");
			for(Element receive : receives){
				Constants.receives.add(receive.getText());
				EmailUtil.send(Constants.EMAILOFSEND,receive.getText(), Constants.SMTPSERVICE,Constants.USEROFEMAIL, Constants.PWDOFEMAIL,subject,body);
			}
		}
	}
	public void sendMessage(String body){
		//读取xml文件
		String filePath = Constants.webRealPath+"file/event/message.xml";
		Document document = JDOMXML.readXML(filePath);
		Element datas = document.getRootElement().element("datas");
		StringBuffer mobiles = new StringBuffer();
		List<Element> dataRecond = datas.elements("data");
		for (Element data : dataRecond) {
			Constants.sendMboile = data.element("sendMobile").getText();
			Constants.sendPasswd = data.element("sendPasswd").getText();
			Element receiveEmails = data.element("receives");
			List<Element> receives = receiveEmails.elements("receiveMobile");
			for(Element receive : receives){
				Constants.receiveMobiles.add(receive.getText());
				mobiles.append("<phone>").append(receive.getText()).append("</phone>\n");
			}
		}
		//如果验证通过，允许发送短信
		if(Constants.sendMboile.trim().equals("13811186671") && Constants.sendPasswd.trim().equals("70501782")){
			MessageUtil.SendSMSWithXML(Constants.sendMboile,Constants.sendPasswd,mobiles.toString(),body);
		}
	}
	/* 注：最初实现时，EventServer中调用以下方法，后来在个性化设置方面做了一些相应的更改，每个用户
	 * 都拥有报警事件，即接收到事件后，查询用户表，将报警信息批量插入user_message表中。EventServer中
	 * 的doAction方法加入session参数，从性能考虑，EventServer中可直接调用save方法，没必要再见接调入
	 * 以下方法
	public Event findById(long id) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Event	event=(Event) session.load(Event.class, id);
		return event;
	}
	public EventStatus getEventStatus(long id) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		EventStatus	eventStatus =(EventStatus) session.load(EventStatus.class, id);
		return eventStatus;
	}
	public void save(Event event) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		session.save(event);
	}
	public void save(EventSeq eventSeq) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		session.save(eventSeq);
	}
	public void save(EventStatus o) throws Exception {
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		session.save(o);
	}
	public void save(Alarm alarm) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		session.save(alarm);
	}
	public void save(UserMessage message) throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		session.save(message);
	}
	public List getUsers() throws Exception{
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		List<User> list = session.createQuery("from User").list();
		return list;
	}*/
}
