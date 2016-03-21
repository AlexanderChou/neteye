package com.totalIP.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.util.HibernateUtil;
import com.totalIP.dto.NodeDelayshow;

public class DelayDaov4v6 {
	public List<NodeDelayshow> getRecentEightHoursDelay() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String eighthoursagodoc = df.format(new Date(date.getTime() - 8 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nd.network_type,nd.time,avg(nd.rtt) from NodeDelay nd where nd.time >= '"+eighthoursagodoc+"' group by nd.time,nd.network_type";
		List<NodeDelayshow> DelayList = new ArrayList<NodeDelayshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeDelayshow tempNode = new NodeDelayshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setRtt(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			DelayList.add(tempNode);
		}
		session.close();
		return DelayList;
	}
	public List<NodeDelayshow> getRecentOneDayDelay() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String onedayagodoc = df.format(new Date(date.getTime() - 24 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nd.network_type,nd.time,avg(nd.rtt) from NodeDelay nd where nd.time >= '"+onedayagodoc+"' group by nd.time,nd.network_type";
		List<NodeDelayshow> DelayList = new ArrayList<NodeDelayshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeDelayshow tempNode = new NodeDelayshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setRtt(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			DelayList.add(tempNode);
		}
		session.close();
		return DelayList;
	}
	public List<NodeDelayshow> getRecentOneWeekDelay() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        SimpleDateFormat df2 = new SimpleDateFormat("MM-dd");
        String [] oneweektimedoc = new String[8];
        String [] oneweektimedoc2 = new String[8];// for save
        for(int i = 0; i < 8; i++){
        oneweektimedoc[i] = df.format(new Date(date.getTime() - i * 24 * 60 * 60 * 1000));
        oneweektimedoc2[i] = df2.format(new Date(date.getTime() - i * 24 * 60 * 60 * 1000));
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<NodeDelayshow> DelayList = new ArrayList<NodeDelayshow>();
		for(int i = 0; i < 7; i++)
		{
		String sql = "select nd.network_type,nd.time,avg(nd.rtt) from NodeDelay nd where nd.time >= '"+oneweektimedoc[i+1]+"' and nd.time < '"+oneweektimedoc[i]+"' group by nd.network_type";
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeDelayshow tempNode = new NodeDelayshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(oneweektimedoc2[i+1]);
			tempNode.setRtt(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			DelayList.add(tempNode);
		}
		}
		session.close();
		return DelayList;
	}
}
