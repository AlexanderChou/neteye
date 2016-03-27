package com.topo.dao;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.base.util.Constants;

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
public class Topo_Edit {

	/**
	 * 得到子的四个空间
	 * @param zb 父区间的坐标 格式 2，3，4， 
	 * @param map 存放区间坐标 名字 和 坐标的 map
	 * @param parentName 父区间的名字 test
	 */
	public static void getFoureSubSpace(String zb, Map<String, String> map, String parentName){
		String[] zbs = zb.split(",");
		
		int x1 = Integer.parseInt(zbs[0]);
		int y1 = Integer.parseInt(zbs[1]);
		
		int x2 = Integer.parseInt(zbs[2]);
		int y2 = Integer.parseInt(zbs[3]);
		
		/* 子区间 1  */
		StringBuffer sub1 = new StringBuffer();
		sub1.append((x1 + x2)/2 + 1).append(",");
		sub1.append(y1).append(",");
		sub1.append(x2).append(",");
		sub1.append((y2-y1)/2);

		map.put(parentName.replace("]", "") + "-1" + "]", sub1.toString());
		
		/* 子区间 2 */
		StringBuffer sub2 = new StringBuffer();
		sub2.append(x1).append(",");
		sub2.append(y1).append(",");
		sub2.append((x1 + x2)/2).append(",");
		sub2.append((y1 + y2)/2);
		
		map.put(parentName .replace("]", "") + "-2" + "]", sub2.toString());
		
		/* 子区间 3  */
		StringBuffer sub3 = new StringBuffer();
		sub3.append(x1).append(",");
		sub3.append((y2 + y1)/2 + 1).append(",");
		sub3.append((x2 + x1)/2 ).append(",");
		sub3.append(y2);
		
		map.put(parentName.replace("]", "") + "-3" + "]", sub3.toString());
		
		/*  子区间 4  */
		StringBuffer sub4 = new StringBuffer();
		sub4.append((x1 + x2) / 2 + 1).append(",");
		sub4.append((y1 + y2) / 2 + 1).append(",");
		sub4.append(x2).append(",");
		sub4.append(y2);
		
		/* 把父区间去掉 */
		map.remove(parentName);
		map.put(parentName.replace("]", "") + "-4" + "]", sub4.toString());
	}
	
	/**
	 * 得到根的四个空间
	 * @param zb
	 * @param map
	 * @param parentName
	 */
	public static void getRootFourSpace(String zb, Map<String, String> map, String parentName){
		String[] zbs = zb.split(",");
		
		int x1 = Integer.parseInt(zbs[0]);
		int y1 = Integer.parseInt(zbs[1]);
		
		int x2 = Integer.parseInt(zbs[2]);
		int y2 = Integer.parseInt(zbs[3]);
		
		/* 子区间 1  */
		StringBuffer sub1 = new StringBuffer();
		sub1.append((x1 + x2)/2 + 1).append(",");
		sub1.append(y1).append(",");
		sub1.append(x2).append(",");
		sub1.append((y2-y1)/2);
		
		map.put(parentName + "[" + "1" + "]", sub1.toString());
		
		/* 子区间 2 */
		StringBuffer sub2 = new StringBuffer();
		sub2.append(x1).append(",");
		sub2.append(y1).append(",");
		sub2.append((x1 + x2)/2).append(",");
		sub2.append((y1 + y2)/2);
		
		map.put(parentName + "[" + "2" + "]", sub2.toString());
		
		/* 子区间 3  */
		StringBuffer sub3 = new StringBuffer();
		sub3.append(x1).append(",");
		sub3.append((y2 + y1)/2 + 1).append(",");
		sub3.append((x2 + x1)/2 ).append(",");
		sub3.append(y2);
		
		map.put(parentName + "[" + "3" + "]", sub3.toString());
		
		/*  子区间 4  */
		StringBuffer sub4 = new StringBuffer();
		sub4.append((x1 + x2) / 2 + 1).append(",");
		sub4.append((y1 + y2) / 2 + 1).append(",");
		sub4.append(x2).append(",");
		sub4.append(y2);
		
		map.put(parentName + "[" + "4" + "]", sub4.toString());
	}
	
	/**
	 * 得到子的两个空间
	 * @param zb
	 * @param map
	 * @param parentName
	 */
	public static void getTwoSubSpace(String zb, Map<String, String> map, String parentName) {
		String[] zbs = zb.split(",");
		
		int x1 = Integer.parseInt(zbs[0]);
		int y1 = Integer.parseInt(zbs[1]);
		
		int x2 = Integer.parseInt(zbs[2]);
		int y2 = Integer.parseInt(zbs[3]);
		
		/* 区间 1  */
		StringBuffer sub1 = new StringBuffer();
		sub1.append((x1 + x2) / 2 + 1).append(",");
		sub1.append(y1).append(",");
		sub1.append(x2).append(",");
		sub1.append(y2);
		
		map.put(parentName.replace("]", "") + "-1" + "]", sub1.toString());
		
		/*  区间 2  */
		StringBuffer sub2 = new StringBuffer();
		sub2.append(x1).append(",");
		sub2.append(y1).append(",");
		sub2.append((x1 + x2) / 2).append(",");
		sub2.append(y2);
		
		/* 把父区间去掉 */
		map.remove(parentName);
		map.put(parentName.replace("]", "") + "-2" + "]", sub2.toString());
	}
	
	/**
	 * 
	 * @return
	 */
	public static String  listHTMLAREA(String disName){
		/* 这是个原始坐标 有图片大小决定 */
		String srcZb = "0,0,190,152";
		
		Map<String, String> map = new HashMap<String, String>();
		
		getRootFourSpace(srcZb, map, disName);

		for (int i = 1; i <= 4; i++) {
			String fileName = disName + "[" + i + "-";
			int count = getFileCount(fileName);
			if (count == 4) {
				getFoureSubSpace(map.get(disName + "[" + i + "]"), map, disName + "[" + i + "]");
			} else if (count == 2) {
				getTwoSubSpace(map.get(disName + "[" + i + "]"), map, disName + "[" + i + "]");
			}
		}
		
		StringBuffer sb = new StringBuffer();
		for (String key : map.keySet()) {
			String value = "<area coords='"+ map.get(key) + "' onclick='showDiv(this)' alt='" + key + "' value='" + key + "' />";  
			sb.append(value);
		}
		
		return sb.toString();
	}
	
	/**
	 * 判断某个区间的下的子区间个数
	 * @param fn
	 * @return
	 */
	public static int getFileCount(String fn) {
		String filePath = Constants.webRealPath + "file/topo/topoHis/";
		File file = new File(filePath);  //文件的位置
		File[] files = file.listFiles();
		int count = 0;
		for (File f : files) {
			String fileName = f.getName();
			if (fileName.startsWith(fn)) {
				count++;
			} 
		}
		return count;
	}

}
