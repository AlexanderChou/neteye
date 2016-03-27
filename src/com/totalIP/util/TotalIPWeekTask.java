package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadTotalAction;


public class TotalIPWeekTask extends TimerTask {
	 public TotalIPWeekTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadTotalAction().readWeekIPNum();
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
