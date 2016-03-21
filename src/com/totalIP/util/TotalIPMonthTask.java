package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadTotalAction;


public class TotalIPMonthTask extends TimerTask {
	 public TotalIPMonthTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadTotalAction().readMonthIPNum();
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
