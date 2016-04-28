package com.tdrouting.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.tdrouting.util.GetDeviceInfo;
import com.tdrouting.dao.InterfaceDao;
import com.tdrouting.dao.LinkDao;
import com.tdrouting.dao.RoutingtableDao;
import com.tdrouting.dao.TdroutingDao;
import com.tdrouting.dto.Interface;
import com.tdrouting.dto.Ipv6routing;
import com.tdrouting.dto.Ipv6tdrouting;
import com.tdrouting.dto.Link;
import com.tdrouting.dto.Router;
import com.tdrouting.dto.Tdroutingcost;

public class CollectionTask {
	public static void beginTask()  {
		List<Router> routerList = new ArrayList<Router>();
		List<Ipv6routing> v6routingList = new ArrayList<Ipv6routing>();
		List<Ipv6tdrouting> v6tdroutingList = new ArrayList<Ipv6tdrouting>();
		List<Interface> interfaceList = new ArrayList<Interface>();
		List<Tdroutingcost> routingCostList = new ArrayList<Tdroutingcost>();
		
		try {
			routerList = GetDeviceInfo.getDeviceInfo();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// TODO:
		
		HashMap<String, Integer> rIdMap = new HashMap<String, Integer>(); 
		for (int i = 0; i < routerList.size(); i++)
		{
			routerList.get(i).setId(i+1);
			rIdMap.put(routerList.get(i).getName(), i+1);
			routerList.get(i).setNetconfuser("root");
			routerList.get(i).setType(1);
			routerList.get(i).setNetconfpasswd("root");
			String routerName = routerList.get(i).getName();
			
			if (IsSupportTwodRoute.isSupportTwodRoute(routerName) == false)
				SetSupportTwodRoute.setSupportTwodRoute(routerName);
			try {
				interfaceList.addAll(GetIetfInterfaceInfo.getIetfInterfaceInfo(routerName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				v6routingList.addAll(GetRoutingState.getRoutingState(routerName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				v6tdroutingList.addAll(GetTwodRoutingState.getTwodRoutingState(routerName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				routingCostList.addAll(GetTwodRoutingCost.getTwodRoutingCost(routerName));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end of loop 'for'*/
		
		HashSet<String> ifPrefixSet = new HashSet<String>();
		HashMap<String, String> ifMap = new HashMap<String, String>();
		HashMap<String, String> ipPrefiHashMap = new HashMap<String, String>();
		for (int i = 0; i < interfaceList.size(); i++)
		{
			Interface iface = interfaceList.get(i);
			iface.setId(i+1);
			ifPrefixSet.add(iface.getIpv6());
			ipPrefiHashMap.put(iface.getRoutername()+iface.getName(), iface.getIpv6());
			ifMap.put(iface.getIpv6(), iface.getRoutername());
		}
		
		List<Link> linkList = new ArrayList<Link>();
		for (int i = 0; i < v6routingList.size(); i++)
		{
			Ipv6routing routing = v6routingList.get(i);
			if (routing.getNexthop().equals("") == false)
			{
				Link link = new Link();
				String from = routing.getRoutername();
				String to = ifMap.get(routing.getNexthop());
				String fromPrefix = ipPrefiHashMap.get(from+routing.getOutinterface());
				String toPrefix = routing.getNexthop();
				if (ifPrefixSet.contains(fromPrefix) && ifPrefixSet.contains(toPrefix))
				{
					link.setFrom(rIdMap.get(from));
					link.setFromprefix(fromPrefix);
					link.setToprefix(toPrefix);
					link.setTo(rIdMap.get(to));
					linkList.add(link);
					ifPrefixSet.remove(fromPrefix);
					ifPrefixSet.remove(toPrefix);
				}
			}
		}// end of loop 'for'
		
		java.util.Iterator<String> iterator = ifPrefixSet.iterator();
		while (iterator.hasNext())
		{
			String prefixString = iterator.next();
			Link link = new Link();
			link.setFrom(0);
			link.setFromprefix(prefixString.substring(0,prefixString.length()-1));
			String toRouterString = ifMap.get(prefixString);
			link.setTo(rIdMap.get(toRouterString));
			link.setToprefix(prefixString);
			linkList.add(link);
		}

		TdroutingDao routerDao = new TdroutingDao();
		routerDao.clearAll();
		routerDao.save(routerList);
		
		InterfaceDao ifDao = new InterfaceDao();
		ifDao.clearAll();
		ifDao.save(interfaceList);
		
		RoutingtableDao routingtableDao = new RoutingtableDao();
		routingtableDao.clearAllRouting();
		routingtableDao.saveRouting(v6routingList);

		routingtableDao.clearAllTwodRouting();
		routingtableDao.saveTwodRouting(v6tdroutingList);
		
		System.out.println("cost:"+routingCostList.size());
		routingtableDao.clearAllCost();
		routingtableDao.saveCost(routingCostList);
		
		LinkDao linkDao = new LinkDao();
		linkDao.clearAll();
		linkDao.save(linkList);
	}// end of function
}