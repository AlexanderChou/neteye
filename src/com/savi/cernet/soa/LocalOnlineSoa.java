/**
 * LocalOnlineSoa.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.savi.cernet.soa;

public interface LocalOnlineSoa extends java.rmi.Remote {
    public java.lang.String getOnlineUserByLogin(java.lang.String authType, java.lang.String authenticateStr, java.lang.String authenticateReserved, java.lang.String randomNum, java.lang.String theTimestamp, java.lang.String login) throws java.rmi.RemoteException;
    public java.lang.String getOnlineUserByIp(java.lang.String authType, java.lang.String authenticateStr, java.lang.String authenticateReserved, java.lang.String randomNum, java.lang.String theTimestamp, java.lang.String ip) throws java.rmi.RemoteException;
    public java.lang.String getOnlineList(java.lang.String authType, java.lang.String authenticateStr, java.lang.String authenticateReserved, java.lang.String randomNum, java.lang.String theTimestamp) throws java.rmi.RemoteException;
}
