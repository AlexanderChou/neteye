/**
 * LocalOnlineSoaServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.savi.cernet.soa;

public class LocalOnlineSoaServiceLocator extends org.apache.axis.client.Service implements com.savi.cernet.soa.LocalOnlineSoaService {

    public LocalOnlineSoaServiceLocator(String portAddress) {
    	this.localOnlineSoaPort_address=portAddress;
    }


    public LocalOnlineSoaServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LocalOnlineSoaServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for localOnlineSoaPort
    private java.lang.String localOnlineSoaPort_address;

    public java.lang.String getlocalOnlineSoaPortAddress() {
        return localOnlineSoaPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String localOnlineSoaPortWSDDServiceName = "localOnlineSoaPort";

    public java.lang.String getlocalOnlineSoaPortWSDDServiceName() {
        return localOnlineSoaPortWSDDServiceName;
    }

    public void setlocalOnlineSoaPortWSDDServiceName(java.lang.String name) {
        localOnlineSoaPortWSDDServiceName = name;
    }

    public com.savi.cernet.soa.LocalOnlineSoa getlocalOnlineSoaPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(localOnlineSoaPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getlocalOnlineSoaPort(endpoint);
    }

    public com.savi.cernet.soa.LocalOnlineSoa getlocalOnlineSoaPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.savi.cernet.soa.LocalOnlineSoaPortBindingStub _stub = new com.savi.cernet.soa.LocalOnlineSoaPortBindingStub(portAddress, this);
            _stub.setPortName(getlocalOnlineSoaPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setlocalOnlineSoaPortEndpointAddress(java.lang.String address) {
        localOnlineSoaPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.savi.cernet.soa.LocalOnlineSoa.class.isAssignableFrom(serviceEndpointInterface)) {
                com.savi.cernet.soa.LocalOnlineSoaPortBindingStub _stub = new com.savi.cernet.soa.LocalOnlineSoaPortBindingStub(new java.net.URL(localOnlineSoaPort_address), this);
                _stub.setPortName(getlocalOnlineSoaPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("localOnlineSoaPort".equals(inputPortName)) {
            return getlocalOnlineSoaPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://soa.cernet.com/", "localOnlineSoaService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://soa.cernet.com/", "localOnlineSoaPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("localOnlineSoaPort".equals(portName)) {
            setlocalOnlineSoaPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
