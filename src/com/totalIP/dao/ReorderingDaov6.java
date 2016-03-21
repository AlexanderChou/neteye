package com.totalIP.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.util.HibernateUtil;
import com.totalIP.dto.NodeReorderingshow;

public class ReorderingDaov6 {
	public List<NodeReorderingshow> getRecentEightHoursReordering() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String eighthoursagodoc = df.format(new Date(date.getTime() - 8 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nr.region,nr.time,avg(nr.reordering) from NodeReordering nr where nr.network_type = 6 and nr.time >= '"+eighthoursagodoc+"' group by nr.time,nr.region";
		List<NodeReorderingshow> ReorderingList = new ArrayList<NodeReorderingshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		session.close();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeReorderingshow tempNode = new NodeReorderingshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setReordering(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			ReorderingList.add(tempNode);
		}
		return ReorderingList;
	}
	public List<NodeReorderingshow> getRecentOneDayReordering() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String onedayagodoc = df.format(new Date(date.getTime() - 24 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nr.region,nr.time,avg(nr.reordering) from NodeReordering nr where nr.network_type = 6 and nr.time >= '"+onedayagodoc+"' group by nr.time,nr.region";
		List<NodeReorderingshow> ReorderingList = new ArrayList<NodeReorderingshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		session.close();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeReorderingshow tempNode = new NodeReorderingshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setReordering(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			ReorderingList.add(tempNode);
		}
		return ReorderingList;
	}
	public List<NodeReorderingshow> getRecentOneWeekReordering() {
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
		List<NodeReorderingshow> ReorderingList = new ArrayList<NodeReorderingshow>();
		for(int i = 0; i < 7; i++)
		{
		String sql = "select nr.region,nr.time,avg(nr.reordering) from NodeReordering nr where nr.network_type = 6 and nr.time >= '"+oneweektimedoc[i+1]+"' and nr.time < '"+oneweektimedoc[i]+"' group by nr.region";
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeReorderingshow tempNode = new NodeReorderingshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(oneweektimedoc2[i+1]);
			tempNode.setReordering(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			ReorderingList.add(tempNode);
		}
		}
		session.close();
		return ReorderingList;
	}
}
