package com.ticket.junitTest;

import com.ticket.action.AcceptDelegationAction;
import com.ticket.action.AuditionPassAction;
import com.ticket.action.AuditionUnpassAction;
import com.ticket.action.DelegatorDealEventAction;
import com.ticket.action.DisAcceptDelegationAction;
import com.ticket.action.EventReSubAction;
import com.ticket.action.EventSubAction;
import com.ticket.action.UnderTakerAcceptEventAction;
import com.ticket.action.UnderTakerDealEventAction;
import com.ticket.action.UnderTakerDisAcceptEventAction;
import com.ticket.action.UsingDelegatinAction;

import junit.framework.TestCase;

public class TestWorkflow extends TestCase{
      public void testWf() throws Exception{
    	//new EventSubAction().execute(); //创建ticket
    	//new UnderTakerAcceptEventAction().execute();//接受 
    	//new UnderTakerDealEventAction().execute();//处理
    	//new AuditionPassAction().execute();//pass
    	    //上述流程全面通过
    	  
    	// new EventSubAction().execute(); //创建ticket
    	 //new UnderTakerDisAcceptEventAction().execute();//不接受 
    	// new EventReSubAction().execute();
    	// new UnderTakerAcceptEventAction().execute();//接受 
     	// new UnderTakerDealEventAction().execute();//处理
     	// new AuditionPassAction().execute();//pass
    	 //上述流程全面通过
    	  
       // new EventSubAction().execute(); //创建ticket
    //   new UnderTakerAcceptEventAction().execute();//接受 
        //new UnderTakerDealEventAction().execute();//处理
       // new AuditionUnpassAction().execute();//pass
    	//new UnderTakerDealEventAction().execute();
    	 // new AuditionUnpassAction().execute();
    	// new UnderTakerDealEventAction().execute();
    	// new AuditionPassAction().execute();
    	  //上述流程测试了在多次不接受的情况下工作流循环工作的case 已通过
    /*	  
       new EventSubAction().execute(); //创建ticket
        new UnderTakerAcceptEventAction().execute();//接受   
    	new UsingDelegatinAction().execute();
        new AcceptDelegationAction().execute();
    	  new DelegatorDealEventAction().execute();
    	  new AuditionPassAction().execute();
    	  
    	  上述流程测试了自使用代理的情况下工作流的情况  ：通过
    	  */
    	  
    	  
    	 /*测试委托不被接受而重新委托的情况*/
    	  
    	  new EventSubAction().execute(); //创建ticket
         // new UnderTakerAcceptEventAction().execute();//接受   
      	 // new UsingDelegatinAction().execute();
        //  new DisAcceptDelegationAction().execute();
        // new UsingDelegatinAction().execute();
       // new AcceptDelegationAction().execute();
      //new DelegatorDealEventAction().execute();
      	 // new AuditionPassAction().execute();
      	  
      	 //已通过
    	  
      }
}
