package com.savi.base.model;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * Subnet entity. @author MyEclipse Persistence Tools
 */

public class Subnet implements java.io.Serializable {

	// Fields

	private Long id;
	private String name;
	private String ipv4subNet;
	private String ipv6subNet;
	private Integer isDelete;
	private Set<Switchbasicinfo> switchbasicinfos = new LinkedHashSet<Switchbasicinfo>();

	// Constructors
	/** default constructor */
	public Subnet() {
	}
	public Subnet(Long id){
		this.id=id;
	}
	/** full constructor */
	public Subnet(String name, String ipv4subNet, String ipv6subNet,
			Integer isDelete, Set switchbasicinfos, Set switchSubnets) {
		this.name = name;
		this.ipv4subNet = ipv4subNet;
		this.ipv6subNet = ipv6subNet;
		this.isDelete = isDelete;
		this.switchbasicinfos = switchbasicinfos;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpv4subNet() {
		return this.ipv4subNet;
	}

	public void setIpv4subNet(String ipv4subNet) {
		this.ipv4subNet = ipv4subNet;
	}

	public String getIpv6subNet() {
		return this.ipv6subNet;
	}

	public void setIpv6subNet(String ipv6subNet) {
		this.ipv6subNet = ipv6subNet;
	}

	public Integer getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Set<Switchbasicinfo> getSwitchbasicinfos() {
		return switchbasicinfos;
	}
	public void setSwitchbasicinfos(Set<Switchbasicinfo> switchbasicinfos) {
		this.switchbasicinfos = switchbasicinfos;
	}
	public static void main(String[] args) {
		Subnet subnet=new Subnet();
		Iterator<Switchbasicinfo> switchbasicinfoIterator=subnet.getSwitchbasicinfos().iterator();
		while(switchbasicinfoIterator.hasNext()){
			Switchbasicinfo switchbasicinfo=switchbasicinfoIterator.next();
			System.out.println("kkkk");
			if(switchbasicinfo.getIsDelete()==1){
				System.out.println("dddd");
			}
		}		
	}
}