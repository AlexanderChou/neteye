package com.base.util;

import java.text.NumberFormat;
import java.util.StringTokenizer;

/*
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
*/
/**
 * <p>Title: 工具类</p>
 * <p>Description: 对字符串的各种操作进行封装</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: network </p>
 * @author guoxi
 * @version 0.1
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StringUtil
{
	/**
	 * 私有构造方法，防止被实例化
	 */
	private StringUtil()
	{
	};
	private static String StringReplace(String from, String to, String source)
	{
		String temp1, temp2;
		int i = source.indexOf(from);
		while (i > 0)
		{
			source =
				source.substring(0, i).concat(to).concat(
					source.substring(i + from.length()));
			i = source.indexOf(from);
		}
		return (source);
	}
	public static String deal(String val)
	{
		val = StringReplace("\"", "’", val);
		val = StringReplace(">", "’", val);
		val = StringReplace("<", "’", val);
		return val;
	}
	/**
	 * 过滤空值，转化为空字符串
	 * 使用方法：String result = StringUtil.filterNull(obj);
	 * @param obj 任何对象
	 * @return String
	 */
	public static String filterNull(Object obj)
	{
		return obj == null ? "" : obj.toString();
	}
	/**
	 * 页面虑空处理
	 * @param obj
	 * @return
	 */
	public static String escapeNull(Object obj)
	{
		return obj == null ? "&nbsp;" : obj.toString();
	}	
	/**
	 * 该方法用于处理最终显示的数字小数位数问题
	 * 如果输入的参数是null,将返回字符串0.00
	 * 如果输入的参数的小数位数没有到达参数digit指定的位数，则在后面补零
	 * 否则截取小数位数为digit
	 * 注：这里不采取四舍五入方法
	 * @param obj 要处理的对象
	 * @param digit 想要保留的小数位数
	 * @return 经过处理的小数
	 */
	public static String filterNumberFormat(Object obj, int digit)
	{
		String objStr = "";
		if (obj == null)
		{
			return "0.00";
		}
		else
		{
			objStr = obj.toString();
			int dotLocation = objStr.indexOf(".");
			int length = objStr.length();
			if (dotLocation != -1)
			{
				//小数点以后有3位以上数字
				if (length - dotLocation - 1 > digit)
				{
					objStr = objStr.substring(0, dotLocation + 3);
				}
				//小数点之后只有一位数字
				else if (length - dotLocation == digit)
				{
					objStr += "0";
				}
				//小数点之后没有数字
				else if (length - dotLocation == digit - 1)
				{
					objStr += "00";
				}
			}
			//没有小数点
			else
			{
				objStr = objStr + ".00";
			}
		}
		return objStr;
	}
	
	public static String convertSpeed(String speed)
	{
		String newSpeed=new String();
		float temp = Float.parseFloat(speed);
		if(temp >=1000000000) 
		{
			float fspeed=(float)temp/1000000000;
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(1);
			newSpeed=""+format.format(fspeed)+"G";
		}
		if(temp <1000000000)
		{
			float fspeed=(float)temp/1000000;
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(4);
			newSpeed=""+format.format(fspeed)+"M";
		}
		if(newSpeed.equals("0M")) newSpeed="0";
		return newSpeed;
	}
	
	public static int getNetMask(String netMask)
	{		
		    StringTokenizer st = new StringTokenizer( netMask, "." );
		    int  maskLength = 0;
		    try {
		      int token = Integer.parseInt( st.nextToken() );
		      if ( token < 0 || token > 255 ){
		        throw new Exception( "Invalid netmask" );
		      }
		      maskLength = Integer.bitCount(token);

		      token = Integer.parseInt( st.nextToken() );
		      if ( token < 0 || token > 255 ){
		        throw new Exception( "Invalid  netmask" );
		      }
		      maskLength += Integer.bitCount(token);

		      token = Integer.parseInt( st.nextToken() );
		      if ( token < 0 || token > 255 ){
		        throw new Exception( "Invalid  netmask" );
		      }
		      maskLength += Integer.bitCount(token);

		      token = Integer.parseInt( st.nextToken() );
		      if ( token < 0 || token > 255 ){
		        throw new Exception( "Invalid  netmask" );
		      }
		      maskLength += Integer.bitCount(token);
		    }
		    catch ( Exception e ){		      
		        System.err.println( "Failed to parse netmask correctly : " + netMask );
		        maskLength = -1;
		    }
		    return maskLength;
	}
	/**
	 * 将xml文件中出现的字符规范化
	 * @param oldStr
	 * @return
	 */
	public static String getCriterionStr(String oldStr)
	{
		String newStr = "";
		if(oldStr!=null){
			if(oldStr.indexOf("&")==-1 && oldStr.indexOf(">")==-1 && oldStr.indexOf("<")==-1 && oldStr.indexOf("\"")==-1 && oldStr.indexOf("'")==-1){
				newStr = oldStr;
			}else{
				boolean flag = false;
				if(oldStr.indexOf("&")!=-1){
					newStr = oldStr.replace("&","&amp;");
					flag = true;
				}
				if(oldStr.indexOf(">")!=-1){
					if(flag){
						newStr = newStr.replace(">","&gt;");
					}else
						newStr = oldStr.replace(">","&gt;");
				}
				
				if(oldStr.indexOf("<")!=-1){
					if(flag){
						newStr = newStr.replace("<","&lt;");
					}else{				
						newStr = oldStr.replace("<","&lt;");
					}
				}
				if(oldStr.indexOf("\"")!=-1){
					if(flag){
						newStr = newStr.replace("\"","&quot;");
					}else
						newStr = oldStr.replace("\"","&quot;");
				}
				if(oldStr.indexOf("'")!=-1){
					if(flag){
						newStr = newStr.replace("'","&apos;");
					}else
						newStr = oldStr.replace("'","&apos;");
				}
			}
		}
		return newStr;
	}
}

