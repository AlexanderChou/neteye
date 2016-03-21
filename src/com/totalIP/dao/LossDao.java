package com.totalIP.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.base.util.HibernateUtil;
import com.base.model.NodeLoss;
import com.totalIP.dto.NodeLossshow;

public class LossDao {
	public List<NodeLossshow> getRecentEightHoursLoss() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String eighthoursagodoc = df.format(new Date(date.getTime() - 8 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nl.region,nl.time,avg(nl.loss) from NodeLoss nl where nl.network_type = 4 and nl.time >= '"+eighthoursagodoc+"' group by nl.time,nl.region";
		List<NodeLossshow> LossList = new ArrayList<NodeLossshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeLossshow tempNode = new NodeLossshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setLoss(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			LossList.add(tempNode);
		}
		session.close();
		return LossList;
	}
	public List<NodeLossshow> getRecentOneDayLoss() {
		Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-HH");
        String onedayagodoc = df.format(new Date(date.getTime() - 24 * 60 * 60 * 1000));
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select nl.region,nl.time,avg(nl.loss) from NodeLoss nl where nl.network_type = 4 and nl.time >= '"+onedayagodoc+"' group by nl.time,nl.region";
		List<NodeLossshow> LossList = new ArrayList<NodeLossshow>();
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeLossshow tempNode = new NodeLossshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(TempList.get(j)[1].toString());
			tempNode.setLoss(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			LossList.add(tempNode);
		}
		session.close();
		return LossList;
	}
	public List<NodeLossshow> getRecentOneWeekLoss() {
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
		List<NodeLossshow> LossList = new ArrayList<NodeLossshow>();
		for(int i = 0; i < 7; i++)
		{
		String sql = "select nl.region,nl.time,avg(nl.loss) from NodeLoss nl where nl.network_type = 4 and nl.time >= '"+oneweektimedoc[i+1]+"' and nl.time < '"+oneweektimedoc[i]+"' group by nl.region";
		List<Object[]> TempList =  session.createQuery(sql).list();
		for(int j = 0; j < TempList.size(); j++)
		{
			NodeLossshow tempNode = new NodeLossshow();
			tempNode.setRegion(TempList.get(j)[0].toString());
			//System.out.println(TempList.get(j)[0].toString());
			tempNode.setTime(oneweektimedoc2[i+1]);
			tempNode.setLoss(Double.parseDouble(TempList.get(j)[2].toString()));
			//System.out.println(tempNode.getRtt());
			LossList.add(tempNode);
		}
		}
		session.close();
		return LossList;
	}
}