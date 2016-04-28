package com.tdrouting.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javassist.expr.NewArray;

import org.apache.log4j.chainsaw.Main;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.apache.struts2.json.annotations.JSON;
import com.tdrouting.dao.LinkDao;
import com.tdrouting.dao.TdroutingDao;
import com.tdrouting.dto.Link;
import com.tdrouting.dto.Node;
import com.tdrouting.dto.Router;


public class LinkAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private List<Link> edges;
	private List<Node> nodes;
	
	@JSON(serialize = false)
	public String getLink() throws Exception
	{
		nodes = new ArrayList<Node>();
		LinkDao linkDao = new LinkDao();
		List<Router> routers = new TdroutingDao().getRouterlist();
		HashMap<Integer, String> routerMap = new HashMap<Integer, String>();
		
		for (int i = 0; i < routers.size(); i++)
		{
			Router router = routers.get(i);
			routerMap.put(router.getId(), router.getName());
		}
		edges = linkDao.getLinks();
		
		HashSet<Integer> set = new HashSet<Integer>();
		int subnetCount = 0;
		for (int i = 0; i < edges.size(); i++)
		{
			Link link = edges.get(i);
			Integer from = link.getFrom();
			if (from == 0)
			{
				subnetCount--;
				nodes.add(new Node(subnetCount, ""));
				link.setFrom(subnetCount);
			}
			else {
				String routerString = routerMap.get(from);
				if (!set.contains(from))
				{
					set.add(from);
					nodes.add(new Node(from, routerString));
				}
			}
			Integer to = link.getTo();
			if (to == 0)
			{	
				subnetCount--;
				nodes.add(new Node(subnetCount, ""));
				link.setTo(subnetCount);
			}
			else {
				String routerString = routerMap.get(to);
				if (!set.contains(to))
				{
					set.add(to);
					nodes.add(new Node(to, routerString));
				}
			}
		}
		return SUCCESS;
	}

	public List<Link> getEdges()
	{
		return edges;
	}
	
	public List<Node> getNodes() 
	{
		return nodes;
	}
}