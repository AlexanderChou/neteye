package com.base.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.base.model.DeviceIcon;
import com.base.model.Image;
import com.base.util.HibernateUtil;
import com.view.dto.Icon;

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
public class ImageService extends BaseService{
	
	private Session session=null;
	
	public List<Icon> getImageList() throws Exception{
		List<Icon> list = new ArrayList<Icon>();
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx = session.beginTransaction();
			List<DeviceIcon> records = session.createCriteria(DeviceIcon.class).list();
			for(DeviceIcon record:records){
				Icon icon = new Icon();
				icon.setId(record.getId());
				icon.setIconFile(record.getIconFile());
				list.add(icon);
			}
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
	public List<Image> getImageList(String imageType) throws Exception{
		List<Image> list =null;
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx = session.beginTransaction();
			list = session.createCriteria(Image.class).add(Restrictions.eq("imageType",imageType)).list();
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
	
	public void addImage(Image image) throws Exception{
		session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			session.save(image);	
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			throw e;
		}
	}
	
	public static boolean isNameUnique(String imageName,String imageType) throws Exception{
		Integer num = null;
		Session session =HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx=null;
		try{
			tx=session.beginTransaction();
			num=(Integer) session.createCriteria(Image.class).add(Restrictions.eq("imageName",imageName))
			  .add(Restrictions.eq("imageType",imageType)).setProjection(Projections.rowCount()).list().get(0);
			tx.commit();
		}catch(Exception e){
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
		}
		if(num.intValue()==0){
			return true;
		}else{
		return false;
		}
	}
}
