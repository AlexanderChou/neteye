package com.savi.show.dao;

import java.util.LinkedList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.savi.base.model.*;
import com.savi.base.util.Constants;
import com.savi.base.util.HibernateUtil;
import com.savi.base.util.SnmpGetTable;
import com.savi.show.dto.BindingTableInfo;

public class IfInterfaceDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	//获得信任类型是no-trust的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getNoTrustInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifTrustStatus=1 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得信任类型是dhcp-trust的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getDhcpTrustInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifTrustStatus=2 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得信任类型是ra-trust的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getRaTrustInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifTrustStatus=3 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得信任类型是dhcp-ra-trust的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getDhcpRaTrustInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifTrustStatus=4 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得验证类型是enable的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getEnableValidationInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifValidationStatus=1 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得验证类型是disable的交换机端口个数
	@SuppressWarnings({"static-access"})
	public int getDisableValidationInterfaceCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(i.id) from Ifinterfacecur i JOIN i.switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where i.ifValidationStatus=2 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
		Query query = session.createQuery(sql);
		int num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Integer.parseInt(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得该交换机允许绑定的最大用户数
	@SuppressWarnings({"static-access"})
	public Long getSwitchMaxFilteringNumCount(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select SUM(i.ifFilteringNum) from Ifinterfacecur i JOIN i.switchcur c"+
		            " where c.id="+switchID;
		Query query = session.createQuery(sql);
		Long num;
		//如果ifinterfacecur表中就没有符合的记录时，query.list().get(0)为null
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Long.parseLong(query.list().get(0).toString());
		}else{
			num=new Long(0);
		}
		transaction.commit();
		return num;
	}
	
	//获得该交换机所有端口中最大的绑定用户数
	@SuppressWarnings({"static-access"})
	public long getInterfaceMaxFilteringNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select MAX(i.ifFilteringNum) from Ifinterfacecur i JOIN i.switchcur c"+
		            " where c.id="+switchID;
		Query query = session.createQuery(sql);
		long num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Long.parseLong(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	// 获得交换机的验证端口数
	@SuppressWarnings({"static-access"})
	public long getInterfaceValidationNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(i.id) from Ifinterfacecur i JOIN i.switchcur c"+
                    " where i.ifValidationStatus=1 and c.id="+switchID;
		Query query = session.createQuery(sql);
		long num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Long.parseLong(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	// 获得交换机的信任端口数
	@SuppressWarnings({"static-access"})
	public long getInterfaceTrustNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(i.id) from Ifinterfacecur i JOIN i.switchcur c"+
                    " where i.ifTrustStatus>1 and c.id="+switchID;
		Query query = session.createQuery(sql);
		long num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Long.parseLong(query.list().get(0).toString());
		}else{
			num=0;
		}
		transaction.commit();
		return num;
	}
	
	//获得该交换机的端口数
	@SuppressWarnings({"static-access"})
	public Long getSwitchInterfaceNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select COUNT(i.id) from Ifinterfacecur i JOIN i.switchcur c"+
		            " where c.id="+switchID;
		Query query = session.createQuery(sql);
		Long num;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			num = Long.parseLong(query.list().get(0).toString());
		}else{
			num=new Long(0);
		}
		transaction.commit();
		return num;
	}
	
	// 获得交换机switchID所有端口的IfIndex值
	@SuppressWarnings({"unchecked","static-access"})
	public List getInterfaceIndex(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select i.id,i.ifIndex from Ifinterfacecur i JOIN i.switchcur c"+
                    " where c.id="+switchID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	//实时获取端口绑定表信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<BindingTableInfo> getRealTimeBindingTableInfoList(String ifIndex, Long switchcurId, String firstResult, String maxResult, List<Integer> totalCount){
		List<BindingTableInfo> list = new LinkedList<BindingTableInfo>();
		int counter=0,maxLifeTime=0;
		int start=Integer.parseInt(firstResult);
		int end=Integer.parseInt(firstResult)+Integer.parseInt(maxResult)-1;
		Switchcur switchCur;
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("id",Long.valueOf(switchcurId))).list();
		if(switchCurList.size()==0){
			switchCur=null;
		}else{
			switchCur=switchCurList.get(0);
		}
		if(switchCur==null){
			totalCount.add(0);
			return list;
		}
		Switchbasicinfo switchBasicInfo=switchCur.getSwitchbasicinfo();
		SnmpGetTable snmpGetTable=new SnmpGetTable(switchBasicInfo);
		Constants.loadMibCount=0;
		String bindingTableResult[][]=snmpGetTable.getTableData("saviObjectsBindingTable");
		String filteringTableResult[][]=snmpGetTable.getTableData("saviObjectsFilteringTable");
		if(bindingTableResult!=null){
			for(int i=1;i<bindingTableResult.length;i++){
				if(Integer.parseInt(bindingTableResult[i][0])==switchCur.getIpVersion().intValue()&&
						Integer.parseInt(bindingTableResult[i][2])==Integer.parseInt(ifIndex)){
					if(counter>=start&&counter<=end){
						BindingTableInfo bindingTableInfo = new BindingTableInfo();
						bindingTableInfo.setIpAddressType(Integer.valueOf(bindingTableResult[i][0]));
						bindingTableInfo.setBindingType(Integer.valueOf(bindingTableResult[i][1]));
						bindingTableInfo.setIfIndex(Integer.valueOf(bindingTableResult[i][2]));
						bindingTableInfo.setIpAddress(bindingTableResult[i][3]);
						bindingTableInfo.setMacAddress(bindingTableResult[i][4]);
						bindingTableInfo.setBindingState(Integer.valueOf(bindingTableResult[i][5]));
						bindingTableInfo.getLifeTime().add(Integer.valueOf(bindingTableResult[i][6])/100);
						bindingTableInfo.setIsInFilteringTable(justify(bindingTableInfo,filteringTableResult));
						list.add(bindingTableInfo);
					}
					if(maxLifeTime<Integer.parseInt(bindingTableResult[i][6])/100){
						maxLifeTime=Integer.parseInt(bindingTableResult[i][6])/100;
					}
					counter++;
				}	
			}
			for(int j=0;j<list.size();j++){
				list.get(j).getLifeTime().add(maxLifeTime);
			}
		}
		totalCount.add(counter);//表项的总数
		transaction.commit();
		return list;
	}	
	
	//根据绑定类型实时获取接口绑定表信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<BindingTableInfo> getRealTimeBindingTableInfoListByType(String ifIndex, Integer type,Long switchcurId, String firstResult, String maxResult, List<Integer> totalCount){
		List<BindingTableInfo> list = new LinkedList<BindingTableInfo>();
		int counter=0,maxLifeTime=0;
		int start=Integer.parseInt(firstResult);
		int end=Integer.parseInt(firstResult)+Integer.parseInt(maxResult)-1;
		Switchcur switchCur;
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("id",switchcurId)).list();
		if(switchCurList.size()==0){
			switchCur=null;
		}else{
			switchCur=switchCurList.get(0);
		}
		if(switchCur==null){
			totalCount.add(0);
			return list;
		}
		Switchbasicinfo switchBasicInfo=switchCur.getSwitchbasicinfo();
		SnmpGetTable snmpGetTable=new SnmpGetTable(switchBasicInfo);
		Constants.loadMibCount=0;
		String bindingTableResult[][]=snmpGetTable.getTableData("saviObjectsBindingTable");
		String filteringTableResult[][]=snmpGetTable.getTableData("saviObjectsFilteringTable");
		if(bindingTableResult!=null){
			for(int i=1;i<bindingTableResult.length;i++){
				if(Integer.parseInt(bindingTableResult[i][0])==switchCur.getIpVersion().intValue()&&
						Integer.parseInt(bindingTableResult[i][2])==Integer.parseInt(ifIndex)&&
						Integer.parseInt(bindingTableResult[i][1])==type.intValue()){
					if(counter>=start&&counter<=end){
						BindingTableInfo bindingTableInfo = new BindingTableInfo();
						bindingTableInfo.setIpAddressType(Integer.valueOf(bindingTableResult[i][0]));
						bindingTableInfo.setBindingType(Integer.valueOf(bindingTableResult[i][1]));
						bindingTableInfo.setIfIndex(Integer.valueOf(bindingTableResult[i][2]));
						bindingTableInfo.setIpAddress(bindingTableResult[i][3]);
						bindingTableInfo.setMacAddress(bindingTableResult[i][4]);
						bindingTableInfo.setBindingState(Integer.valueOf(bindingTableResult[i][5]));
						bindingTableInfo.getLifeTime().add(Integer.valueOf(bindingTableResult[i][6])/100);
						bindingTableInfo.setIsInFilteringTable(justify(bindingTableInfo,filteringTableResult));
						list.add(bindingTableInfo);
					}
					if(maxLifeTime<Integer.parseInt(bindingTableResult[i][6])/100){
						maxLifeTime=Integer.parseInt(bindingTableResult[i][6])/100;
					}
					counter++;
				}	
			}
			for(int j=0;j<list.size();j++){
				list.get(j).getLifeTime().add(maxLifeTime);
			}
		}
		totalCount.add(counter);//表项的总数
		transaction.commit();
		return list;
	}
	private int justify(BindingTableInfo bindingTableInfo,String filteringTableResult[][]){
		if(filteringTableResult!=null){
			for(int i=1;i<filteringTableResult.length;i++){
				if(Integer.parseInt(filteringTableResult[i][0])==bindingTableInfo.getIpAddressType().intValue()&&
						Integer.parseInt(filteringTableResult[i][1])==bindingTableInfo.getIfIndex().intValue()&&
						filteringTableResult[i][2].equals(bindingTableInfo.getIpAddress())&&
						filteringTableResult[i][3].equals(bindingTableInfo.getMacAddress())){
					return 1;
				}
			}
			return 0;
		}else{
			return 0;
		}
	}
}
