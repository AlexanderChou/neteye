package com.base.service;

import java.io.File;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


import com.base.model.Attachment;
import com.base.model.BaseEntity;
import com.base.model.Image;
import com.base.model.Ticket;
import com.base.model.UserPopedom;
import com.base.util.HibernateUtil;

public class TicketService extends BaseService{
	public  void create(Ticket o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			session.save(o);
			tx.commit();
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	public void deleteById(long id) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		List <Attachment>result;
		try
		{    
			tx = session.beginTransaction();
            
			Ticket ticket=(Ticket) session.load(Ticket.class, id);
			session.delete(ticket);
		
			String queryString = "from Attachment where ticket="+id;
			result = session.createQuery(queryString).list();
			for(Attachment att:result){
				String path = att.getFilePath();
				File dir=new File(path);
	   		    if(dir.exists() == true) {
	   			dir.delete();
	   		    }
	   			Attachment attach = (Attachment)session.load(Attachment.class,att.getId());
	   			session.delete(attach);
			}
			
			
			tx.commit();
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}
	public  void modifyTicket(Ticket o) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{    
			tx = session.beginTransaction();

			session.update(o);
			tx.commit();
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	public Ticket getTicketByPid(long id) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		
		List result=null;
		try
		{   tx=session.beginTransaction();
			String queryString = "from Ticket where pid="+id;
			result = session.createQuery(queryString).list();
			tx.commit();
			if(result.size()==0)
				 return null;
			else
				return (Ticket)(result.get(0));
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}    
	
	public List QueryByHql(String Hql) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		
		List result=null;
		try
		{   tx=session.beginTransaction();
			result = session.createQuery(Hql).list();
			tx.commit();
			return result;
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}    
	
	public long[] getGroupIdByUserId(long userId)throws Exception{
        long []role;   		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		
		List<UserPopedom> result=null;
		try
		{   tx=session.beginTransaction();
			String queryString = "from UserPopedom user where user.userId="+userId;
			result = session.createQuery(queryString).list();
			role = new long[result.size()];
			tx.commit();
			    if(result.size()!=0){
			    	for(int i =0 ;i<result.size();i++){
			    		role[i] = ((UserPopedom)(result.get(i))).getGroupId();
			    	}
			    	return role;
			    }
			    else
			    	return null;
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	public boolean isNameUnique(String fileName){
		Integer num = 0;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			num = session.createQuery("from Attachment att where att.fileName ='"+fileName+"'").list().size();
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(num==0){
			return true;
		}else{
		return false;
		}
	}
	
	public Attachment getAttachmentById(long id) throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Attachment o=(Attachment)session.get(Attachment.class, id);
			tx.commit();
			return o;
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public Ticket getById(long id)throws Exception{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try
		{
			tx = session.beginTransaction();
			Ticket o=(Ticket)session.get(Ticket.class, id);
			tx.commit();
			return o;
		}catch (Exception e)
		{
			if (tx != null)
			{
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
		
	}
	public boolean checkAttach(long id)throws Exception{
		String query="from Attachment att where att.ticket="+id;
		List<Attachment> result=QueryByHql(query);
		if(result.size()!=0)
			return true;
		else
			return false;
	}
}
