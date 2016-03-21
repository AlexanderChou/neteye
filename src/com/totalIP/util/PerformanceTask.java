package com.totalIP.util;

import java.util.TimerTask;

import com.totalIP.action.DelayXmlAction;
import com.totalIP.action.LossXmlAction;
import com.totalIP.action.ReorderingXmlAction;


public class PerformanceTask extends TimerTask {
	 public PerformanceTask() {
	    }
	    /* (non-Javadoc)
	     * @see java.util.TimerTask#run()
	     */
	    @Override
	    public void run() {
	    	try{
	    		new DelayXmlAction().eighthoursdelayxml();
	    		new DelayXmlAction().onedaydelayxml();
	    		new DelayXmlAction().oneweekdelayxml();
	    		new LossXmlAction().eighthourslossxml();
	    		new LossXmlAction().onedaylossxml();
	    		new LossXmlAction().oneweeklossxml();
	    		new ReorderingXmlAction().eighthoursreorderingxml();
	    		new ReorderingXmlAction().onedayreorderingxml();
	    		new ReorderingXmlAction().oneweekreorderingxml();
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}