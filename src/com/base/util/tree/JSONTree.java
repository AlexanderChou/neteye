package com.base.util.tree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.model.IfInterface;
import com.base.model.Resource;
import com.base.model.ResourceGroupPopedom;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.util.Constants;
import com.base.util.FileUtil;
import com.base.util.HibernateUtil;
import com.base.util.W3CXML;
import com.flow.dao.FlowListViewDao;
import com.topo.dao.FileDAO;
import com.user.dao.ResourceDAO;
import com.user.dao.ResourceGroupPopedomDAO;
import com.user.dao.UserGroupDAO;
import com.user.dao.UserGroupPopedomDAO;
import com.view.dao.ViewDAO;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;

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
public class JSONTree {
	private String viewName;
	private String deviceId;
	private String chosedId;
	private String interfaceId;
	private String chosedFile;
	private String groupId;
	private static Map<String, JSONTreeCheck> choseTypeMap;
	private static Map<String,Long> chosedNode;
	private ResourceDAO dao = new ResourceDAO();

	public String getJSONString() throws Exception{ 
        List<JSONTreeNode> TreeNodeArray = new ArrayList<JSONTreeNode>();
        Map<String,JSONTreeNode> typeMap = new HashMap<String,JSONTreeNode>();
        //求出所有分类以及其包含的节点
        List<Device> devices = new DeviceService().getDeviceList(0); 
        Iterator<Device> it = devices.iterator();
        while(it.hasNext()){
        	Device device = it.next();
        	if(!typeMap.containsKey(device.getDeviceType().getId().toString())){
        		JSONTreeNode typeNode = new JSONTreeNode();
        		typeNode.setId("d"+device.getDeviceType().getId().toString());
        		typeNode.setText(device.getDeviceType().getName());
        		typeNode.setDescription(device.getDeviceType().getDescription());
        		typeNode.setLeaf(false);
        		typeNode.setExpandable(true);
                typeMap.put(device.getDeviceType().getId().toString(), typeNode);
        	}
    		JSONTreeNode treeNode = new JSONTreeNode();
    		treeNode.setId(device.getId().toString());
    		if(device.getChineseName()!=null && !device.getChineseName().equals("")){
    			treeNode.setText(device.getChineseName());
    		}else{
    			treeNode.setText(device.getName());
    		}
            treeNode.setDescription(device.getDescription());
            treeNode.setLeaf(true);
            treeNode.setExpandable(false);
            JSONTreeNode rootNode = (JSONTreeNode)typeMap.get(device.getDeviceType().getId().toString());
            rootNode.getChildren().add(treeNode);
        	
        }
        if(!typeMap.isEmpty()){
        	Iterator<String> its = typeMap.keySet().iterator();
        	while(its.hasNext()){
        		String key = its.next();
        		TreeNodeArray.add(typeMap.get(key));
        	}
        }
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
        return jsonarr.toString();//返回JSON数据 
    } 	
	
	/**
	 * 用这个方法得到用户组的信息
	 * @return
	 */
	public String getJSONSelect(String userId){
		List<JSONTreeNode> TreeNodeArray = new ArrayList<JSONTreeNode>();
        //求出所有分类以及其包含的节点
        List<Object[]> userGroups = new UserGroupPopedomDAO().getUserGroupsByUserId(userId); 
        for(Object[] o:userGroups){
        	JSONTreeNode treeNode = new JSONTreeNode();
    		treeNode.setId(o[0].toString());
    		treeNode.setText(o[1].toString());
            treeNode.setLeaf(true);
            treeNode.setExpandable(false);
            TreeNodeArray.add(treeNode);
        }        
       
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组
        return jsonarr.toString();//返回JSON数据
	}
	
	/**
	 * 用这个方法得到用户没有选择的用户组
	 * @return
	 */
	public String getJSONNOSelect(String userId){
		List<JSONTreeNode> TreeNodeArray = new ArrayList<JSONTreeNode>();
		//求出所有分类以及其包含的节点
		List<Object[]> userGroups = new UserGroupDAO().getAllUserGroups(userId) ; 
		for(Object[] o:userGroups){
				JSONTreeNode treeNode = new JSONTreeNode();
				treeNode.setId(o[0].toString());
				treeNode.setText(o[1].toString());
				treeNode.setLeaf(true);
				treeNode.setExpandable(false);
				TreeNodeArray.add(treeNode);
		 }
		JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
		return jsonarr.toString();//返回JSON数据 
	}
	
	
    /**
     * 获得视图选择或未选择的节点树字符串
     * @param isChosed true:已选择的节点树  false:未选择的节点树
     * @return
     * @throws Exception
     */
    public String getChoseJSONString(boolean isChosed) throws Exception{ 
    	List<JSONTreeNode> TreeNodeArray = new ArrayList<JSONTreeNode>();
    	Map<String,JSONTreeNode> choseTypeMap = new HashMap<String,JSONTreeNode>();
    	Map<String,JSONTreeNode> chosedMap = chosedDevice();
    	//求出所有分类以及其包含的节点
    	List<Device> devices = new DeviceService().getDeviceList(0);     	
    	Iterator<Device> it = devices.iterator();
    	while(it.hasNext()){
    		Device device = it.next();
    		String deviceId = device.getId().toString();
    		boolean flag = false;
    		if(isChosed){
    			flag = chosedMap.containsKey(deviceId);	//包含deviceId则flag为true
    		}else{
    			flag = !chosedMap.containsKey(deviceId);
    		}
    		if(flag){
    			if(!choseTypeMap.containsKey(device.getDeviceType().getId().toString())){
	    			JSONTreeNode typeNode = new JSONTreeNode();
	    			typeNode.setId("d"+device.getDeviceType().getId().toString());
	    			typeNode.setText(device.getDeviceType().getName());
	    			typeNode.setDescription(device.getDeviceType().getDescription());
	    			typeNode.setLeaf(false);
	    			typeNode.setExpandable(true);
	    			choseTypeMap.put(device.getDeviceType().getId().toString(), typeNode);
	    		}
	    		JSONTreeNode treeNode = new JSONTreeNode();
	    		treeNode.setId(deviceId);
	    		if(device.getChineseName()!=null && !device.getChineseName().equals("")){
	    			treeNode.setText(device.getChineseName());
	    		}else{
	    			treeNode.setText(device.getName());
	    		}
	    		treeNode.setDescription(device.getDescription());
	    		treeNode.setLeaf(true);
	    		treeNode.setExpandable(false);
	    		JSONTreeNode rootNode = (JSONTreeNode)choseTypeMap.get(device.getDeviceType().getId().toString());
	    		rootNode.getChildren().add(treeNode);
    		}
    	}
    	if(!choseTypeMap.isEmpty()){
    		Iterator<String> its = choseTypeMap.keySet().iterator();
    		while(its.hasNext()){
    			String key = its.next();
    			TreeNodeArray.add(choseTypeMap.get(key));
    		}
    	}
    	JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组  
    	return jsonarr.toString();//返回JSON数据 
    } 
    /**
     * 从视图配置文件中获得该视图包含的节点
     * @return 
     */
    public Map<String,JSONTreeNode> chosedDevice(){
    	Map<String,JSONTreeNode> chosedMap = new HashMap<String,JSONTreeNode>();
    	//读取配置文件viewName.xml（从页面得到该视图的名称）,得到已选节点的id
    	ViewDAO viewDAO = new ViewDAO();
		View view = viewDAO.getViewByViewName(viewName);
		String path = Constants.webRealPath + "file/user/" + view.getUserName() + "_" + view.getUserId() + "/";
		String fileStr = path + viewName + ".xml";
		org.w3c.dom.Document xmldoc = W3CXML.loadXMLDocumentFromFile(fileStr);
		NodeList routerLists = xmldoc.getElementsByTagName("to:router"); // NodeList包括所有名字为router的节点
    	if(routerLists!=null){
			for (int i = 0; i < routerLists.getLength(); i++) {
				Node routerNode = routerLists.item(i);// routerNode是每一个router节点
				NodeList childLists = routerNode.getChildNodes();// childLists存放的是router的子节点
				String id = "";
				String name = "";
				for (int j = 0; j < childLists.getLength(); j++) {
					Node childNode = childLists.item(j);// childNode是router的每一个子节点
					String nodeName = childNode.getNodeName();// nodeName是每一个子节点的名字
					if (nodeName.equals("to:id")) {
						id = childNode.getTextContent(); 
					}else if (nodeName.equals("to:name")) {
						name = childNode.getTextContent();
					} 
				}
				JSONTreeNode treeNode = new JSONTreeNode();
				treeNode.setId(id);
				treeNode.setText(name);
	            treeNode.setLeaf(true);
	            treeNode.setExpandable(false);
				chosedMap.put(id, treeNode);
			}
    	}
    	return chosedMap;
    }
    
    public String getLink() throws Exception{ 
        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>(); 
        //根据某一设备id获得该设备的所有端口
        //对于服务器来说，它的各个服务的id会是XX_X的格式,此时就不进行getLink()函数
        if(!deviceId.contains("_")){
	        Device device = new DeviceService().findById(Long.parseLong(deviceId));
	        JSONTreeCheck rootNode = new JSONTreeCheck();
	        rootNode.setId("d"+device.getId().toString());
	        if(device.getChineseName()!=null && !device.getChineseName().equals("")){
	        	rootNode.setText(device.getChineseName());
	        }else{
	        	rootNode.setText(device.getName());
	        }
	        rootNode.setDescription(device.getDescription());
	        rootNode.setLeaf(false);
	        rootNode.setIconCls("test");
	        rootNode.setExpandable(true);
	        Set<IfInterface> ifs = device.getIfinterfaces();
	        Iterator<IfInterface> it = ifs.iterator();
	        String IP = null;
	        while(it.hasNext()){
	        	IfInterface inf = it.next();
	        	JSONTreeCheck treeNode = new JSONTreeCheck();
	        	treeNode.setId(inf.getId().toString());
	        	IP = inf.getIpv4();
	        	if((IP==null || "".equals(IP)) && (inf.getIpv6()!=null && !"".equals(inf.getIpv6()))){
	        		IP = inf.getIpv6();
	        	}
	        	if(inf.getChineseName()!=null && !"".equals(inf.getChineseName())){
	        		treeNode.setText(inf.getChineseName()+"  || IP:"+ IP +"  || Index:"+inf.getIfindex());
	        	}else{
	        		treeNode.setText(inf.getDescription()+"  || IP:"+ IP +"  || Index:"+inf.getIfindex());
	        	}
				treeNode.setCls("file");
				treeNode.setIconCls("test");
	            treeNode.setLeaf(true);
	            treeNode.setExpandable(false);
	            treeNode.setChecked(false);
	            rootNode.getChildren().add(treeNode);
	        }        
	        TreeNodeArray.add(rootNode);
	        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray);
	        return jsonarr.toString();
        }else{
        	return "";
        }
    }
    public String getLinked() throws Exception{ 
    	List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>(); 
    	//根据某一设备id获得该设备的所有端口
    	//对于服务器来说，它的各个服务的id会是XX_X的格式,此时就不进行getLink()函数
    	if(!deviceId.contains("_")){
    		Device device = new DeviceService().findById(Long.parseLong(deviceId));
    		JSONTreeCheck rootNode = new JSONTreeCheck();
    		rootNode.setId("d"+device.getId().toString());
    		if(device.getChineseName()!=null && !device.getChineseName().equals("")){
    			rootNode.setText(device.getChineseName());
    		}else{
    			rootNode.setText(device.getName());
    		}
    		rootNode.setDescription(device.getDescription());
    		rootNode.setLeaf(false);
    		rootNode.setIconCls("test");
    		rootNode.setExpandable(true);
    		Set<IfInterface> ifs = device.getIfinterfaces();
    		Iterator<IfInterface> it = ifs.iterator();
    		while(it.hasNext()){
    			IfInterface inf = it.next();
    			JSONTreeCheck treeNode = new JSONTreeCheck();
    			treeNode.setId(inf.getId().toString());
    			if(inf.getChineseName()!=null && !"".equals(inf.getChineseName())){
    				treeNode.setText(inf.getChineseName());
    			}else{
    				treeNode.setText(inf.getDescription());
    			}
    			treeNode.setCls("file");
    			treeNode.setIconCls("test");
    			treeNode.setLeaf(true);
    			treeNode.setExpandable(false);
    			int temp1 = inf.getId().intValue();
    			int temp2 = Integer.parseInt(interfaceId.trim());
    			if(temp1==temp2){
    				treeNode.setChecked(true);
    			}else{
    				treeNode.setChecked(false);
    			}
    			rootNode.getChildren().add(treeNode);
    		}        
    		TreeNodeArray.add(rootNode);
    		JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray);
    		return jsonarr.toString();
    	}else{
    		return "";
    	}
    }
    public String choseMonitorInf() throws Exception{ 
        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>();
        //先要求出当前用户的名称和id，然后在相应目录下，根据视图id找到该视图中包含的所有设备及其相应端口（系统目前将权限也放在了这个文件夹下，将存在一些隐患，应该把权限文件移至到另外一个目录）
        //为了减少对数据库的操作，可将所有id拼成一个字符串，在一个语句中查出所有这些设备及其相应的端口
        //从页面传过来的viewName格式：视图名称_视图Id，需要进行分割
        String viewId = viewName.substring(viewName.lastIndexOf("_")+1,viewName.length());
        //更改获取用户信息的方式，以免不同用户身份登录时，找不到相应的视图文件
        View view = new ViewDAO().getViewByViewId(Long.valueOf(viewId));
        Long userId = view.getUserId();
        String userName = view.getUserName();
    	//String userId = ServletActionContext.getRequest().getSession().getAttribute("userId").toString();
    	//String userName = ServletActionContext.getRequest().getSession().getAttribute("userName").toString();    	
    	//String reasViewName = viewName.substring(0,viewName.lastIndexOf("_"));
    	String filePath = Constants.webRealPath + "/file/user/" + userName + "_" + userId  + "/"+view.getName()+".xml";
		File file = new File(filePath);
		if (file.exists()) {// 如果文件存在，获得该文件中包含的所有设备
			List<Router> nodes = MyXmlUtil.getNodeIdList(filePath);
			for(Router node:nodes){
				//由node的id，查询数据库获得Device对象
				Device device = new DeviceService().findById(node.getId());
				JSONTreeCheck deviceNode = new JSONTreeCheck();
				deviceNode.setId(device.getId().toString());
				if(device.getChineseName()!=null && !device.getChineseName().equals("")){
					deviceNode.setText(device.getChineseName());
	    		}else{
	    			deviceNode.setText(device.getName());
	    		}
				deviceNode.setDescription("");
				deviceNode.setLeaf(false);
				deviceNode.setExpandable(true);
				deviceNode.setIconCls("test");
				//根据设备id求出其所有已进行流量采集的端口
				Set<IfInterface> ifs = device.getIfinterfaces();
	    		Iterator<IfInterface> it = ifs.iterator();
	    		while(it.hasNext()){
	    			IfInterface inf = it.next();
	    			JSONTreeCheck treeNode = new JSONTreeCheck();
	    			treeNode.setId(device.getId().toString()+"_"+inf.getIfindex()+"_"+inf.getId());
	    			String ip = "";
	    			if(inf.getIpv4()!=null && !inf.getIpv4().equals("")){
	    				ip = inf.getIpv4();
	    			}
	    			if(inf.getIpv6()!=null && !inf.getIpv6().equals("") && !ip.equals("")){
	    				ip = ip + " || IP:" +inf.getIpv6();
	    			}
	    			String text = "";
	    			if(inf.getChineseName()!=null && !"".equals(inf.getChineseName())){
	    				text = inf.getChineseName();
	    			}else{
	    				text = inf.getDescription();
	    			}
	    			if(!ip.equals("")){
	    				text = text + " || IP:" + ip;
	    			}
	    			if(!text.equals("")){
	    				treeNode.setText(text+" ||  Index:"+inf.getIfindex());
	    			}else{
	    				treeNode.setText(inf.getIfindex());
	    			}
	    			treeNode.setCls("file");
	    			treeNode.setIconCls("test");
	    			treeNode.setLeaf(true);
	    			treeNode.setExpandable(false);
	    			//如果该端口已配置过，则页面显示为选中状态
	    			//首先判断file/flow/config/viewId.txt是否存在，若存在，读该文件的端口信息，存入Map；若不存在，读/file/user/userName_userId/view.xml文件，获得其链路的相应信息
	    			String configFilePath = Constants.webRealPath + "file/flow/config/" + viewId +".txt";
	    			Set<Long> infHash = new HashSet<Long>();
	    			File configFile = new File(configFilePath);
	    			if(configFile.exists()){
	    				BufferedReader input = new BufferedReader( new FileReader( configFilePath ) );
	    				String configStr;					
	    				while ( ( configStr = input.readLine() ) != null ){
	    					if(!configStr.equals("")){
	    						String arr[]=configStr.split("_");//text的格式为:设备id_ifindex_infId
	    						if(!infHash.contains(Long.valueOf(arr[2]))){
	    							infHash.add(Long.valueOf(arr[2]));
	    						}
	    					}
	    				}
	    			}else{
	    				//读视图文件，获取其应的链路信息
	    				infHash = new FlowListViewDao().getInformview(filePath);
	    			}
    				if(infHash.size()>0 && infHash.contains(inf.getId())){
    					treeNode.setChecked(true);
    				}else{
    					treeNode.setChecked(false);
    				}
	    			deviceNode.getChildren().add(treeNode);
	    		}//Endof while  
	    		TreeNodeArray.add(deviceNode);
			}//Endof For
		}
		
		JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray);
        return jsonarr.toString();//返回JSON数据 
    }
    
    public String getChoseDevice() throws Exception{ 
        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>();
        Map<String,JSONTreeCheck> typeMap = new HashMap<String,JSONTreeCheck>();
        //求出所有分类以及其包含的节点
        List<Device> devices = new DeviceService().getDeviceList(0); 
        Iterator<Device> it = devices.iterator();
        while(it.hasNext()){
        	Device device = it.next();
        	if(!typeMap.containsKey(device.getDeviceType().getId().toString())){
        		JSONTreeCheck typeNode = new JSONTreeCheck();
        		typeNode.setId("d"+device.getDeviceType().getId().toString());
        		typeNode.setText(device.getDeviceType().getName());
        		typeNode.setDescription(device.getDeviceType().getDescription());
        		typeNode.setLeaf(false);
        		typeNode.setExpandable(true);
        		typeNode.setIconCls("test");
                typeMap.put(device.getDeviceType().getId().toString(), typeNode);
        	}
        	JSONTreeCheck treeNode = new JSONTreeCheck();
    		treeNode.setId(device.getId().toString()+"_"+device.getDeviceType().getId().toString());
    		if(device.getChineseName()!=null && !device.getChineseName().equals("")){
    			treeNode.setText(device.getChineseName());
    		}else{
    			treeNode.setText(device.getName());
    		}
            treeNode.setDescription(device.getDescription());
            treeNode.setLeaf(true);
            treeNode.setExpandable(false);
            treeNode.setIconCls("test");
            String tempId = ","+this.getChosedId()+",";
            if(tempId.indexOf(","+device.getId()+",")==-1){
            	treeNode.setChecked(false);
            }else{
            	treeNode.setChecked(true);
            	continue;
            }
            JSONTreeCheck rootNode = (JSONTreeCheck)typeMap.get(device.getDeviceType().getId().toString());
            rootNode.getChildren().add(treeNode);
        }
        if(!typeMap.isEmpty()){
        	Iterator<String> its = typeMap.keySet().iterator();
        	while(its.hasNext()){
        		String key = its.next();
        		TreeNodeArray.add(typeMap.get(key));
        	}
        }
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
        
        return jsonarr.toString();//返回JSON数据 
    }
    
    public String getChoseRouter() throws Exception{ 
        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>();
        Map<String,JSONTreeCheck> typeMap = new HashMap<String,JSONTreeCheck>();
        //求出所有分类以及其包含的节点
        List<Device> devices = new DeviceService().getRouterList(2); 
        Iterator<Device> it = devices.iterator();
        while(it.hasNext()){
        	Device device = it.next();
        	if(!typeMap.containsKey(device.getDeviceType().getId().toString())){
        		JSONTreeCheck typeNode = new JSONTreeCheck();
        		typeNode.setId("d"+device.getDeviceType().getId().toString());
        		typeNode.setText(device.getDeviceType().getName());
        		typeNode.setDescription(device.getDeviceType().getDescription());
        		typeNode.setLeaf(false);
        		typeNode.setExpandable(true);
        		typeNode.setIconCls("test");
                typeMap.put(device.getDeviceType().getId().toString(), typeNode);
        	}
        	
        	String tempId = ","+this.getChosedId()+",";
            if(tempId.indexOf(","+device.getId()+",")==-1){
            	JSONTreeCheck treeNode = new JSONTreeCheck();
        		treeNode.setId(device.getId().toString());
        		if(device.getChineseName()!=null && !device.getChineseName().equals("")){
        			treeNode.setText(device.getChineseName());
        		}else{
        			treeNode.setText(device.getName());
        		}
                treeNode.setDescription(device.getDescription());
                treeNode.setLeaf(true);
                treeNode.setExpandable(false);
                treeNode.setIconCls("test");
                JSONTreeCheck rootNode = (JSONTreeCheck)typeMap.get(device.getDeviceType().getId().toString());
                rootNode.getChildren().add(treeNode);
            }
        }
        if(!typeMap.isEmpty()){
        	Iterator<String> its = typeMap.keySet().iterator();
        	while(its.hasNext()){
        		String key = its.next();
        		TreeNodeArray.add(typeMap.get(key));
        	}
        }
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
        return jsonarr.toString();//返回JSON数据 
    }
    
    public String getOtherTopoDevice() throws Exception{ 
        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>();
        //求出所有分片以及其包含的节点
        String original = "";
        if(viewName.indexOf("[")==-1){
        	original = viewName;
        }else{
        	original = viewName.substring(0,viewName.indexOf("["));
        }
        if(!chosedFile.equals(original+".txt")){
        	String dirPath = Constants.webRealPath + "file/topo/topoHis/";
        	File[] contents = new FileUtil().getFiles(dirPath,original,"[","txt");
        	if (contents != null) {
   		      for (File file : contents) {
		    	  if(file.getName().equals(chosedFile)){
		    		  continue;
	   		      }else{
		   		    	//读取文件内容（目录可以显示为分片的名称，同时也可以作为id值）
	   					BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dirPath+file.getName())));
	   					String line = null;
   						//创建目录节点
   						JSONTreeCheck typeNode = new JSONTreeCheck();
   						String tmp = file.getName().replace(".txt", "");
   						typeNode.setId(tmp);
   						typeNode.setText(tmp);
   						typeNode.setLeaf(false);
   						typeNode.setExpandable(true);
   						typeNode.setIconCls("test");
   						
   						line = reader.readLine();
   						while (StringUtils.isNotEmpty(line)) {
   							if(!line.startsWith("registerLine3")){//对该行内容进行处理
   								int prefixBracket = line.indexOf("(");
   								int postfixBracket = line.indexOf(")");
   								String middleStr = line.substring(prefixBracket+1,postfixBracket);
   								String[] middleArray = middleStr.split(",");
   								JSONTreeCheck treeNode = new JSONTreeCheck();
   								//id--IP--community--version--type--x--y
   								treeNode.setId(middleArray[0].replace("'", "")+"--"+middleArray[2].replace("'", "")+"--"+middleArray[3].replace("'", "")+"--"+middleArray[4].replace("'", "")+"--"+middleArray[5].replace("'", "")+"--"+middleArray[6].replace("'", "")+"--"+middleArray[7].replace("'", ""));
   								treeNode.setText(middleArray[1].replace("'", ""));
   							    treeNode.setLeaf(true);
   			   		            treeNode.setExpandable(false);
   			   		            treeNode.setIconCls("test");
   			   		            treeNode.setChecked(false);
   			   		            typeNode.getChildren().add(treeNode);
   							}
   							line = reader.readLine();
   						}//Endof while
   						TreeNodeArray.add(typeNode);
	   		      }//Endof else
   		      }//Endof for
   		    }
        }
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
        return jsonarr.toString();//返回JSON数据 
    }
    
	public String getNullJsonString() throws Exception{ 
        List<JSONTreeNode> TreeNodeArray = new ArrayList<JSONTreeNode>();
        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); //得到JSON数组     
        return jsonarr.toString();//返回JSON数据 
	}
	
	 public String getTopoLink() throws Exception{ 
	        List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>(); 
	        JSONTreeCheck rootNode = new JSONTreeCheck();
	        //根据某一设备id,解析viewName.xml，获得该设备的所有端口
	        String path = Constants.webRealPath + "file/topo/topoHis/";
	        String original = "";
	        if(viewName.indexOf("[")==-1){
	        	original = viewName;
	        }else{
	        	original = viewName.substring(0,viewName.indexOf("["));
	        }
	        Document document = new FileDAO().readXML(path+original+".xml");
			if (document != null) {
				Element root = document.getRootElement();
				List<Element> devices = root.element("devices").elements();
				for (Element device : devices) {
					String id = device.elementText("id");
					String name = device.elementText("name");
					String IP = device.elementText("IP");
					if(id.equals(deviceId)){
					    rootNode.setId("d"+deviceId);//为避免设备id和端口的ifindex值相同，将设备id加前缀"d"
					    rootNode.setText(name);
					    rootNode.setLeaf(false);
				        rootNode.setIconCls("test");
				        rootNode.setExpandable(true);
						List<Element> intfs = device.element("interfaces").elements();
						for (Element intf : intfs) {
							String ifIndex = intf.elementText("ifIndex");
							JSONTreeCheck treeNode = new JSONTreeCheck();
							treeNode.setId(name+"_"+IP+"_"+ifIndex);
							treeNode.setText(ifIndex);//此处应该添加端口描述，但xml文件未提供该值，暂时用ifindex代替（和朱确认，添加此项）
							treeNode.setCls("file");
							treeNode.setIconCls("test");
				            treeNode.setLeaf(true);
				            treeNode.setExpandable(false);
				            treeNode.setChecked(false);
				            rootNode.getChildren().add(treeNode);
						}
					}else{
						continue;
					}
				}
			}
	        TreeNodeArray.add(rootNode);
	        JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray);
	        return jsonarr.toString();
	}
	 
	 /**
		 * 生成资源树结构
		 * @param isChosed 该资源项是否已选
		 * @return 树型结构的Json字符串
		 * @throws Exception
		 */
		public String getChoseResource(boolean isChosed) throws Exception {
			choseTypeMap = new HashMap<String, JSONTreeCheck>();
			List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>();
			ResourceGroupPopedomDAO rgPopedomDao=new ResourceGroupPopedomDAO();
			List<ResourceGroupPopedom> rgs=rgPopedomDao.getResourceByGroupId(Long.parseLong(groupId));
			chosedNode=new HashMap<String,Long>();
			for(ResourceGroupPopedom rg:rgs){
				chosedNode.put(rg.getResourceId().toString(),rg.getResourceGroupId());
			}
			List<Resource> resources = dao.getAllResources();
			
			JSONTreeCheck rootNode = new JSONTreeCheck();
			makeGroupTree(resources,0,rootNode);
			List<JSONTreeCheck> nodes = rootNode.getChildren();
			for(JSONTreeCheck node:nodes){
				TreeNodeArray.add(node);
			}
			
			JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); // 得到JSON数组
			return jsonarr.toString();// 返回JSON数据
		}
		
		public static void makeGroupTree(List list, int id,JSONTreeCheck rootNode){
	        ArrayList childList = new ArrayList(10);
	        for (int i = 0; i < list.size(); i++) {
	        	Resource groupDTO = (Resource) list.get(i);
	            if (groupDTO.getParentsId().intValue()== id) {
	                childList.add(groupDTO);
	            }
	        }
			if(childList.size() > 0){
		        for ( int i=0; i<childList.size(); i++ ) {
		        	Resource groupDTO = (Resource)childList.get(i);
		        	JSONTreeCheck treeNode = new JSONTreeCheck();
					treeNode.setId(groupDTO.getId().toString());
					treeNode.setText(groupDTO.getName());
					treeNode.setCls("file");
					treeNode.setIconCls("test");
					if(groupDTO.getResourceTypeId()==4){
						treeNode.setLeaf(true);
					}else{
						treeNode.setLeaf(false);
					}
		            treeNode.setExpandable(false);
		            if(chosedNode.containsKey(groupDTO.getId().toString())){
						treeNode.setChecked(true);
					}else{
						treeNode.setChecked(false);
					}
		            rootNode.getChildren().add(treeNode);
		            makeGroupTree(list, groupDTO.getId().intValue(),treeNode);
		        }
			}
	    }
		public static void main(String[] args){
			List<JSONTreeCheck> TreeNodeArray = new ArrayList<JSONTreeCheck>(); 
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			Transaction transaction = session.beginTransaction();
			List<Resource> resources = session.createCriteria(Resource.class).list();
			JSONTreeCheck rootNode = new JSONTreeCheck();
			makeGroupTree(resources,0,rootNode);
			transaction.commit();
			TreeNodeArray.add(rootNode);
			JSONArray jsonarr = JSONArray.fromObject(TreeNodeArray); // 得到JSON数组
			System.out.println("last result:"+jsonarr.toString());
//			String name = "guoxi644_df_26";
//			System.out.println(name.substring(0,name.lastIndexOf("_")));
		}
	
		/**
		 * 递归生成资源树结构
		 * @param res 根节点对象
		 */
		public void createTree(Resource res) {
			Long parentId = res.getParentsId();
			if (parentId > 0) {
				if (res.getResourceTypeId() == 4) {
					JSONTreeCheck treeNode = new JSONTreeCheck();
					treeNode.setId(res.getId().toString());
					treeNode.setText(res.getName());
					treeNode.setLeaf(true);
					treeNode.setExpandable(false);
					if(chosedNode.containsKey(res.getId().toString())){
						treeNode.setChecked(true);
					}
					if (!choseTypeMap.containsKey(parentId.toString())) {
						JSONTreeCheck typeNode = new JSONTreeCheck();
						typeNode.setId(parentId.toString());
						typeNode.setText(dao.getNameById(parentId));
						typeNode.setLeaf(false);
						typeNode.setExpandable(true);
						if(chosedNode.containsKey(parentId.toString())){
							typeNode.setChecked(true);
						}
						choseTypeMap.put(parentId.toString(), typeNode);
					}
					choseTypeMap.get(parentId.toString()).getChildren().add(
							treeNode);
				} else {
					if (!choseTypeMap.containsKey(res.getId().toString())) {
						JSONTreeCheck treeNode = new JSONTreeCheck();
						treeNode.setId(res.getId().toString());
						treeNode.setText(res.getName());
						treeNode.setLeaf(false);
						treeNode.setExpandable(true);
						if(chosedNode.containsKey(res.getId().toString())){
							treeNode.setChecked(true);
						}
					}
					if (!choseTypeMap.containsKey(parentId.toString())) {
						JSONTreeCheck typeNode = new JSONTreeCheck();
						typeNode.setId(parentId.toString());
						typeNode.setText(dao.getNameById(parentId));
						typeNode.setLeaf(false);
						typeNode.setExpandable(true);
						if(chosedNode.containsKey(parentId.toString())){
							typeNode.setChecked(true);
						}
						choseTypeMap.put(parentId.toString(), typeNode);
						choseTypeMap.get(parentId.toString()).getChildren().add(
								choseTypeMap.get(res.getId().toString()));
					}
				}
				createTree(dao.getResourceById(parentId));
			}

		}
    public String getViewName() {
    	return viewName;
    }
    
    public void setViewName(String viewName) {
    	this.viewName = viewName;
    }
    
    public String getDeviceId() {
		return deviceId;
	}
    
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getChosedId() {
		return chosedId;
	}
	
	public void setChosedId(String chosedId) {
		this.chosedId = chosedId;
	}
	
	public String getInterfaceId() {
		return interfaceId;
	}
	
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	
	public String getChosedFile() {
		return chosedFile;
	}

	public void setChosedFile(String chosedFile) {
		this.chosedFile = chosedFile;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
