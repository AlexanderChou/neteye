package com.flow.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.base.model.Device;
import com.base.model.View;
import com.base.service.DeviceService;
import com.base.service.IfInterfaceService;
import com.base.service.PortService;
import com.base.util.BaseAction;
import com.base.util.Constants;
import com.base.util.W3CXML;
import com.flow.dao.FlowListViewDao;
import com.flow.dto.FlowListTemp;
import com.view.dao.ViewDAO;
import com.view.dto.InterfaceInfo;
import com.view.dto.Router;
import com.view.util.MyXmlUtil;

public class flowDeviceList extends BaseAction {
	private static final long serialVersionUID = 1L;
	private FlowListViewDao flowlistdao = new FlowListViewDao();
	private IfInterfaceService service = new IfInterfaceService();
	private DeviceService deviceService = new DeviceService();
	private Map<String, List<Router>> deviceMap = new TreeMap<String, List<Router>>();
	private List<FlowListTemp> flowlistdo;
	private List<FlowListTemp> allwatchlist;
	private Integer totalCount;

//	public String listflowEvent() throws Exception {
//		flowlistdo = new ArrayList();
//		String id = this.getRequest().getParameter("deviceid");
//		Long tmpid = Long.valueOf(id);
//		String ip = flowlistdao.getDeviceIp(tmpid);
//		flowlistdo = flowlistdao.getinf(ip);
//		setTotalCount(flowlistdo.size());
//		return SUCCESS;
//	}
	
	public String listallwatch() throws Exception{
		   List<FlowListTemp> inflistadd =new ArrayList<FlowListTemp>() ;
		   allwatchlist =new ArrayList();
		   ViewDAO AllviewDAO = new ViewDAO();
		   View  viewmain = AllviewDAO.getViewMain();
		   String name =viewmain.getName();
//		     List<View> viewall =  AllviewDAO.getAllViewByViewName();
//		     for(int i=0;i<viewall.size();i++){
//								String name = viewall.get(i).getName();
//								ViewDAO viewDAO = new ViewDAO();
//								View view = viewDAO.getViewByViewName(name);
								String filePath = Constants.webRealPath + "file/user/" + viewmain.getUserName() + "_" + viewmain.getUserId() + "/";
								File file=new File(filePath+name+".xml");
								if(file.exists()){
									List<Router> nodes = MyXmlUtil.getNodeIdList(filePath+name+".xml");
									Set viewlinktmp = flowlistdao.getlinkformview(filePath+name+".xml");
							//		List viewlink = new ArrayList(viewlinktmp);
									allwatchlist=flowlistdao.getinfinfo(viewlinktmp);
									
									
//									
//									for(int j=0;j<nodes.size();j++){
//				             	    	  List<FlowListTemp> inflisttmp =new ArrayList<FlowListTemp>() ;
//				             	    	  String ip = flowlistdao.getDeviceIp(Long.valueOf(nodes.get(j).getId()));
//				             	    	  inflisttmp = flowlistdao.getinf(ip);
//				             	    	 if(inflisttmp.size()>0){
//			             	    			 inflistadd.addAll(inflisttmp);} 
//				             	    	  
//									}
//									
//									for(int n=0;n<viewlink.size();n++){
//		             	    			   String[] viewl = viewlink.get(n).toString().split("_");
//		             	    			   String viewip =viewl[0];
//		             	    			   String viewinf = viewl[1];
//		             	    			   for(int m=0;m<inflistadd.size();m++){
//		             	    			  if(inflistadd.get(m).getIp().toString().equals(viewip)){
//		             	    				  if(inflistadd.get(m).getInf().toString().equals(viewinf)){
//		             	    					 allwatchlist.add(inflistadd.get(m));
//		             	    				  }
//		             	    			  }
//		             	    		  }
//		             	    		  
//		             	    	  }
//									deviceMap.put(name, nodes);
								}

//		     }
		     totalCount = allwatchlist.size();
		   return SUCCESS;
	   }

	public String listallwatchbak() throws Exception {
		// List<FlowListTemp> inflistadd =new ArrayList<FlowListTemp>() ;
		allwatchlist = new ArrayList<FlowListTemp>();
		ViewDAO AllviewDAO = new ViewDAO();
		View viewmain = AllviewDAO.getViewMain();
		String name = viewmain.getName();
		// List<View> viewall = AllviewDAO.getAllViewByViewName();
		// for(int i=0;i<viewall.size();i++){
		// String name = viewall.get(i).getName();
		ViewDAO viewDAO = new ViewDAO();
		View view = viewDAO.getViewByViewName(name);
		String filePath = Constants.webRealPath + "file/user/"
				+ view.getUserName() + "_" + view.getUserId() + "/";
		File file = new File(filePath + name + ".xml");
		if (file.exists()) {// 如果文件存在，获得该文件中包含的所有设备
			List<Router> nodes = MyXmlUtil.getNodeIdList(filePath + name + ".xml");
			for (int j = 0; j < nodes.size(); j++) {
				// 根据id获得设备对象
				Device device = deviceService.findById(Long.valueOf(nodes.get(j).getId()));
				// 根据设备的id求出该设备的所有端口
				String ip = "";
				if (device.getLoopbackIP() != null) {
					ip = device.getLoopbackIP();
				} else if (device.getLoopbackIPv6() != null) {
					ip = device.getLoopbackIPv6();
				}
				if (ip != null && !ip.equals("")) {
					String ipname = "";
					if (device.getChineseName() != null	&& !device.getChineseName().equals("")) {
						ipname = device.getChineseName();
					} else {
						ipname = device.getName();
					}
					List<InterfaceInfo> list = service.getIfInterfaceListBydeviceId(Long.valueOf(nodes.get(j).getId()), 0, 1000);
					File imgFile = null;
					for (InterfaceInfo dto : list) {
						FlowListTemp temp = new FlowListTemp();
						temp.setInf(String.valueOf(dto.getIndex()));
						temp.setIpde(dto.getDesc());
						temp.setIp(ip);
						temp.setIpv6(dto.getIpv6());
						temp.setName(ipname);
						String iptestname = ipname + "_" + ip + "_"	+ dto.getIndex();
						temp.setIpname(iptestname);
						// 最好判断一下该文件是否存在，如果不存在，就不再传入该集合
						imgFile = new File(	"/opt/NetEye/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_bit.gif");
						if (imgFile.exists()) {
							temp.setPic1("/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_bit.gif");
						} else {
							temp.setPic1("images/null.gif");
						}
						imgFile = new File("/opt/NetEye/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_pkt.gif");
						if (imgFile.exists()) {
							temp.setPic2("/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_pkt.gif");
						} else {
							temp.setPic2("images/null.gif");
						}
						imgFile = new File("/opt/NetEye/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_len.gif");
						if (imgFile.exists()) {
							temp.setPic3("/file/flow/flowscan/dat/pic/" + ip + "_" + dto.getIndex() + "_len.gif");
						} else {
							temp.setPic3("images/null.gif");
						}
						allwatchlist.add(temp);
					}
				}				
				deviceMap.put(name, nodes);
			}
		}

		// }
		totalCount = allwatchlist.size();
		System.out.println("end");
		return SUCCESS;
	}



	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public FlowListViewDao getFlowlistdao() {
		return flowlistdao;
	}

	public void setFlowlistdao(FlowListViewDao flowlistdao) {
		this.flowlistdao = flowlistdao;
	}

	public List<FlowListTemp> getFlowlistdo() {
		return flowlistdo;
	}

	public void setFlowlistdo(List<FlowListTemp> flowlistdo) {
		this.flowlistdo = flowlistdo;
	}

	public void setAllwatchlist(List<FlowListTemp> allwatchlist) {
		this.allwatchlist = allwatchlist;
	}

	public List<FlowListTemp> getAllwatchlist() {
		return allwatchlist;
	}

}
