package com.tdrouting.util;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.DateTime;


public class MyServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;
    private MyThread myThread;
    
    public MyServlet(){
    }

    public void init(){
    	String str = null;
    	/*
    	try {
			AddTwodRoutingCost.addTwodRoutingCost("twodr2", "3000::/64", "3002::/64", 2);
			AddTwodRoutingCost.addTwodRoutingCost("twodr2", "2000::/64", "2001::/64", 5);
    		//DeleteTwodRoutingCost.deleteTwodRoutingCost("twodr2", "3000::/64", "3002::/64");
    		UpdateTwodRoutingCost.updateTwodRoutingCost("twodr2", "2000::/64", "2001::/64", 8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
    	CollectionTask.beginTask();
		if (str == null && myThread == null) {
			//CollectionTask.beginTask();
			//myThread = new MyThread();
			//myThread.start(); // servlet 上下文初始化时启动 socket
		}
    }

    public void doGet(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws ServletException, IOException{
    }

    public void destory(){
    	if (myThread != null && myThread.isInterrupted()) {
    		myThread.interrupt();
		}
    }
}

/**
 * 自定义一个 Class 线程类继承自线程类，重写 run() 方法，用于从后台获取并处理数据
 * 
 * @author Champion.Wong
 * 
 */
class MyThread extends Thread {
	public void run() {
		while (!this.isInterrupted()) {// 线程未中断执行循环
			try {
				Thread.sleep(20000);
				this.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// ------------------ 开始执行 ---------------------------
			System.out.println("____FUCK TIME:" +  " ");
		}
	}
}
