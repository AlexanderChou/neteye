package com.totalIP.action;

import java.util.List;

import com.base.model.NodeDelay;
import com.totalIP.dao.DelayDao;
import com.totalIP.dto.NodeDelayshow;

public class DelayAction {
	public static void main(String [] args){
		DelayDao dd = new DelayDao();
		List<NodeDelayshow> nds = dd.getRecentEightHoursDelay();
		System.out.println(nds.size());
		for(int i = 0;i <= nds.size()-1; i++){
		System.out.println(dd.getRecentEightHoursDelay().get(i).getRegion());
		System.out.println(dd.getRecentEightHoursDelay().get(i).getTime());
		}
		System.out.println("ed");
	}
}