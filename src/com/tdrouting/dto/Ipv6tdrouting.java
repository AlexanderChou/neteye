package com.tdrouting.dto;

import java.io.Serializable;

public class Ipv6tdrouting implements Serializable{
	private static final long serialVersionUID = 1L;
	private String dstprefix;
	private String srcprefix;
	private String outinterface;
	private String nexthop;
	private String routername;
	private Integer flag;
	
	
	public Ipv6tdrouting() {}
	
	public String getDstprefix()
	{
		return this.dstprefix;
	}
	
	public String getSrcprefix()
	{
		return this.srcprefix;
	}
	
	public String getOutinterface()
	{
		return this.outinterface;
	}
	
	public String getNexthop()
	{
		return this.nexthop;
	}
	
	public String getRoutername()
	{
		return this.routername;
	}
	
	public Integer getFlag()
	{
		return this.flag;
	}
	
	public void setDstprefix(String _dst)
	{
		this.dstprefix = _dst;
	}
	
	public void setSrcprefix(String _src) {
		this.srcprefix = _src;
	}
	
	public void setNexthop(String _nexthop)
	{
		this.nexthop = _nexthop;
	}
	
	public void setOutinterface(String _out) 
	{
		this.outinterface = _out;
	}
	
	public void setFlag(Integer _flag)
	{
		this.flag = _flag;
	}
	
	public void setRoutername(String _router)
	{
		this.routername = _router;
	}
	
	@Override
	public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dstprefix == null) ? 0 : dstprefix.hashCode());
        result = prime * result + ((srcprefix == null) ? 0 : srcprefix.hashCode());
        result = prime * result + ((routername == null) ? 0 : routername.hashCode());
        return result;
    }
	
	@Override
	public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Ipv6tdrouting other = (Ipv6tdrouting) obj;
        if (dstprefix == null)
        {
            if (other.dstprefix != null)
                return false;
        }
        else if (!dstprefix.equals(other.dstprefix))
            return false;
        if (srcprefix == null)
        {
            if (other.srcprefix != null)
                return false;
        }
        else if (!srcprefix.equals(other.srcprefix))
            return false;
        if (routername == null)
        {
            if (other.routername != null)
                return false;
        }
        else if (!routername.equals(other.routername))
            return false;
        return true;
    }
}