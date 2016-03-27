package com.asset.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.asset.dto.AssetList;
import com.asset.dto.AssetSum;
import com.base.model.AssetDepart;
import com.base.model.AssetInfo;
import com.base.model.AssetUser;


import com.base.util.HibernateUtil;



public class AssetInfoDAO {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	/**
	 * 获得规定数目的项目记录
	 * @param firstResult
	 * @param maxResult
	 * @return
	 * @throws Exception 
	 */
	public List<AssetList> getAssetInfos(String firstResult, String maxResult) throws Exception {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<AssetList> assetInfos =new ArrayList<AssetList>();
		String sql = "select * from asset_info  limit " + firstResult + "," + maxResult ;
		List tempassetInfos = session.createSQLQuery(sql).list();
	       for(Iterator its = tempassetInfos.iterator();its.hasNext();){
			Object[] objs =(Object[])its.next();
			AssetList assetList = new AssetList();
			AssetDepart departnames = (AssetDepart)session.get(AssetDepart.class, (Long) objs[3]);
			if (objs[0].equals("")){
			}else{
			String departname = departnames.getName();
			assetList.setDepartmentName(departname);};
			AssetUser adminnames = (AssetUser)session.get(AssetUser.class, (Long) objs[14]);
			AssetUser bakadminnames = (AssetUser)session.get(AssetUser.class, (Long) objs[16]);
			if (objs[14].equals(""))
			{}
			else{
			String adminname= adminnames.getUserName();
			assetList.setAdminName(adminname);
			};
			if (objs[16].equals("")){}else{
			String bakamdinname= bakadminnames.getUserName();
			assetList.setBackupAdminName(bakamdinname);
			};
		    assetList.setId((Long)objs[0]);
			assetList.setPurchaseMoney((Float)objs[7]);
			assetList.setDescription(objs[21].toString());
			assetList.setDeviceArrange(objs[23].toString());
			assetList.setDeviceClasses(objs[2].toString());
			assetList.setDeviceConfig(objs[5].toString());
			assetList.setDeviceIP(objs[22].toString());
			assetList.setDeviceLocation(objs[17].toString());
			assetList.setDeviceModel(objs[6].toString());
			assetList.setDeviceName(objs[4].toString());
			assetList.setDeviceNumber(objs[1].toString());
			assetList.setDeviceStatus(objs[24].toString());
			assetList.setDeviceUses(objs[11].toString());
			assetList.setDeviceVender(objs[8].toString());
			assetList.setDiscardDate(objs[19].toString());
			assetList.setDomainName(objs[12].toString());
			assetList.setEstablishDate(objs[13].toString());
			assetList.setFundSource(objs[20].toString());
			assetList.setMaintainRecord(objs[18].toString());
			assetList.setOwnAdminId(objs[15].toString());
			assetList.setProduceNumber(objs[9].toString());
			assetList.setPurchaseDate(objs[10].toString());
	        assetInfos.add(assetList);
	 }
		transaction.commit();
		return assetInfos;
	}
	public List<AssetInfo> getAssetInfoQuerys(AssetInfo assetQuerys) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria =session.createCriteria(AssetInfo.class);
         criteria.add( Restrictions.like("deviceName",assetQuerys.getDeviceName(),MatchMode.ANYWHERE));//模糊查询
		 criteria.add( Restrictions.like("deviceClasses",assetQuerys.getDeviceClasses(),MatchMode.ANYWHERE));		 
		 criteria.add( Restrictions.like("deviceConfig",assetQuerys.getDeviceConfig(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceModel",assetQuerys.getDeviceModel(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceVender",assetQuerys.getDeviceVender(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("produceNumber",assetQuerys.getProduceNumber(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("purchaseDate",assetQuerys.getPurchaseDate(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceUses",assetQuerys.getDeviceUses(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("domainName",assetQuerys.getDomainName(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("establishDate",assetQuerys.getEstablishDate(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("ownAdminId",assetQuerys.getOwnAdminId(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceLocation",assetQuerys.getDeviceLocation(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("maintainRecord",assetQuerys.getMaintainRecord(),MatchMode.ANYWHERE));
   		criteria.add( Restrictions.like("discardDate",assetQuerys.getDiscardDate(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("fundSource",assetQuerys.getFundSource(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("description",assetQuerys.getDescription(),MatchMode.ANYWHERE));
		criteria.add( Restrictions.like("deviceIP",assetQuerys.getDeviceIP(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceArrange",assetQuerys.getDeviceArrange(),MatchMode.ANYWHERE));
		 criteria.add( Restrictions.like("deviceStatus",assetQuerys.getDeviceStatus(),MatchMode.ANYWHERE));
		 if (assetQuerys.getDeviceNumber().isEmpty()){}else{
		 criteria.add(Restrictions.like("deviceNumber",assetQuerys.getDeviceNumber(),MatchMode.EXACT));
			};
			if (assetQuerys.getPurchaseMoney()>0){
				criteria.add(Restrictions.eq("purchaseMoney",new Float(assetQuerys.getPurchaseMoney())));
			};
			if (assetQuerys.getAdminId()>0){
				criteria.add(Restrictions.eq("adminId",new Long (assetQuerys.getAdminId())));
			};
			if (assetQuerys.getBackupAdminId()>0){
				criteria.add( Restrictions.eq("backupAdminId",new Long(assetQuerys.getBackupAdminId())));
			};
			if (assetQuerys.getDepartmentId()>0){
				criteria.add( Restrictions.eq("departmentId",new Long(assetQuerys.getDepartmentId())));
			};
		 List<AssetInfo> assetInfoQuerys = criteria.list();
	     transaction.commit();
		return assetInfoQuerys;
	}
	public List<AssetSum> getAssetInfoSum(String groupsum) throws  Exception{
		List<AssetSum> assetSumlist =new ArrayList<AssetSum>();
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
        List t =session.createCriteria(AssetInfo.class).setProjection(Projections.projectionList()
                		.add(Projections.sum("purchaseMoney"))
                		.add(Projections.alias(Projections.rowCount(), "count"))
                		.add(Projections.groupProperty(groupsum)))
                		.list();
		if (groupsum == "deviceModel"){
			groupsum = "按设备类型分类";
		};
		if (groupsum == "deviceVender"){
			groupsum = "按设备厂家分类";
		};
		if (groupsum == "deviceStatus"){
			groupsum = "按设备状况分类";
		};
		if (groupsum == "departmentId"){
			groupsum = "按使用部门分类";
		};
		for(Iterator it = t.iterator();it.hasNext();){
			Object[] objs =(Object[])it.next();
			AssetSum assetSum = new AssetSum();
			if (objs[2].equals("")){objs[2]="其他";};
			assetSum.setTotalmoney((Float) objs[0]);
			assetSum.setTotalname(objs[2].toString());
			if(groupsum == "按使用部门分类"){
				AssetDepart tempobjs = (AssetDepart)session.get(AssetDepart.class, (Long) objs[2]);
				if (tempobjs==null){
				}else{
					String departname = tempobjs.getName();
					assetSum.setTotalname(departname);
					};
	//			System.out.println("objs="+objs[2]);
	//			System.out.println("name"+tempobjs);
				
			};
			assetSum.setTotalnumber((Integer) objs[1]);
			assetSum.setGroupname(groupsum);
			assetSumlist.add(assetSum);
		}
         transaction.commit();
		return assetSumlist;
	}
	public String showdepartname(long id) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetDepart assetDepart = (AssetDepart)session.get(AssetDepart.class, id);
		String departname = assetDepart.getName();
		return departname;
	}
	public int getAssetInfosCount(){
		Session session = new HibernateUtil().getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(AssetInfo.class).setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		return count;
	}
	public void delete(Long assetInfoId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetInfo assetInfo = (AssetInfo)session.get(AssetInfo.class, assetInfoId);
		if(assetInfo!=null){
			session.delete(assetInfo);
		}
		transaction.commit();
	}
	public AssetInfo getassetInfoById(long id) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		AssetInfo assetInfo = (AssetInfo)session.get(AssetInfo.class, id);
		transaction.commit();
		return assetInfo;
	}
	public boolean checkassetInfoNameIsExist(String assetInfoName){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<AssetInfo> assetInfos = session.createCriteria(AssetInfo.class).add(Restrictions.eq("deviceName", assetInfoName)).list();
		transaction.commit();
		return !assetInfos.isEmpty();
	}
	public AssetInfo save(AssetInfo assetInfo) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
//		System.out.println("assetInfo="+assetInfo);
		session.saveOrUpdate(assetInfo);
		transaction.commit();
		return assetInfo;
	}
}
