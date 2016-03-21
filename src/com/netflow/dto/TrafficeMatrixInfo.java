package com.netflow.dto;

public class TrafficeMatrixInfo {
   private String srcAs="";
   private String dstAs="";
   private String bytes="";

public String getDstAs() {
	return dstAs;
}
public void setDstAs(String dstAs) {
	this.dstAs = dstAs;
}
public String getBytes() {
	return bytes;
}
public void setBytes(String bytes) {
	this.bytes = bytes;
}
public String getSrcAs() {
	return srcAs;
}
public void setSrcAs(String srcAs) {
	this.srcAs = srcAs;
}
   
}
