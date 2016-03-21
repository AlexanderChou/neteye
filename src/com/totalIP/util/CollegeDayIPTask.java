package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadCollegeAction;


public class CollegeDayIPTask extends TimerTask {
	 public CollegeDayIPTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadCollegeAction().readIPNum("day");
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
