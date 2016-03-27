package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadTotalAction;


public class TotalIPDayTask extends TimerTask {
	 public TotalIPDayTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadTotalAction().readDayIPNum();
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
}
