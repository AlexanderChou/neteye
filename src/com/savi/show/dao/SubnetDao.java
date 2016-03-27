package com.savi.show.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.savi.base.model.Savibindingtablecur;
import com.savi.base.model.Subnet;
import com.savi.base.model.Switchbasicinfo;
import com.savi.base.model.Switchcur;
import com.savi.base.util.HibernateUtil;
import com.savi.show.dto.BindingTableInfo;
import com.savi.show.dto.PreUserInfo;
import com.savi.show.dto.SubnetForGlobalView;
import com.savi.show.dto.SubnetTemp;
import com.savi.show.dto.SwitchForGlobalView;
//import org.springframework.orm.hibernate.support.HibernateDaoSupport;
public class SubnetDao {
	private HibernateUtil hibernateUtil = new HibernateUtil();
	
	//搜索子网
	@SuppressWarnings({ "static-access", "unchecked" })
	public List<SubnetForGlobalView> searchSubnet(String[] keywordArr, String firstResult,
			String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Subnet.class).
			add(Restrictions.eq("isDelete", 0)).
			setFirstResult(Integer.parseInt(firstResult)).
			setMaxResults(Integer.parseInt(maxResult));
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("ipv4subNet", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.ilike("ipv6subNet", keywordArr[i], MatchMode.ANYWHERE)));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("ipv4subNet", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.ilike("ipv6subNet", keywordArr[i], MatchMode.ANYWHERE)));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.add(expression);	
		List<Subnet> subnetBaseList = c.list();
		List<SubnetForGlobalView> subnetList=new ArrayList<SubnetForGlobalView>();
		for(int i=0;i<subnetBaseList.size();i++){
			int switchNum=0;
			int userNum=0;
			SubnetForGlobalView subnetForGlobalView=new SubnetForGlobalView();
			Subnet subnet=subnetBaseList.get(i);
			subnetForGlobalView.setId(subnet.getId());
			subnetForGlobalView.setName(subnet.getName());
			List<SwitchForGlobalView> switchForGlobalViewList=new ArrayList<SwitchForGlobalView>();
			Iterator<Switchbasicinfo> switchbasicinfoIterator=subnet.getSwitchbasicinfos().iterator();
			while(switchbasicinfoIterator.hasNext()){
				Switchbasicinfo switchbasicinfo=switchbasicinfoIterator.next();
				if(switchbasicinfo.getIsDelete()!=null&&switchbasicinfo.getIsDelete().intValue()==1){
					continue;
				}
				Iterator<Switchcur> switchcurIterator=switchbasicinfo.getSwitchcurs().iterator();
				while(switchcurIterator.hasNext()){
					Switchcur switchcur=switchcurIterator.next();
					SwitchForGlobalView switchForGlobalView=new SwitchForGlobalView();
					switchForGlobalView.setSwitchBasicInfoId(switchbasicinfo.getId());
					switchForGlobalView.setIpVersion(switchcur.getIpVersion());
					switchForGlobalView.setSystemMode(switchcur.getSystemMode());
					switchForGlobalView.setUserNum(switchcur.getDhcpNum()+switchcur.getSlaacNum()+switchcur.getStaticNum());
					switchForGlobalViewList.add(switchForGlobalView);
					switchNum++;
					userNum+=switchForGlobalView.getUserNum();
				}
			}
			subnetForGlobalView.setSwitchNum(switchNum);
			subnetForGlobalView.setUserNum(userNum);
			subnetForGlobalView.setSwitchForGlobalViewList(switchForGlobalViewList);
			subnetList.add(subnetForGlobalView);
		}
		transaction.commit();
		return subnetList;
	}
	
	//搜索子网结果的总数
	@SuppressWarnings({ "static-access"})
	public int getSearchResultCount(String[] keywordArr){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria c = session.createCriteria(Subnet.class).
			add(Restrictions.eq("isDelete", 0));
		
		LogicalExpression expression=null;
		for(int i = 0; i < keywordArr.length; i++){
			if(expression == null)
				expression = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("ipv4subNet", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.ilike("ipv6subNet", keywordArr[i], MatchMode.ANYWHERE)));
			else{
				LogicalExpression temp = Restrictions.or(Restrictions.ilike("name", keywordArr[i], MatchMode.ANYWHERE),
						Restrictions.or(Restrictions.ilike("ipv4subNet", keywordArr[i], MatchMode.ANYWHERE),
								Restrictions.ilike("ipv6subNet", keywordArr[i], MatchMode.ANYWHERE)));
				expression = Restrictions.or(expression, temp);
			}
		}
		
		if(expression != null) c.add(expression);
		
		int count = (Integer) c.setProjection(Projections.rowCount()).
				uniqueResult();
		transaction.commit();
		return count;
	}
	
	//更新或添加子网
	@SuppressWarnings("static-access")
	public void save(Subnet subnet) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		try{
			session.saveOrUpdate(subnet);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}

	//删除子网
	@SuppressWarnings("static-access")
	public void delete(Subnet subnet) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();	
		try{
			subnet.setIsDelete(1);
			session.update(subnet);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			transaction.rollback();
		}
	}

	//获取当前未被删除的子网
	@SuppressWarnings({ "unchecked", "static-access" })
	public List<SubnetForGlobalView> listSubnets(String firstResult, String maxResult) throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Subnet> subnetBaseList = session.createCriteria(Subnet.class).
			//setFetchMode("switchbasicinfos", FetchMode.JOIN).
			add(Restrictions.eq("isDelete", 0)).
			setFirstResult(Integer.parseInt(firstResult)).
			setMaxResults(Integer.parseInt(maxResult)).
			addOrder(Order.asc("id")).
			list();
		
		List<SubnetForGlobalView> subnetList=new ArrayList<SubnetForGlobalView>();
		for(int i=0;i<subnetBaseList.size();i++){
			int switchNum=0;
			int userNum=0;
			SubnetForGlobalView subnetForGlobalView=new SubnetForGlobalView();
			Subnet subnet=subnetBaseList.get(i);
			subnetForGlobalView.setId(subnet.getId());
			subnetForGlobalView.setName(subnet.getName());
			List<SwitchForGlobalView> switchForGlobalViewList=new ArrayList<SwitchForGlobalView>();
			Iterator<Switchbasicinfo> switchbasicinfoIterator=subnet.getSwitchbasicinfos().iterator();
			while(switchbasicinfoIterator.hasNext()){
				Switchbasicinfo switchbasicinfo=switchbasicinfoIterator.next();
				if(switchbasicinfo.getIsDelete()!=null&&switchbasicinfo.getIsDelete().intValue()==1){
					continue;
				}
				Iterator<Switchcur> switchcurIterator=switchbasicinfo.getSwitchcurs().iterator();
				while(switchcurIterator.hasNext()){
					Switchcur switchcur=switchcurIterator.next();
					SwitchForGlobalView switchForGlobalView=new SwitchForGlobalView();
					switchForGlobalView.setSwitchBasicInfoId(switchbasicinfo.getId());
					switchForGlobalView.setIpVersion(switchcur.getIpVersion());
					switchForGlobalView.setSystemMode(switchcur.getSystemMode());
					switchForGlobalView.setUserNum(switchcur.getDhcpNum()+switchcur.getSlaacNum()+switchcur.getStaticNum());
					switchForGlobalViewList.add(switchForGlobalView);
					switchNum++;
					userNum+=switchForGlobalView.getUserNum();
				}
			}
			subnetForGlobalView.setSwitchNum(switchNum);
			subnetForGlobalView.setUserNum(userNum);
			subnetForGlobalView.setSwitchForGlobalViewList(switchForGlobalViewList);
			subnetList.add(subnetForGlobalView);
		}
		transaction.commit();
		return subnetList;
	}
	//获取当前未被删除的子网,为下拉列表框用
	@SuppressWarnings({"unchecked","static-access"})
	public List<SubnetTemp> listSubnetsForCombo(String firstResult, String maxResult)throws Exception{
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql="select New com.savi.show.dto.SubnetTemp(s.id,s.name,s.ipv4subNet,s.ipv6subNet,s.isDelete)"+
			" from Subnet s where s.isDelete=0 ";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<SubnetTemp> list = query.list();
		transaction.commit();
		return list;
	}
	
	//获取当前未被删除的子网数目
	@SuppressWarnings("static-access")
	public int getSubnetsCount(){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		int count = (Integer)session.createCriteria(Subnet.class).
			add(Restrictions.eq("isDelete", 0)).
			setProjection(Projections.rowCount()).
			uniqueResult();
		transaction.commit();
		return count;
	}
	
	//通过id获得子网
	@SuppressWarnings({ "static-access"})
	public SubnetForGlobalView getSubnetForGlobalView(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Subnet subnet=(Subnet)session.get(Subnet.class, Long.parseLong(subnetId));
		//List subnetList = session.createCriteria(Subnet.class).add(Restrictions.eq("id", Long.parseLong(subnetId))).list();
		int switchNum=0;
		int userNum=0;
		SubnetForGlobalView subnetForGlobalView=new SubnetForGlobalView();
		subnetForGlobalView.setId(subnet.getId());
		subnetForGlobalView.setName(subnet.getName());
		List<SwitchForGlobalView> switchForGlobalViewList=new ArrayList<SwitchForGlobalView>();
		Iterator<Switchbasicinfo> switchbasicinfoIterator=subnet.getSwitchbasicinfos().iterator();
		while(switchbasicinfoIterator.hasNext()){
			Switchbasicinfo switchbasicinfo=switchbasicinfoIterator.next();
			if(switchbasicinfo.getIsDelete()!=null&&switchbasicinfo.getIsDelete().intValue()==1){
				continue;
			}
			Iterator<Switchcur> switchcurIterator=switchbasicinfo.getSwitchcurs().iterator();
			while(switchcurIterator.hasNext()){
				Switchcur switchcur=switchcurIterator.next();
				SwitchForGlobalView switchForGlobalView=new SwitchForGlobalView();
				switchForGlobalView.setSwitchBasicInfoId(switchbasicinfo.getId());
				switchForGlobalView.setIpVersion(switchcur.getIpVersion());
				switchForGlobalView.setSystemMode(switchcur.getSystemMode());
				switchForGlobalView.setUserNum(switchcur.getDhcpNum()+switchcur.getSlaacNum()+switchcur.getStaticNum());
				switchForGlobalViewList.add(switchForGlobalView);
				switchNum++;
				userNum+=switchForGlobalView.getUserNum();
			}
		}
		subnetForGlobalView.setSwitchNum(switchNum);
		subnetForGlobalView.setUserNum(userNum);
		subnetForGlobalView.setSwitchForGlobalViewList(switchForGlobalViewList);
		transaction.commit();
		return subnetForGlobalView;
	}
	@SuppressWarnings({"static-access"})
	public Subnet getSubnet(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Subnet subnet = (Subnet)session.get(Subnet.class,Long.parseLong(subnetId));
		transaction.commit();
		return subnet;
	}
	@SuppressWarnings({"static-access"})
	public boolean hasActiveSwitchbasicinfos(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Subnet subnet = (Subnet)session.get(Subnet.class,Long.parseLong(subnetId));
		Iterator<Switchbasicinfo> switchbasicinfoIterator=subnet.getSwitchbasicinfos().iterator();
		while(switchbasicinfoIterator.hasNext()){
			Switchbasicinfo switchbasicinfo=switchbasicinfoIterator.next();
			if(switchbasicinfo.getIsDelete()==0){
				transaction.commit();
				return true;
			}
		}
		transaction.commit();
		return false;
	}
		
	//获取通过名字获得子网
	@SuppressWarnings({ "static-access", "unchecked" })
	public Subnet getSubnetByName(String name){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List subnetList = session.createCriteria(Subnet.class).
			add(Restrictions.eq("name", name)).list();
		transaction.commit();
		if(subnetList.size()==0) return null;
		return (Subnet) subnetList.get(0);
		
	}
	
	//获取子网的绑定表
	@SuppressWarnings({ "static-access", "unchecked"})
	public List<BindingTableInfo> getBindingTableInfo(String subnetId, String firstResult, String maxResult){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select savi from Savibindingtablecur savi " +
				"join savi.ifinterfacecur ifc " +
				"join ifc.switchcur sc " +
				"join sc.switchbasicinfo sinfo " +
				"join sinfo.subnet sn " +
				"where sinfo.isDelete =0 and  sn.id = '" + subnetId + "'";
		Query query = session.createQuery(sql);
		query.setFirstResult(Integer.parseInt(firstResult));
		query.setMaxResults(Integer.parseInt(maxResult));
		List<Savibindingtablecur> list = query.list();
		
		sql = "select max(savi.bindingLifetime) from Savibindingtablecur savi " +
			"join savi.ifinterfacecur ifc " +
			"join ifc.switchcur sc " +
			"join sc.switchbasicinfo sinfo " +
			"join sinfo.subnet sn " +
			"where sinfo.isDelete =0 and  sn.id = '" + subnetId + "'";
		query = session.createQuery(sql);
		List maxLifeTimeList=query.list();
		int maxLifeTime=0;
		if(maxLifeTimeList!=null&&maxLifeTimeList.size()!=0&&maxLifeTimeList.get(0)!=null){
			maxLifeTime=Integer.parseInt(query.list().get(0).toString());
			if(maxLifeTime < 1 ) maxLifeTime = 1;
		}
		List<BindingTableInfo> bindingTableInfoList = new LinkedList<BindingTableInfo>();
		for(int i = 0; i < list.size(); i++){
			BindingTableInfo bi = null;
			Savibindingtablecur sc = list.get(i);
			/*
			sql = "select u from User u where u.status=1 and u.ipAddress='"
				+ sc.getIpAddress() + "'";
			query = session.createQuery(sql);
			List<User> userList = query.list();
			if(userList.size() == 0) bi = new BindingTableInfo(sc, maxLifeTime,null);
			else bi = new BindingTableInfo(sc, maxLifeTime, userList.get(0).getName());
			bindingTableInfoList.add(bi);
			*/
			bi = new BindingTableInfo(sc, maxLifeTime);
			bindingTableInfoList.add(bi);
		}
		transaction.commit();
		return bindingTableInfoList;
	}
	
	//获取子网的绑定表项数目
	@SuppressWarnings({"unchecked","static-access"})
	public int getBindingTableInfoNum(String subnetId){
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		String sql = "select count(savi) as num from Savibindingtablecur savi " +
				"join savi.ifinterfacecur ifc " +
				"join ifc.switchcur sc " +
				"join sc.switchbasicinfo sinfo " +
				"join sinfo.subnet sn " +
				"where sinfo.isDelete =0 and sn.id = '" + subnetId + "'";
		Query query = session.createQuery(sql);
		List countList=query.list();
		int num=0;
		if(countList!=null&&countList.size()!=0&&countList.get(0)!=null){
			num=Integer.parseInt(query.list().get(0).toString());
		}
		transaction.commit();
		return num;
	}
	
	// 获取子网24小时内用户变化信息
	//TODO:使用User替换Savibindingtablehis
	@SuppressWarnings( { "unchecked", "static-access" })
	public List<PreUserInfo> getUserChangeInfo(String subnetId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
//		String sql = "select u from User u left join u.ifinterfacehis ifhis " +
//				"left join ifhis.switchhis shis " +
//				"left join shis.switchbasicinfo sinfo " +
//				"left join sinfo.subnet sn " +
//				"where u.startTime >= " + millis +" and " +
//				"sn.id = '" + subnetId + "'";
		/*
		String sql = "select u from Savibindingtablehis u left join u.ifinterfacehis ifhis " +
		"left join ifhis.switchhis shis " +
		"left join shis.switchbasicinfo sinfo "+
		"left join sinfo.subnet snet, "+
		"in (snet.switchbasicinfos) sbasic " + 
		"where (u.startTime >= " + millis +" or u.status=1 or u.endTime>" +millis+") and sbasic.isDelete=0 and " +
		"snet.id = '" + subnetId + "'";
		*/
		String sql ="select New com.savi.show.dto.PreUserInfo(t.id,t.ipAddressType,t.userName,t.ipAddress,t.startTime,t.endTime,t.status,i.ifIndex,b.name) from Savibindingtablehis t JOIN t.ifinterfacehis i JOIN i.switchhis h JOIN h.switchbasicinfo b JOIN b.subnet s"+
		" where (t.startTime >=" + millis +" or t.status=1 or t.endTime>"+millis+") and b.isDelete=0 and s.id="+subnetId;
		/*
		String sql = "select u from Savibindingtablehis u left join u.ifinterfacehis ifhis " +
			"left join ifhis.switchhis shis " +
			"left join shis.switchbasicinfo sinfo " +
			"left join sinfo.subnet sn " +
			"where (u.startTime >= " + millis +" or u.status=1 or u.endTime>" +millis+") and sinfo.isDelete=0 and " +
			"sn.id = '" + subnetId + "'";
		*/
		Query query = session.createQuery(sql);
		//query.setFirstResult(Integer.parseInt(firstResult));
		//query.setMaxResults(Integer.parseInt(maxResult));
		//query.setFirstResult(0);
		//query.setMaxResults(500);
		List list = query.list();
		transaction.commit();
		return list;
	}
/*
	// 获取子网24小时内用户数
	@SuppressWarnings( {"static-access" })
	public int getUserChangeInfoNum(String subnetId) {
		Session session = hibernateUtil.getSessionFactory().getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Long millis = System.currentTimeMillis() - 24 * 60 * 60 * 1000;
		String sql = "select count(u) as count from User u left join u.ifinterfacehis ifhis " +
				"left join ifhis.switchhis shis " +
				"left join shis.switchbasicinfo sinfo " +
				"left join sinfo.subnet sn " +
				"where u.startTime >= " + millis +" and " +
				"sn.id = '" + subnetId + "'";
		Query query = session.createQuery(sql);
		int count = Integer.parseInt(query.list().get(0).toString());
		transaction.commit();
		return count;
	}
*/	
	public static  void main(String[] args) throws Exception{
		//SubnetDao subnetDao = new SubnetDao();
		Date d1 = new Date();
		long longtime1 = d1.getTime();
		//List<SubnetForGlobalView> subnetList=subnetDao.listSubnets("0", "50");
		Date d2 = new Date();
		long longtime12 = d2.getTime();
		System.out.println(longtime1-longtime12);
		/*
		//Subnet subnet = subnetDao.getSubnet("1");
		//subnetDao.delete(subnet);
		String[] keywordArr = new String[]{"net3", "FF03" , ""};
		List<Subnet> list = subnetDao.searchSubnet(keywordArr, "1", "10");
		
//		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//		session.beginTransaction();
//		List<Subnet> list = session.createCriteria(Subnet.class).
//			add(Restrictions.ilike("name", "我", MatchMode.ANYWHERE)).list();
		
		for(int i = 0; i < list.size(); i++)
			System.out.println("subnet id: " + list.get(i).getId());
		*/
	}
}
