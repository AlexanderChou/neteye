package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.ReadCollegeAction;


public class CollegeIPTask extends TimerTask {
	 public CollegeIPTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new ReadCollegeAction().readIPNum("hour");
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
