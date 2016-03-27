package com.event.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.model.Alarm;
import com.base.model.Device;
import com.base.model.EventPojo;
import com.base.model.EventSeq;
import com.base.model.EventStatus;
import com.base.model.IfInterface;
import com.base.model.UserMessage;
import com.base.model.UserPojo;
import com.base.service.EventStatusService;
import com.base.util.Constants;
import com.base.util.HibernateUtil;
import com.event.dao.EventDAO;
import com.event.dto.EventInfo;

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
 * <p>Title: 事件统一进行处理类</p>
 * <p>Description: 对接收到的事件，进行相应的分析和处理（20090905对该类进行修改，将分析后要进行报警的事件批量插入user_message表，
 * 为提高性能，将以前调用EventDAO中方法，全部改为直接操作）
 * </p>
 * @version 1.0
 * @author 郭玺
 * <p>Company: 网络中心</p>
 * <p>Copyright: Copyright (c) 2009</p>
 */
public class EventServer {
	private EventStatusService eventStatusService;
	private EventDAO eventDAO;
	private static LinkedHashSet<String> downIps=new LinkedHashSet<String>();
	String file = Constants.webRealPath+"file/event/faultNodes";
	
	public EventServer() {
		this.eventStatusService = new EventStatusService();
		this.eventDAO = new EventDAO();
	}
	public void writeToFile() {
		try {
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write(new Timestamp(Calendar.getInstance().getTimeInMillis()).toString().substring(0,19));
			bw.newLine();
			Iterator it = downIps.iterator();
			while(it.hasNext()) {
				bw.write((String)it.next());
				bw.newLine();
			}
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Vector readFile(){		
		Vector result = new Vector();
		FileReader reader = null;
		BufferedReader br = null;
		String line = null;	
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			while ((line = br.readLine()) != null) {
				if (line.equals("")) 
				{
					continue;
				}else{
					result.add(line);
				}
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}finally{
			try {
				br.close();
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public void changeDownIps(EventPojo recEvent) {
		if(recEvent.getPriority()==0
				//||recEvent.getObjType().equals("interface")
				)
			return;
		if(recEvent.getTypeValue().equalsIgnoreCase("link_up")||recEvent.getTypeValue().equalsIgnoreCase("up")) {
			//应该通过该事件值找到所对应的IP
			String ip = recEvent.getObjIPv4();
			if(ip!=null && !ip.equals(""))
				downIps.remove(ip);
			ip = recEvent.getObjIPv6();
			if(ip!=null && !ip.equals(""))
				downIps.remove(ip);
			writeToFile();
		}else if(recEvent.getTypeValue().equalsIgnoreCase("link_down")||recEvent.getTypeValue().equalsIgnoreCase("down")) {
			String ip = recEvent.getObjIPv4();
			if(ip!=null && !ip.equals(""))
				downIps.add(ip);
			ip = recEvent.getObjIPv6();
			if(ip!=null && !ip.equals(""))
				downIps.add(ip);
			writeToFile();
		}
	}
	public String dealWithEvent(String moduleId, String primeTime,
			String typeValue, String IP, String content) {
//	public String dealWithEvent(String moduleId, String time, String objIp,
//				String moduleType, String typeValue, String serviceType,int priority,
//				String title, String content) {
		Date date = new Date(new Long(primeTime).longValue()*1000);
		
		String  time = MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss}" ,date);
		EventPojo theRecEvent = null;
		EventInfo eventInfo = null;
		int action = 0;
		String objIp = IP;
		String moduleType = typeValue;
		int priority = 0;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			theRecEvent = currentEvent(moduleId, time, objIp, moduleType, typeValue,
					priority, "", content);
			if(theRecEvent != null){
				eventInfo = eventStatusService.getEventStatusInfoByCondition(" e.objtype='"
					+ theRecEvent.getObjType() + "' and e.obj_id='" + theRecEvent.getObjId()
					+ "' and e.moduletype='" + theRecEvent.getModuleType() + "'");
				
				action = this.nextAction(theRecEvent, eventInfo);
				switch (action) {
				case 0:
					doAction0();
					break;
				case 1:
					doAction1(theRecEvent,session);
					changeDownIps(theRecEvent);
					break;
				case 2:
					doAction2(theRecEvent,session);
					changeDownIps(theRecEvent);
					break;
				case 3:
					doAction3(theRecEvent, eventInfo,session);
					break;
				case 4:
					doAction4(theRecEvent, eventInfo,session);
					changeDownIps(theRecEvent);
					break;
				default:
					break;
				}
			}			
			tx.commit();
		} catch (Exception e) {
			action = -1;
			e.printStackTrace();
			if (tx != null)
				tx.rollback();
		}
		//return message;
		return "action="+action+"";
	}

	/**
	 * 返回值为整数，表示下一步要对事件进行处理的动作： 0--代表不合法或过时事件，丢弃 1--代表暂不在受控范围内的事件，写入事件表与事件序列表
	 * 2--代表新类型事件，写入事件表与事件序列表，并在对象状态表中添加新项 3--代表需要进行的压缩事件，更新事件与对象状态表，插入事件到事件序列表
	 * 4--代表已有类型的新事件，写入事件表与事件序列表，并更新对象状态表
	 */
	private int nextAction(EventPojo recEvent, EventInfo o) throws Exception {
		if (recEvent == null)
			return 0;
		if (recEvent.getObjType().equals("unknown"))
			return 1;
		if (o==null)
			return 2;
		if (recEvent.getOccurTime().compareTo(o.getOccurTime()) < 0)
			return 0;
		if (recEvent.getTypeValue().equals(o.getTypeValue())
				&& recEvent.getOccurTime().substring(0, 10).equals(
						o.getOccurTime().substring(0, 10)))
			return 3;
		return 4;
	}

	private void doAction0() {
		return;
	}

	private void doAction1(EventPojo recEvent,Session session) throws Exception {
		//eventDAO.save(recEvent);
		session.save(recEvent);
		EventSeq eseq = new EventSeq(recEvent.getId(), recEvent.getOccurTime(),
						recEvent.getReceiveTime(), recEvent.getModuleId(), 
						recEvent.getModuleType(), recEvent.getTypeValue(), 
						recEvent.getPriority(), recEvent.getObjType(), 
						recEvent.getObjId(),recEvent.getTitle(), recEvent.getContent());
		//eventDAO.save(eseq);
		session.save(eseq);
		insertToAlarms(recEvent,session);
		//此处添加邮件报警
		eventDAO.sendEmain("故障告警",recEvent.getTypeValue());
		//此处添加短信报警
		StringBuffer content = new StringBuffer();
		String IP = recEvent.getObjIPv4();
		if((IP==null||IP.equals(""))&&(recEvent.getObjIPv6()!=null||!recEvent.getObjIPv6().equals(""))){
			IP = recEvent.getObjIPv6();
		}
		content.append("您好：").append(recEvent.getObjName()).append("("+IP+")").append("出现").append(recEvent.getTypeValue()).append(",请及时处理！");
		eventDAO.sendMessage(content.toString());
	}

	/**
	 * 插入事件到事件表与事件序列表，并在对象状态表中添加新项
	 */
	private void doAction2(EventPojo recEvent,Session session) throws Exception {
		doAction1(recEvent,session);
		EventStatus objStatus = new EventStatus(recEvent.getId());
		//eventDAO.save(objStatus);
		session.save(objStatus);
	}
	
	/**
	 * 更新事件与对象状态表，插入事件到事件序列表
	 */
	private void doAction3(EventPojo recEvent, EventInfo o,Session session) throws Exception {
		//Event dbEvent = eventDAO.findById(o.getEventId());
		EventPojo dbEvent = (EventPojo)session.load(EventPojo.class, o.getEventId());
		dbEvent.setOccurTime(recEvent.getOccurTime());
		dbEvent.setReceiveTime(recEvent.getReceiveTime());
		dbEvent.setOccurNum(dbEvent.getOccurNum() + 1);
		EventSeq eseq = new EventSeq(o.getEventId(), recEvent.getOccurTime(),
						recEvent.getReceiveTime(), recEvent.getModuleId(), 
						recEvent.getModuleType(), recEvent.getTypeValue(), 
						recEvent.getPriority(), recEvent.getObjType(), 
						recEvent.getObjId(), recEvent.getTitle(), 
						recEvent.getContent());
		//eventDAO.save(eseq);
		session.save(eseq);
	}

	/**
	 * 插入事件到事件表与事件序列表，并更新对象状态表
	 */
	private void doAction4(EventPojo recEvent, EventInfo o,Session session) throws Exception {
		doAction1(recEvent,session);
		//EventStatus eventStatus = eventDAO.getEventStatus(o.getId());
		EventStatus eventStatus = (EventStatus)session.load(EventStatus.class, o.getId());
		eventStatus.setEventId(recEvent.getId());
	}

	private void insertToAlarms(EventPojo e,Session session) throws Exception {
		if (e.getPriority() > 0) {
			Alarm a = new Alarm(e.getOccurTime(), e.getModuleId(), e.getModuleType(),
					e.getTypeValue(), e.getPriority(), e.getObjType(), e.getObjId(), 
					e.getObjName(), e.getObjIPv4(), e.getObjIPv6(), e.getTitle(), 
					e.getContent());
			//eventDAO.save(a);
			session.save(a);
			//查询user表，获得所有用户列表
			List<UserPojo> users = session.createQuery("from User").list();
			for(int i=0;i<users.size();i++){
				UserPojo user = users.get(i);
				UserMessage message = new UserMessage();
				message.setUserId(user.getId());
				message.setObjId(a.getId());
				message.setObjStatus("unread");
				message.setObjType("alarm");
				//eventDAO.save(message);
				session.save(message);
				if ( i % 20 == 0 ) { 
			       //20，与JDBC批量设置相同,将本批插入的对象立即写入数据库并释放内存  
			       session.flush(); 
			       session.clear(); 
			    } 

			}
			
		}
	}
	private EventPojo currentEvent(String moduleId, String time, String objIp,
			String moduleType, String typeValue, int priority,
			String title, String content) throws Exception {

		// 需要补上的信息
		Integer occurNum = 1;
		String objIPv4 = "";
		String objIPv6 = "";
		String objType = "";
		Long objId;
		String objName = "";
		String status = "open";
		long receiveTimeValue = System.currentTimeMillis();
		Date date = new Date(new Long(receiveTimeValue).longValue());
		String  receiveTime = MessageFormat.format("{0,date,yyyy-MM-dd HH:mm:ss}" ,date);
		if (time.equals("")) {
			time = receiveTime;
		}
		/// 判断objIp是v4还是v6
		if (objIp == null || objIp.equals("")) {
			// 对象不存在，错误的事件，应该丢弃�ö���
			return null;
		} else if (objIp.indexOf(".") != -1) {
			objIPv4 = objIp;
		} else {
			objIPv6 = objIp;
		}
		Device device = eventDAO.getDeviceByCondition(" where loopbackIP='" + objIp
				+ "' or loopbackIPv6='" + objIp + "'");
		if (device != null) {
			objType = "device";
			objId = device.getId();
			objName = device.getName();
			objIPv4 = device.getLoopbackIP();
			objIPv6 = device.getLoopbackIPv6();
		} else {
			IfInterface inter = eventDAO.getIfInterfaceByCondition("where ipv4='" + objIp + "' or ipv6='" + objIp + "'");
			
			if (inter != null) {
				objType = "interface";
				objId = inter.getId();
				/*李宪亮：这里将事件的名字设为路由器的名字 和端口的描述 */
				objName = inter.getDevice().getName() + "的设备下的端口：" + inter.getIfindex()+ "(该端口描述：" + inter.getDescription() + ")" ;
				objIPv4 = inter.getIpv4();
				objIPv6 = inter.getIpv6();
			} else {
				objType = "unknown";
				objId = Long.valueOf("-1");
			}
		}
		EventPojo curEvent = new EventPojo(occurNum, time, receiveTime, moduleId, moduleType,
				typeValue, priority, objType, objId, objName,
				objIPv4, objIPv6, title, content, status);
		return curEvent;
	}
	
	private String judgeIPType(String objIp){
		String objType = "4";
		if (objIp != null && !objIp.equals("")) {			
			if (objIp.indexOf(":") != -1) {
				objType = "6";
			} 
		}
		return objType;
	}
    

}
