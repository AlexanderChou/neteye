package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadCollegeAction;


public class WeekIPTask extends TimerTask {
	 public WeekIPTask() {
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
