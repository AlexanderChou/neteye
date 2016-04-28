package com.tdrouting.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javassist.expr.NewArray;

import org.apache.log4j.chainsaw.Main;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.apache.struts2.json.annotations.JSON;

import bsh.Console;

import com.tdrouting.dto.Edge;
import com.tdrouting.dto.Interface;
import com.tdrouting.dto.Ipv6routing;
import com.tdrouting.dto.Ipv6tdrouting;
import com.tdrouting.dto.Link;
import com.tdrouting.dto.Router;
import com.tdrouting.dto.Tdroutingcost;
import com.tdrouting.dao.InterfaceDao;
import com.tdrouting.dao.LinkDao;
import com.tdrouting.dao.RoutingtableDao;
import com.tdrouting.dao.TdroutingDao;

public class Ipv6routingAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String routername;
	private String srcRouter;
	private String dstPrefix;
	private String srcPrefix;
	private List<Ipv6routing> v6routing;
	private List<Ipv6tdrouting> v6tdrouting;
	private List<Edge> edges = new ArrayList<Edge>();
	
	@JSON(serialize = false)
	public String getIpv6routing() throws Exception
	{
		RoutingtableDao dao = new RoutingtableDao();
		v6routing = dao.getIpv6routing(routername);
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getIpv6tdrouting() throws Exception
	{
		RoutingtableDao dao = new RoutingtableDao();
		v6tdrouting = dao.getIpv6tdrouting(routername);
		return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String getRoutingpath()throws Exception
	{
		RoutingtableDao dao = new RoutingtableDao();
			
		List<Ipv6routing> routingTable = dao.getAllIpv6routing();
		List<Ipv6tdrouting> tdroutingTable = dao.getAllIpv6tdrouting();
		
		HashMap<String, String> routingMap = new HashMap<String, String>();
		for (int i = 0; i < routingTable.size(); i++)
		{
			Ipv6routing routing = routingTable.get(i);
			if (routing.getNexthop() == null || routing.getNexthop().equals(""))
				continue;
			String keyString = routing.getRoutername() + routing.getDstprefix().substring(0,routing.getDstprefix().length()-3);
			System.out.println(keyString);
			routingMap.put(keyString, routing.getNexthop());
		}
		
		List<Interface> interfaceList = new InterfaceDao().getAllInterfaces();
		HashMap<String, String> ifRouterMap = new HashMap<String, String>();
		for (int i = 0; i < interfaceList.size(); i++)
		{
			Interface iface = interfaceList.get(i);
			ifRouterMap.put(iface.getIpv6(), iface.getRoutername());
		}
		
		String nowRouterString = srcRouter;
		boolean flag = true;
		while (flag)
		{
			if (false)
			{
				//to do ! ! !
			}
			else 
			{
				String keyString = nowRouterString + dstPrefix;
				String outPrefix = routingMap.get(keyString); 
				if (outPrefix == null || outPrefix.equals(""))
				{
					flag = false;	
				} 
				else 
				{
					String preRouterString = nowRouterString;
					nowRouterString = ifRouterMap.get(outPrefix);
					edges.add(new Edge(preRouterString, nowRouterString));
				}
			}// end of search search routing table
		}// end of while
		return SUCCESS;
	}
	public void setRoutername(String _router)
	{
		this.routername = _router;
	}
	
	public void setSrcRouter(String _src) 
	{
		this.srcRouter = _src;
	}
	
	public void setDstPrefix(String _dst) 
	{
		this.dstPrefix = _dst;
	}
	
	public void setSrcPrefi(String _src)
	{
		this.setSrcPrefix(_src);
	}
	
	public List<Ipv6routing> getV6routing()
	{
		return v6routing;
	}
	
	public List<Ipv6tdrouting> getV6tdrouting() {
		return v6tdrouting;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}

	@JSON(serialize = false)
	public String getSrcPrefix() {
		return srcPrefix;
	}

	public void setSrcPrefix(String srcPrefix) {
		this.srcPrefix = srcPrefix;
	}
}