package com.tdrouting.action;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;
import org.json.JSONArray;

import com.tdrouting.dao.RoutingtableDao;
import com.tdrouting.dto.Tdroutingcost;
import com.tdrouting.util.AddTwodRoutingCost;
import com.tdrouting.util.DeleteTwodRoutingCost;
import com.tdrouting.util.UpdateTwodRoutingCost;
public class RoutingCostAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String routername;
	private List<Tdroutingcost> data;
	private String change;
	
	@JSON(serialize = false)
	public String getCost() throws Exception
	{
			RoutingtableDao dao = new RoutingtableDao();
			data = dao.getRoutingCost(routername);
			return SUCCESS;
	}
	
	@JSON(serialize = false)
	public String saveChanges() throws Exception
	{
			JSONArray jsonArray = new JSONArray(change);
			RoutingtableDao dao = new RoutingtableDao();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				org.json.JSONObject json = jsonArray.getJSONObject(i);
				String srcPrefix = json.getString("srcprefix");
				String dstPrefix = json.getString("dstprefix");
				int cost = json.getInt("cost");
				String status = json.getString("_state");
				
				Tdroutingcost routingCost = new Tdroutingcost();
				routingCost.setRoutername(routername);
				routingCost.setSrcprefix(srcPrefix);
				routingCost.setDstprefix(dstPrefix);
				routingCost.setCost(cost);

				if (status.equals("removed"))
				{
					dao.delRoutingCost(routingCost);
					DeleteTwodRoutingCost.deleteTwodRoutingCost(routername, dstPrefix, srcPrefix);
				}
				else if (status.equals("modified"))
				{
					dao.saveOrUpdate(routingCost);
					UpdateTwodRoutingCost.updateTwodRoutingCost(routername, dstPrefix, srcPrefix, cost);
				}
				else if (status.equals("added"))
				{
					dao.saveOrUpdate(routingCost);
					AddTwodRoutingCost.addTwodRoutingCost(routername, dstPrefix, srcPrefix, cost);

				}
			}
			return SUCCESS;
	}
	
	public void setRoutername(String _router)
	{
		this.routername = _router;
	}
	
	public List<Tdroutingcost> getData() {
		return data;
	}

	public void setData(List<Tdroutingcost> data) {
		this.data = data;
	}
	
	public void setChange(String _change)
	{
		this.change = _change;
	}
}