package com.savi.show.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.savi.base.model.Apinfo;
import com.savi.base.model.Deviceinfo;
import com.savi.base.model.Ifinterfacecur;
import com.savi.base.model.SaviFilterTableCur;
import com.savi.base.model.Savibindingtablecur;
import com.savi.base.model.Subnet;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.model.User;
import com.savi.base.util.Constants;
import com.savi.base.util.HibernateUtil;
import com.savi.base.util.SnmpGetTable;
import com.savi.cernet.soa.WSUtil;
import com.savi.show.dto.AcForGlobalView;
import com.savi.show.dto.ApForGlobalView;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.HuaSanBindingTableInfo;
import com.savi.show.dto.HuaSanSaviFilterTableCurInfo;
import com.savi.show.dto.InterfaceInfo;
import com.savi.show.dto.PreHuaSanUserInfo;
import com.savi.show.dto.PreUserInfo;
import com.savi.show.dto.SubnetForGlobalView;
import com.savi.show.dto.SwitchForGlobalView;
import com.savi.show.dto.SwitchInfo;
import com.savi.show.dto.SwitchInfoForDetail;
import com.savi.show.dto.SwitchcurTemp;
import com.view.dto.DeviceInfo;

public class DeviceDao {
	private static HibernateUtil hibernateUtil = new HibernateUtil();
	
	public List<Deviceinfo> getDeviceInfoList(Integer isDelete){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Deviceinfo> deviceinfoList=session.createCriteria(Deviceinfo.class).add(Restrictions.eq("isDelete",isDelete)).list();
		transaction.commit();
		return deviceinfoList;
	}
	
	public Deviceinfo getDeviceinfo(Long id){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Deviceinfo> deviceinfoList=session.createCriteria(Deviceinfo.class).add(Restrictions.eq("id",id)).list();
		if(deviceinfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return deviceinfoList.get(0);
		
	}
	
	//批量获取交换机基本信息
	@SuppressWarnings("unchecked")
	public List<Switchcur> batchGetSwitchbasicinfo(String[] idArr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchcur.class).createCriteria("switchbasicinfo");
		Criterion expression=null;

//		for(int i = 0; i < idArr.length; i++){
//			if(expression == null)
//				expression = Restrictions.eq("id", Long.parseLong(idArr[i]));
//			else
//				expression = Restrictions.or(expression, 
//						Restrictions.eq("id", Long.parseLong(idArr[i])));
//		}
		
		if(expression != null) c.add(expression).addOrder(Order.asc("name"));
		List<Switchcur> list = c.list();
		transaction.commit();
		return list;
	}
	
	//批量获取交换机基本信息
	@SuppressWarnings("unchecked")
	public List<Deviceinfo> batchGetDeviceinfo(String[] idArr){
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Deviceinfo.class);
		Criterion expression=null;
		if(expression != null) c.add(expression).addOrder(Order.asc("name"));
		List<Deviceinfo> list = c.list();
		transaction.commit();
		return list;
		
	}	
	//更新或添加交换机基本信息
	@SuppressWarnings({ "static-access"})
	public void saveDeviceinfo(Deviceinfo deviceinfo){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
//			String sql="INSERT INTO deviceinfo(NAME,equipmentType,ipv4Address,ipv6Address,description,snmpVersion,readCommunity,writeCommunity,authKey,privateKey,STATUS,isDelete) values"+
//			
//			"('"+deviceinfo.getName()+
//			"',"+deviceinfo.getEquipmentType()+",'"+deviceinfo.getIpv4address()+
//			"','"+deviceinfo.getIpv6address()+
//			"','"+deviceinfo.getDescription()+
//			"','"+deviceinfo.getSnmpVersion()+
//			"','"+deviceinfo.getReadCommunity()+
//			"','"+deviceinfo.getWriteCommunity()+
//			"','"+deviceinfo.getAuthKey()+"','"+deviceinfo.getPrivateKey()+"',1,0)";
//			Query query = session.createQuery(sql);
//			query.executeUpdate();
			
			deviceinfo.setStatus(1);
            session.saveOrUpdate(deviceinfo);
			
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}
//
	//通过id获得子网
	@SuppressWarnings({ "static-access"})
	public Iterator<Apinfo> getAcForGlobalView(String acId) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Deviceinfo ac=(Deviceinfo)session.get(Deviceinfo.class, Long.parseLong(acId));
		int userNum=0;
		int apsum=0;
		AcForGlobalView acForGlobalView=new AcForGlobalView();
		acForGlobalView.setId(ac.getId());
		acForGlobalView.setName(ac.getName());
		List<ApForGlobalView> apForGlobalViewList=new ArrayList<ApForGlobalView>();
		Iterator<Apinfo> apinfoIterator=ac.getApinfos().iterator();

		transaction.commit();
		acForGlobalView.setUserNum(userNum);
		acForGlobalView.setApForGlobalViewList(apForGlobalViewList);
		System.out.println(acForGlobalView);
		return apinfoIterator;
	}
	
	// 对应于交换机用户数TopN
	@SuppressWarnings({"unchecked","static-access"})
	public static List getACUserNum(Long acID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql="SELECT DISTINCT s.apid,a.apname,s.ipAddressType,(SELECT COUNT(*) FROM SaviFilterTableCur WHERE apid=s.apid) AS COUNT " +
				"FROM SaviFilterTableCur s,Apinfo a,Deviceinfo d WHERE a.acid=d.id AND s.apid=a.apid AND " +
				"d.status=1 AND a.acid='"+acID+"' AND d.isDelete='0'";
		
//		String sql ="select c.id,b.name,c.ipVersion,(c.staticNum+c.slaacNum+c.dhcpNum) AS userNum"+
//		            " from Deviceinfo c JOIN c.apinfos b  where s.id="+
//		            + subnetID + " and b.status=1 and b.isDelete=0"+
//		            " order by (c.staticNum+c.slaacNum+c.dhcpNum) DESC";
		Query query = session.createQuery(sql);
		List list = query.list();
		System.out.println(list);
		transaction.commit();
		return list;
	}
	
	
	// 获得subnetID子网从当前时间之前24小时内的用户
	@SuppressWarnings({"unchecked","static-access"})
	public List<PreHuaSanUserInfo> getOnlineTimeUsers(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New com.savi.show.dto.PreHuaSanUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,d.status,a.apname) from SaviFilterTableCur t,Apinfo a,Deviceinfo d "+
		" where (t.startTime >=" + millis +") and t.apid=a.apid and a.acid=d.id and d.isDelete=0 and d.status=1 and a.acid="+subnetId;
	
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	
	
	// 获取子网24小时内用户变化信息
	//TODO:使用User替换Savibindingtablehis
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<PreHuaSanUserInfo> getUserChangeInfo(String subnetId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		List list=null;
        try{
        	String sql ="select New com.savi.show.dto.PreHuaSanUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,d.status,a.apname) from SaviFilterTableCur t,Apinfo a,Deviceinfo d "+
    		" where (t.startTime >=" + millis +") and t.apid=a.apid and a.acid=d.id and d.isDelete=0 and t.endTime is null and a.acid="+subnetId +
    		" order by t.startTime desc ";
    	
    		Query query = session.createQuery(sql);
    		list = query.list();
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		transaction.commit();
		return list;
	}
	
	
	//获取子网的绑定表
	@SuppressWarnings({ "static-access", "unchecked"})
	public List<HuaSanBindingTableInfo> getHuaSanBindingTableInfo(String subnetId, String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="";
		List<HuaSanBindingTableInfo> bindingTableInfoList = new LinkedList<HuaSanBindingTableInfo>();
		try{
//			sql = "select New com.savi.show.dto.HuaSanSaviFilterTableCurInfo(t.id,a.apname,t.serviceName,t.ipAddress,t.macAddress,t.userName,t.endTime-t.startTime as count) from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
//			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and a.acid="+subnetId;
			
			sql = "select t.id,a.apname,t.serviceName,t.ipAddress,t.macAddress,t.userName,t.ipAddressType,t.startTime as count from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and t.endTime is null and a.acid="+subnetId +
			" order by t.startTime desc ";	
			
			Query query = session.createQuery(sql);
			
			query.setFirstResult(Integer.parseInt(firstResult));
			query.setMaxResults(Integer.parseInt(maxResult));
			List list = query.list();
			Iterator it = list.iterator();
			List<HuaSanSaviFilterTableCurInfo> newlist=new ArrayList<HuaSanSaviFilterTableCurInfo>();
			while(it.hasNext()){
				HuaSanSaviFilterTableCurInfo info=new HuaSanSaviFilterTableCurInfo();
				Object [] obj = new Object[8];
				obj = (Object[])it.next();
				info.setId(Long.parseLong(obj[0].toString()));
				info.setApName(obj[1].toString());
				info.setServiceName(obj[2].toString());
				info.setIpAddress(obj[3].toString());
				info.setMacAddress(obj[4].toString());
				if(obj[5]!=null){
					info.setUserName(obj[5].toString());
				}
				if(obj[6]!=null){
					info.setIpAddressType(Integer.parseInt(obj[6].toString()));
				}
				if(obj[7] != null) {
					info.setCount(Long.parseLong(obj[7].toString()));
				}
				
				newlist.add(info);
				
			}
			
			System.out.println("::::"+newlist);
			
			sql = "select min(t.startTime) as count from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and a.acid="+subnetId;
			
			query = session.createQuery(sql);
			List maxLifeTimeList=query.list();
			
			System.out.println("****"+maxLifeTimeList);
			
			Long maxLifeTime=0l;
			if(maxLifeTimeList!=null&&maxLifeTimeList.size()!=0&&maxLifeTimeList.get(0)!=null){
				maxLifeTime=Long.parseLong(query.list().get(0).toString());
				if(maxLifeTime < 1 ) maxLifeTime = 1l;
			}
			
			for(int i = 0; i < newlist.size(); i++){
				HuaSanBindingTableInfo bi = null;
				HuaSanSaviFilterTableCurInfo sc = newlist.get(i);
				bi = new HuaSanBindingTableInfo(sc, maxLifeTime);
				bindingTableInfoList.add(bi);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		transaction.commit();
		return bindingTableInfoList;
	}
	
	@SuppressWarnings("unchecked")
	public List<SaviFilterTableCur> getApinfoList(long apid) throws Exception{
		List<SaviFilterTableCur> list =null;
		Session session =hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			Criteria cri=session.createCriteria(SaviFilterTableCur.class);
			cri.add(Restrictions.eq("apid",apid));
			list=cri.list();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	//通过id获得子网
	@SuppressWarnings({ "static-access"})
	public Deviceinfo getAcView(String acId) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Deviceinfo ac=(Deviceinfo)session.get(Deviceinfo.class, Long.parseLong(acId));
		transaction.commit();
		return ac;
	}
	
	
	

	//批量获取SAVI信息
	@SuppressWarnings("unchecked")
	public List<SwitchcurTemp> batchGetSwitchcur(String[] idArr){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchcurTemp(c.ipVersion,c.systemMode,c.maxDadDelay,c.maxDadPrepareDelay,b.id,b.name)"+
		" from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s where b.isDelete=0 ";
		for(int i = 0; i < idArr.length; i++){
			if(i==0){
				sql+=" and b.id="+idArr[i];
			}else{
				sql+=" or b.id="+idArr[i];
			}
		}
		sql+=" ORDER BY b.name ASC";
		Query query = session.createQuery(sql);
		List<SwitchcurTemp> list = query.list();
		transaction.commit();
		return list;
	}
	//搜索交换机基本信息
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<SwitchInfo> searchSwitch(String[] keywordArr, String firstResult,
			String maxResult ){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchbasicinfo.class).
			add(Restrictions.eq("isDelete", 0)).
			setFirstResult(Integer.parseInt(firstResult)).
			setMaxResults(Integer.parseInt(maxResult));
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.add(expression);
		List<Switchbasicinfo> switchBasicList = c.list();
		List<SwitchInfo> switchList=new ArrayList<SwitchInfo>();
		for(int i=0;i<switchBasicList.size();i++){
			Switchbasicinfo s=switchBasicList.get(i);
			SwitchInfo switchInfo=new SwitchInfo(s.getId(),s.getName(),s.getEquipmentType(),s.getIpv4address(),s.getIpv6address(),s.getSubnet().getName()
					,s.getSubnet().getId(),s.getStatus(),s.getDescription(),s.getSnmpVersion(),s.getReadCommunity(),s.getWriteCommunity(),
					s.getAuthKey(),s.getPrivateKey());
			switchList.add(switchInfo);
		}
		transaction.commit();
		return switchList;
	}
	
	//搜索结果结果的总数
	@SuppressWarnings({ "static-access" })
	public int getSearchResultCount(String[] keywordArr){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchbasicinfo.class).
			add(Restrictions.eq("isDelete", 0));
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.add(expression);
		
		int count = (Integer) c.setProjection(Projections.rowCount()).
				uniqueResult();
		transaction.commit();
		return count;
	}
	
	//搜索符合条件的SAVI信息
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<SwitchcurTemp> listSearchSwitchSAVI(String[] keywordArr, String firstResult,
			String maxResult ){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchcur.class).
			setFirstResult(Integer.parseInt(firstResult)).
			setMaxResults(Integer.parseInt(maxResult));
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.createCriteria("switchbasicinfo").add(expression);
		
		List<Switchcur> saviBasicList = c.list();
		List<SwitchcurTemp> saviList=new ArrayList<SwitchcurTemp>();
		for(int i=0;i<saviBasicList.size();i++){
			Switchcur s=saviBasicList.get(i);
			SwitchcurTemp switchcurTemp=new SwitchcurTemp(s.getIpVersion(),s.getSystemMode(),s.getMaxDadDelay(),
					s.getMaxDadPrepareDelay(),s.getSwitchbasicinfo().getId(),s.getSwitchbasicinfo().getName());
			saviList.add(switchcurTemp);
		}
		transaction.commit();
		return saviList;
	}
	
	//搜索符合条件的SAVI信息的数量
	@SuppressWarnings({ "static-access" })
	public int getSearchSwitchSAVICount(String[] keywordArr){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchcur.class);
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("equipmentType", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.or(Restrictions.ilike("ipv4address", keywordArr[i], MatchMode.ANYWHERE),
										Restrictions.or(Restrictions.ilike("ipv6address", keywordArr[i], MatchMode.ANYWHERE),
												Restrictions.ilike("description", keywordArr[i], MatchMode.ANYWHERE)))));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.createCriteria("switchbasicinfo").add(expression);
		
		int count = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	//获取交换机基本信息
	@SuppressWarnings({ "static-access", "unchecked" })
	public Switchbasicinfo getSwitchbasicinfo(Long id){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchbasicinfo> switchbasicinfoList=session.createCriteria(Switchbasicinfo.class).add(Restrictions.eq("id",id)).list();
		if(switchbasicinfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return switchbasicinfoList.get(0);
		
	}
	
	//删除交换机基本信息
	@SuppressWarnings({ "static-access"})
	public void deleteSwitchbasicinfo(Switchbasicinfo switchbasicinfo){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			switchbasicinfo.setIsDelete(1);
			switchbasicinfo.setStatus(0);
			session.update(switchbasicinfo);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
			
	}
	
	//更新或添加交换机基本信息
	@SuppressWarnings({ "static-access"})
	public void saveSwitchbasicinfo(Switchbasicinfo switchbasicinfo){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(switchbasicinfo);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}
	
	//更新交换机SAVI信息
	@SuppressWarnings({ "static-access"})
	public void updateSwitchcur(Switchcur switchcur) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			String hql="update Switchcur s set s.maxDadDelay=" + switchcur.getMaxDadDelay() + 
            ", s.maxDadPrepareDelay=" + switchcur.getMaxDadPrepareDelay() + 
            ", s.systemMode=" + switchcur.getSystemMode() + 
            " where s.ipVersion=" + switchcur.getIpVersion() +
            " and s.switchbasicinfo = :switchbasicinfo";
			Query query = session.createQuery(hql);
			query.setParameter("switchbasicinfo", switchcur.getSwitchbasicinfo());
			query.executeUpdate();
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}
	//获取未被删除的交换机基本信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<com.savi.show.dto.SwitchInfo> getAllExistSwitches(String firstResult,String maxResult) throws Exception {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchInfo(b.id,b.name,b.equipmentType,b.ipv4address,b.ipv6address,s.name,s.id,b.status," +
			"b.description,b.snmpVersion,b.readCommunity,b.writeCommunity,b.authKey,b.privateKey)"+
			" from Switchbasicinfo b JOIN b.subnet s where b.isDelete=0 ORDER BY b.name ASC";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<com.savi.show.dto.SwitchInfo> list = query.list();
		transaction.commit();
		return list;
	}
	
	//获取某些子网下的交换机基本信息
	@SuppressWarnings( { "unchecked" })
	public List<com.savi.show.dto.SwitchInfo> getExistSwitchesBySubnetIds(String[] subnetIds,
			String firstResult,String maxResult) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchInfo(b.id,b.name,b.equipmentType,b.ipv4address,b.ipv6address,s.name,s.id,b.status," +
			"b.description,b.snmpVersion,b.readCommunity,b.writeCommunity,b.authKey,b.privateKey)"+
			" from Switchbasicinfo b JOIN b.subnet s where b.isDelete=0 ";
		for(int i = 0; i < subnetIds.length; i++){
			if(i==0){
				sql+=" and s.id="+subnetIds[i];
			}else{
				sql+=" or s.id="+subnetIds[i];
			}
			
		}
		sql+=" ORDER BY b.name ASC";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<com.savi.show.dto.SwitchInfo> list = query.list();
		transaction.commit();
		return list;
	}

	//获取某些子网下的savi基本信息
	@SuppressWarnings( { "unchecked"})
	public List<SwitchcurTemp> getSavisBySubnetIds(String[] subnetIds,
			String firstResult,String maxResult) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchcurTemp(c.ipVersion,c.systemMode,c.maxDadDelay,c.maxDadPrepareDelay,b.id,b.name)"+
			" from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s where b.isDelete=0 ";
		for(int i = 0; i < subnetIds.length; i++){
			if(i==0){
				sql+=" and s.id="+subnetIds[i];
			}else{
				sql+=" or s.id="+subnetIds[i];
			}
			
		}
		sql+=" ORDER BY b.name ASC";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<SwitchcurTemp> list = query.list();
		transaction.commit();
		return list;
	}
	//获取某些子网下的交换机总数
	public int getExistSwitchesBySubnetIdsCount(String[] subnetIds) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchbasicinfo.class);
		Criterion expression=null;
		int count=0;
		for(int i = 0; i < subnetIds.length; i++){
			if(expression == null)
				expression = Restrictions.eq("subnet.id", Long.parseLong(subnetIds[i]));
			else
				expression = Restrictions.or(expression, 
						Restrictions.eq("subnet.id", Long.parseLong(subnetIds[i])));
		}		
		if(expression != null){
			expression=Restrictions.and(expression, Restrictions.eq("isDelete", 0));
			count=(Integer)c.add(expression).setProjection(
					Projections.rowCount()).uniqueResult();
		}
		transaction.commit();
		return count;
	}
	
	//获取某些子网下的Savi总数
	public int getSavisBySubnetIdsCount(String[] subnetIds) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Switchcur.class).createCriteria("switchbasicinfo");
		Criterion expression=null;
		int count=0;
		for(int i = 0; i < subnetIds.length; i++){
			Subnet subnet=new Subnet(Long.parseLong(subnetIds[i]));
			//Switchbasicinfo switchbasicinfo=new Switchbasicinfo(subnet);
			if(expression == null)
				expression = Restrictions.eq("subnet", subnet);
			else
				expression = Restrictions.or(expression, 
						Restrictions.eq("subnet", subnet));
		}		
		if(expression != null){
			expression=Restrictions.and(expression, Restrictions.eq("isDelete", 0));
			count=(Integer)c.add(expression).setProjection(
					Projections.rowCount()).uniqueResult();
		}
		transaction.commit();
		return count;
	}
	/*
	//获取所有正在工作的交换机
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<Switchcur> getSwitchcurs(String firstResult, String maxResult)
			throws Exception {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchcurList = session.createCriteria(Switchcur.class).createCriteria("switchbasicinfo").
			add(Restrictions.eq("isDelete", 0))
				.setFirstResult(Integer.parseInt(firstResult)).setMaxResults(
						Integer.parseInt(maxResult)).addOrder(Order.asc("name")).list();
		transaction.commit();
		return switchcurList;
	}
	*/
	//获取所有正在工作的交换机
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<SwitchcurTemp> getSwitchcurs(String firstResult, String maxResult)
			throws Exception {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchcurTemp(c.ipVersion,c.systemMode,c.maxDadDelay,c.maxDadPrepareDelay,b.id,b.name)"+
			" from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s where b.isDelete=0 ORDER BY b.name ASC";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<SwitchcurTemp> list = query.list();
		transaction.commit();
		return list;
	}
	//获取一个正在工作的交换机
	@SuppressWarnings( { "unchecked", "static-access" })
	public Switchcur getSwitchcurByIPVersionAndSwitchId(int ipVersion, long switchbasicinfoId)
			throws Exception {
		Switchbasicinfo s = new Switchbasicinfo();
		s.setId(switchbasicinfoId);
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction traction=session.beginTransaction();
		List<Switchcur> switchcurList = session.createCriteria(Switchcur.class)
				.add(Restrictions.eq("ipVersion", ipVersion))
				.add(Restrictions.eq("switchbasicinfo", s))
				.list();
		traction.commit();
		if(switchcurList.size()==0) return null;
		return switchcurList.get(0);
	}
	@SuppressWarnings( { "unchecked", "static-access" })
	public SwitchInfoForDetail getSwitchcurTempByIPVersionAndSwitchId(int ipVersion, long switchbasicinfoId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SwitchInfoForDetail(b.name,s.name,c.staticNum,c.slaacNum,c.dhcpNum,b.ipv4address,"+
			"b.ipv6address,c.ipVersion,b.equipmentType,b.description)"+
			" from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s where b.id="+switchbasicinfoId+" and c.ipVersion="+ipVersion;
		Query query = session.createQuery(sql);
		List<SwitchInfoForDetail> list = query.list();
		transaction.commit();
		if(list.size()==0) return null;
		return list.get(0);
	}

	//未被删除的交换机数量基本信息数量
	@SuppressWarnings("static-access")
	public int getExistSwitchesCount() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer) session.createCriteria(Switchbasicinfo.class)
				.add(Restrictions.eq("isDelete", 0)).setProjection(
						Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}

	//获取正在工作的交换机数量	
	@SuppressWarnings("static-access")
	public int getSwitchcursCount() {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer) session.createCriteria(Switchcur.class).createCriteria("switchbasicinfo")
				.add(Restrictions.eq("isDelete", 0))
				.setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	
	//获取正在工作的交换机的所有端口信息
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<InterfaceInfo> getSwitchInterfaces(long switchcurId, String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<InterfaceInfo> iiList = new LinkedList();
		
		String sql = "select i  from Ifinterfacecur i " +
				"join i.switchcur s where s.id=" + switchcurId;
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		
		List<Ifinterfacecur> list = query.list();
		
		for(int i=0; i < list.size(); i++){
			Ifinterfacecur icur = list.get(i);
			sql = "select count(*) from Savibindingtablecur s " +
					"join s.ifinterfacecur i where s.isInFilteringTable=1 " +
					"and i.id = " + icur.getId();
			query = session.createQuery(sql);
			int userNum;
			if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
				userNum = Integer.parseInt(query.list().get(0).toString());
			}else{
				userNum=0;
			}
			InterfaceInfo ii = new InterfaceInfo(icur, userNum);
			iiList.add(ii);
		}
		transaction.commit();
		return iiList;
	}
	
	//获取正在工作的交换机的所有端口数量
	@SuppressWarnings({ "static-access" })
	public int getSwitchInterfacesCount(long switchcurId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		
		String sql = "select count(i)  from Ifinterfacecur i " +
				"join i.switchcur s where s.id=" + switchcurId;
		Query query = session.createQuery(sql);
		
		int count = Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return count;
	}

	// 对应于交换机用户数TopN
	@SuppressWarnings({"unchecked","static-access"})
	public List getSwitchUserNum(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select c.id,b.name,c.ipVersion,(c.staticNum+c.slaacNum+c.dhcpNum) AS userNum"+
		            " from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s where s.id="+
		            + subnetID + " and b.status=1 and b.isDelete=0"+
		            " order by (c.staticNum+c.slaacNum+c.dhcpNum) DESC";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}

	// 获得类型是savi-disable的交换机个数
	@SuppressWarnings({"static-access"})
	public int getSaviDisableSwitchCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(c.id) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where c.systemMode=1 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得类型是savi-default的交换机个数
	@SuppressWarnings({"static-access"})
	public int getSaviDefaultSwitchCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(c.id) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where c.systemMode=2 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得类型是savi-dhcp-only的交换机个数
	@SuppressWarnings({"static-access"})
	public int getSaviDhcpOnlySwitchCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(c.id) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where c.systemMode=3 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得类型是savi-slaac-only的交换机个数
	@SuppressWarnings({"static-access"})
	public int getSaviSlaacOnlySwitchCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(c.id) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where c.systemMode=4 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得类型是savi-dhcp-slaac-mix的交换机个数
	@SuppressWarnings({"static-access"})
	public int getSaviDhcpSlaacMixSwitchCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select count(c.id) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where c.systemMode=5 and b.status=1 and b.isDelete=0 and s.id="+subnetID;
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
/*
	// 获得subnetID子网从当前时间之前24小时内的用户
	@SuppressWarnings("System.currentTimeMillis()")
	public List getSwitchhisStaticNumCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New Switchhis(h.timeStamp,h.staticNum,h.slaacNum,h.dhcpNum) from Switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		            " where h.timeStamp > "+ millis +" and s.id="+subnetID;
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	// 获得switchbasicinfoID交换机从当前时间之前24小时内的用户
	@SuppressWarnings("System.currentTimeMillis()")
	public List getSwitchhisNumCount(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New Switchhis(h.timeStamp,h.staticNum,h.slaacNum,h.dhcpNum) from Switchhis h JOIN h.switchbasicinfo b"+
		            " where h.timeStamp > "+ millis +" and h.ipVersion=(select c.ipVersion from Switchcur c where c.id="+switchID+")"+
		            " and b.id=(select b.id from Switchcur c JOIN c.switchbasicinfo b where c.id="+switchID+")";
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
*/
	// 获得subnetID子网IP绑定类型是 static的用户数
	@SuppressWarnings({"static-access"})
	public int getStaticNumCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select SUM(c.staticNum) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得subnetID子网IP绑定类型是 slaac的用户数
	@SuppressWarnings({"static-access"})
	public int getSlaacNumCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select SUM(c.slaacNum) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where b.status=1 and b.isDelete=0 and s.id="+subnetID;
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

	// 获得subnetID子网IP绑定类型是 dhcp的用户数
	@SuppressWarnings({"static-access"})
	public int getDhcpNumCount(Long subnetID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select SUM(c.dhcpNum) from Switchcur c JOIN c.switchbasicinfo b JOIN b.subnet s"+
		            " where b.status=1 and b.isDelete=0 and s.id="+subnetID;
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
	
	// 对应于交换机视图中的Filtering表项数
	@SuppressWarnings({"static-access"})
	public long getSwitchFilteringRecordNum(Long switchID){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select c.staticNum+c.slaacNum+c.dhcpNum"+
		            " from Switchcur c where c.id="+ switchID ;
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
	
	//获得staticNum,slaacNum,dhcpNum三个字段中最大的值，并求和
	@SuppressWarnings({"static-access"})
	public int getSwitchMaxFilteringRecordNum(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql ="select MAX(c.staticNum)+MAX(c.slaacNum)+MAX(c.dhcpNum) from Switchcur c";
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
	
	//获取交换机的绑定表
	@SuppressWarnings({ "static-access", "unchecked"})
	public List<BindingTableInfo> getBindingTableInfo(Long switchId, String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select savi from Savibindingtablecur savi " +
				"join savi.ifinterfacecur ifc " +
				"join ifc.switchcur sc " +
				"where sc.id = '" + switchId + "'";
		
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<Savibindingtablecur> list = query.list();
		
		sql = "select max(savi.bindingLifetime) from Savibindingtablecur savi";
		query = session.createQuery(sql);
		int maxLifeTime;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			maxLifeTime = Integer.parseInt(query.list().get(0).toString());
		}else{
			maxLifeTime=0;
		}
		if(maxLifeTime < 1 ) maxLifeTime = 1;		
		List<BindingTableInfo> bindingTableInfoList = new LinkedList<BindingTableInfo>();
		for(int i = 0; i < list.size(); i++){
			BindingTableInfo bi = null;
			Savibindingtablecur sc = list.get(i);
			sql = "select u from User u where u.status=1 and u.ipAddress='"
				+ sc.getIpAddress() + "'";
			query = session.createQuery(sql);
			List<User> userList = query.list();
			
			if(userList.size() == 0) bi = new BindingTableInfo(sc, maxLifeTime,null);
			else bi = new BindingTableInfo(sc, maxLifeTime, userList.get(0).getName());
				
			bindingTableInfoList.add(bi);
		}
		transaction.commit();
		return bindingTableInfoList;
	}
	
	//获取交换机的绑定表项数目
	@SuppressWarnings("static-access")
	public int getBindingTableInfoNum(Long switchId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction=session.beginTransaction();
		String sql = "select count(savi) as num from Savibindingtablecur savi " +
				"join savi.ifinterfacecur ifc " +
				"join ifc.switchcur sc " +
				"where sc.id  = '" + switchId + "'";
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
	
	// 获取交换机24小时内用户变化信息
	//TODO:使用User替换Savibindingtablehis
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<PreUserInfo> getUserChangeInfo(Long switchId, String firstResult, String maxResult) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
//		String sql = "select u from User u left join u.ifinterfacehis ifhis " +
//				"left join ifhis.switchhis shis " +
//				"left join shis.switchbasicinfo sinfo, " +
//				"in (sinfo.switchcurs) sc " + 
//				"where u.startTime >= " + millis +" and " +
//				"sc.id = '" + switchId + "'";
		/*
		String sql = "select u from Savibindingtablehis u left join u.ifinterfacehis ifhis " +
			"left join ifhis.switchhis shis " +
			"left join shis.switchbasicinfo sinfo, " +
			"in (sinfo.switchcurs) sc " + 
			"where (u.startTime >= " + millis +" or u.status=1 or u.endTime>" +millis+") and " +
			"sc.id = '" + switchId + "'";
		*/
		String sql = "select New com.savi.show.dto.PreUserInfo(u.id,u.ipAddressType,u.userName,u.ipAddress,u.startTime,u.endTime,u.status,ifhis.ifIndex,sinfo.name) from Savibindingtablehis u join u.ifinterfacehis ifhis " +
		"join ifhis.switchhis shis " +
		"join shis.switchbasicinfo sinfo, " +
		"in (sinfo.switchcurs) sc " + 
		"where (u.startTime >= " + millis +" or u.status=1 or u.endTime>" +millis+") and " +
		"sc.id = '" + switchId + "'";
		Query query = session.createQuery(sql);
		//query.setFirstResult(Integer.parseInt(firstResult));
		//query.setMaxResults(Integer.parseInt(maxResult));
		List list = query.list();
		transaction.commit();
		return list;
	}
/*
	// 获取交换机24小时内用户数
	@SuppressWarnings( {"static-access" })
	public int getUserChangeInfoNum(String switchId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql = "select count(u) as count from User u left join u.ifinterfacehis ifhis " +
				"left join ifhis.switchhis shis " +
				"left join shis.switchbasicinfo sinfo, " +
				"in (sinfo.switchcurs) sc " + 
				"where u.startTime >= " + millis +" and " +
				"sc.id = '" + switchId + "'";
		Query query = session.createQuery(sql);
		int count;
		if(query.list()!=null&&query.list().size()>0&&query.list().get(0)!=null){
			count = Integer.parseInt(query.list().get(0).toString());
		}else{
			count=0;
		}
		transaction.commit();
		return count;
	}
*/	
	//实时获取交换机接口信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<InterfaceInfo> getRealTimeInterfaceList(Long switchId, String firstResult, String maxResult, List<Integer> totalCount){
		List<InterfaceInfo> list = new LinkedList<InterfaceInfo>();
		int counter=0;
		long maxFilteringNum=0;
		int start=Integer.parseInt(firstResult);
		int end=Integer.parseInt(firstResult)+Integer.parseInt(maxResult)-1;
		Switchcur switchCur;
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("id",Long.valueOf(switchId))).list();
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
		String ifTableResult[][]=snmpGetTable.getTableData("saviObjectsIfTable");
		if(ifTableResult!=null){
			for(int i=1;i<ifTableResult.length;i++){
				if(Integer.parseInt(ifTableResult[i][0])==switchCur.getIpVersion().intValue()){
					if(counter>=start&&counter<=end){
						InterfaceInfo interfaceInfo=new InterfaceInfo();
						interfaceInfo.setIpVersion(Integer.valueOf(ifTableResult[i][0]));
						interfaceInfo.setIfIndex(Integer.valueOf(ifTableResult[i][1]));
						interfaceInfo.setIfValidationStatus(Integer.valueOf(ifTableResult[i][2]));
						interfaceInfo.setIfTrustStatus(Integer.valueOf(ifTableResult[i][3]));
						interfaceInfo.setIfFilteringNum(Long.valueOf(ifTableResult[i][4]));
						list.add(interfaceInfo);
					}
					if(maxFilteringNum<Long.parseLong(ifTableResult[i][4])){
						maxFilteringNum=Long.parseLong(ifTableResult[i][4]);
					}
					counter++;
				}					
			}
			for(int j=0;j<list.size();j++){
				list.get(j).setMaxFilteringNum(maxFilteringNum);
			}
		}
		totalCount.add(counter);//表项的总数
		transaction.commit();
		return list;
	}
	
	//实时获取交换机绑定表信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<BindingTableInfo> getRealTimeBindingTableInfoList(Long switchId, String firstResult, String maxResult, List<Integer> totalCount){
		List<BindingTableInfo> list = new LinkedList<BindingTableInfo>();
		int counter=0,maxLifeTime=0;
		int start=Integer.parseInt(firstResult);
		int end=Integer.parseInt(firstResult)+Integer.parseInt(maxResult)-1;
		Switchcur switchCur;
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("id",Long.valueOf(switchId))).list();
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
			/*
			 * timeoutFlag是为了保证：当本次操作中只要有一次向认证服务器请求用户名超时的话，之后的获取用户名操作全都停止，只设置用户名为空即可。
			 */
			ArrayList<Integer> timeoutFlag=new ArrayList<Integer>();
			timeoutFlag.add(0);
			for(int i=1;i<bindingTableResult.length;i++){
				if(Integer.parseInt(bindingTableResult[i][0])==switchCur.getIpVersion().intValue()){
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
						if(timeoutFlag.get(0)==0){
							bindingTableInfo.setUser(WSUtil.getUserName(bindingTableInfo.getIpAddress(),timeoutFlag));
						}else{
							bindingTableInfo.setUser("");
						}
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
	
	//根据绑定类型实时获取交换机绑定表信息
	@SuppressWarnings({"unchecked","static-access"})
	public List<BindingTableInfo> getRealTimeBindingTableInfoListByType(Long switchId, Integer type, String firstResult, String maxResult, List<Integer> totalCount){
		List<BindingTableInfo> list = new LinkedList<BindingTableInfo>();
		int counter=0,maxLifeTime=0;
		int start=Integer.parseInt(firstResult);
		int end=Integer.parseInt(firstResult)+Integer.parseInt(maxResult)-1;
		Switchcur switchCur;
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Switchcur> switchCurList=session.createCriteria(Switchcur.class).add(Restrictions.eq("id",Long.valueOf(switchId))).list();
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
	
	//PA
	// 获取子网24小时内用户变化信息
	//TODO:使用User替换Savibindingtablehis
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<PreHuaSanUserInfo> getPAUserChangeInfo(String subnetId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		List list=null;
        try{
        	String sql ="select New com.savi.show.dto.PreHuaSanUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,d.status,a.apname) from SaviFilterTableCur t,Apinfo a,Deviceinfo d "+
    		" where (t.startTime >=" + millis +") and t.apid=a.apid and a.acid=d.id and d.isDelete=0 and t.endTime is null and a.apid="+subnetId + 
    		" order by t.startTime desc";
    	
    		Query query = session.createQuery(sql);
    		list = query.list();
        	
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		transaction.commit();
		return list;
	}
	
//PA
	
	//获取子网的绑定表
	@SuppressWarnings({ "static-access", "unchecked"})
	public List<HuaSanBindingTableInfo> getHuaSanPABindingTableInfo(String subnetId, String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="";
		List<HuaSanBindingTableInfo> bindingTableInfoList = new LinkedList<HuaSanBindingTableInfo>();
		try{
//			sql = "select New com.savi.show.dto.HuaSanSaviFilterTableCurInfo(t.id,a.apname,t.serviceName,t.ipAddress,t.macAddress,t.userName,t.endTime-t.startTime as count) from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
//			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and a.acid="+subnetId;
			
			sql = "select t.id,a.apname,t.serviceName,t.ipAddress,t.macAddress,t.userName,t.ipAddressType,t.startTime as count from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and t.endTime is null and a.apid="+subnetId + 
			" order by t.startTime desc ";	
			
			Query query = session.createQuery(sql);
			
			query.setFirstResult(Integer.parseInt(firstResult));
			query.setMaxResults(Integer.parseInt(maxResult));
			List list = query.list();
			Iterator it = list.iterator();
			List<HuaSanSaviFilterTableCurInfo> newlist=new ArrayList<HuaSanSaviFilterTableCurInfo>();
			while(it.hasNext()){
				HuaSanSaviFilterTableCurInfo info=new HuaSanSaviFilterTableCurInfo();
				Object [] obj = new Object[8];
				obj = (Object[])it.next();
				info.setId(Long.parseLong(obj[0].toString()));
				info.setApName(obj[1].toString());
				info.setServiceName(obj[2].toString());
				info.setIpAddress(obj[3].toString());
				info.setMacAddress(obj[4].toString());
				if(obj[5]!=null){
					info.setUserName(obj[5].toString());
				}
				if(obj[6]!=null){
					info.setIpAddressType(Integer.parseInt(obj[6].toString()));
				}
				if(obj[7] != null) {
					info.setCount(Long.parseLong(obj[7].toString()));
				}
				
				newlist.add(info);
				
			}
			
			System.out.println("::::"+newlist);
			
			sql = "select min(t.startTime) as count from SaviFilterTableCur t ,Apinfo a,Deviceinfo d" +
			" where t.apid=a.apid and a.acid=d.id and d.isDelete=0 and a.apid="+subnetId;
			
			query = session.createQuery(sql);
			List maxLifeTimeList=query.list();
			
			System.out.println("****"+maxLifeTimeList);
			
			Long maxLifeTime=0l;
			if(maxLifeTimeList!=null&&maxLifeTimeList.size()!=0&&maxLifeTimeList.get(0)!=null){
				maxLifeTime=Long.parseLong(query.list().get(0).toString());
				if(maxLifeTime < 1 ) maxLifeTime = 1l;
			}
			
			for(int i = 0; i < newlist.size(); i++){
				HuaSanBindingTableInfo bi = null;
				HuaSanSaviFilterTableCurInfo sc = newlist.get(i);
				bi = new HuaSanBindingTableInfo(sc, maxLifeTime);
				bindingTableInfoList.add(bi);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		transaction.commit();
		return bindingTableInfoList;
	}
	
     /**0621ADD**/
	
	//通过id获得子网
	@SuppressWarnings({ "static-access"})
	public SaviFilterTableCur getSaviFilterTableCur(Long switchbasicinfoId) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<SaviFilterTableCur> apInfoList=session.createCriteria(SaviFilterTableCur.class).add(Restrictions.eq("apid",switchbasicinfoId)).list();
		if(apInfoList.size()==0){
			transaction.commit();
			return null;
		}
		transaction.commit();
		return apInfoList.get(0);
		
	}
	
	// 获得subnetID子网从当前时间之前24小时内的用户
	@SuppressWarnings({"unchecked","static-access"})
	public List<PreHuaSanUserInfo> getAPOnlineTimeUsers(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql ="select New com.savi.show.dto.PreHuaSanUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,d.status,a.apname) from SaviFilterTableCur t,Apinfo a,Deviceinfo d "+
		" where (t.startTime >=" + millis +") and t.apid=a.apid and a.acid=d.id and d.isDelete=0 and d.status=1 and a.apid="+subnetId;
	
		Query query = session.createQuery(sql);
		List list = query.list();
		transaction.commit();
		return list;
	}
	
	// 获得subnetID子网从当前时间之前24小时内的用户
	@SuppressWarnings({"unchecked","static-access"})
	public List<PreHuaSanUserInfo> getAPOnlineTimeUsersMouth(String subnetId){

		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		  Calendar c = Calendar.getInstance();
		  c.add(Calendar.MONTH, -1);
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
		  String time = sdf.format(c.getTime());
		  Long millis;
		  List list=null;
		try {
			//Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000*30;//毫秒
			millis = sdf.parse(time).getTime();
			String sql ="select New com.savi.show.dto.PreHuaSanUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,d.status,a.apname) from SaviFilterTableCur t,Apinfo a,Deviceinfo d "+
			" where (t.startTime >=" + millis +") and t.apid=a.apid and a.acid=d.id and d.isDelete=0 and d.status=1 and a.apid="+subnetId;
			Query query = session.createQuery(sql);
			list = query.list();
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		transaction.commit();
		return list;
	}
	
	 public static void main(String[] args) {
		 
		 List list=getACUserNum(1l);
		 System.out.println(list);

	 }
	 
	
}
