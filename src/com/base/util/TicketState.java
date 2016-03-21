package com.base.util;

public class TicketState {
	/**
	 * 默认的状态未提交
	 */
	public static int UNPUBLISH=-1;
   /**
    * 事件被提交所处的状态
    */
   public static int PUBLISH=0;
   /**
    * 事件被接受所处的状态
    */
   public static int ACCEPT_PUB_AND_WAIT_DEALING=1;
   /**
    * 事件处理完毕等待审核
    */
   public static int DEAL_DONE_WAIT_AUDITING=2;
   /**
    * 事件被委托的状态
    */
   public static int BEING_DELEGATE=3;
   /**
    * 事件被委托人接受
    */
   public static int DELEGATING_ACCEPT=4;
 
   /**
    * 事件成功结束
    */
   public static int TICKET_DONE=5;
   /**
    * ticket事件不被接受
    */
   public static int PUB_DISACCEPT=6;
   /**
    * 委托不被接受
    */
   public static int DELEGATING_DISACCEPT=7;
   /**
    * 终审不通过
    */
   public static int TICKET_UNPASS=8;
}
