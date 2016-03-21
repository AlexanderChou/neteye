package com.flow.dto;

public class FlowListTemp {
		private String ip;//设备IP地址(ipv4|ipv6)
		private String name;//综合显示设备的名称(设备描述|设备中文名称|英文名称)
		private String inf;//端口ifindex
		private String ipde;//端口描述description
		private String ipv6;//端口ipv6地址
		private String ipname;//综合显示设备的名称(设备描述|设备中文名称|英文名称)_设备IP地址（ipv4|ipv6）_端口ifindex
		private String pic1;//比特
		private String pic2;//分组
		private String pic3;//包长
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getInf() {
			return inf;
		}
		public void setInf(String inf) {
			this.inf = inf;
		}
		public String getPic1() {
			return pic1;
		}
		public void setPic1(String pic1) {
			this.pic1 = pic1;
		}
		public String getPic2() {
			return pic2;
		}
		public void setPic2(String pic2) {
			this.pic2 = pic2;
		}
		public String getPic3() {
			return pic3;
		}
		public void setPic3(String pic3) {
			this.pic3 = pic3;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public String getIpde() {
			return ipde;
		}
		public void setIpde(String ipde) {
			this.ipde = ipde;
		}
		public String getIpv6() {
			return ipv6;
		}
		public void setIpv6(String ipv6) {
			this.ipv6 = ipv6;
		}
		public String getIpname() {
			return ipname;
		}
		public void setIpname(String ipname) {
			this.ipname = ipname;
		}
		
}
