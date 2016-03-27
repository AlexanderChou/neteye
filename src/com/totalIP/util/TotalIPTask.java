package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadTotalAction;


public class TotalIPTask extends TimerTask {
	 public TotalIPTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadTotalAction().readHourIPNum();
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
