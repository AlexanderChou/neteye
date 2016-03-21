/**
 * GetOnlineUserByIp.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.savi.cernet.soa;

public class GetOnlineUserByIp  implements java.io.Serializable {
    private java.lang.String authType;

    private java.lang.String authenticateReserved;

    private java.lang.String authenticateStr;

    private java.lang.String ip;

    private java.lang.String randomNum;

    private java.lang.String theTimestamp;

    public GetOnlineUserByIp() {
    }

    public GetOnlineUserByIp(
           java.lang.String authType,
           java.lang.String authenticateReserved,
           java.lang.String authenticateStr,
           java.lang.String ip,
           java.lang.String randomNum,
           java.lang.String theTimestamp) {
           this.authType = authType;
           this.authenticateReserved = authenticateReserved;
           this.authenticateStr = authenticateStr;
           this.ip = ip;
           this.randomNum = randomNum;
           this.theTimestamp = theTimestamp;
    }


    /**
     * Gets the authType value for this GetOnlineUserByIp.
     * 
     * @return authType
     */
    public java.lang.String getAuthType() {
        return authType;
    }


    /**
     * Sets the authType value for this GetOnlineUserByIp.
     * 
     * @param authType
     */
    public void setAuthType(java.lang.String authType) {
        this.authType = authType;
    }


    /**
     * Gets the authenticateReserved value for this GetOnlineUserByIp.
     * 
     * @return authenticateReserved
     */
    public java.lang.String getAuthenticateReserved() {
        return authenticateReserved;
    }


    /**
     * Sets the authenticateReserved value for this GetOnlineUserByIp.
     * 
     * @param authenticateReserved
     */
    public void setAuthenticateReserved(java.lang.String authenticateReserved) {
        this.authenticateReserved = authenticateReserved;
    }


    /**
     * Gets the authenticateStr value for this GetOnlineUserByIp.
     * 
     * @return authenticateStr
     */
    public java.lang.String getAuthenticateStr() {
        return authenticateStr;
    }


    /**
     * Sets the authenticateStr value for this GetOnlineUserByIp.
     * 
     * @param authenticateStr
     */
    public void setAuthenticateStr(java.lang.String authenticateStr) {
        this.authenticateStr = authenticateStr;
    }


    /**
     * Gets the ip value for this GetOnlineUserByIp.
     * 
     * @return ip
     */
    public java.lang.String getIp() {
        return ip;
    }


    /**
     * Sets the ip value for this GetOnlineUserByIp.
     * 
     * @param ip
     */
    public void setIp(java.lang.String ip) {
        this.ip = ip;
    }


    /**
     * Gets the randomNum value for this GetOnlineUserByIp.
     * 
     * @return randomNum
     */
    public java.lang.String getRandomNum() {
        return randomNum;
    }


    /**
     * Sets the randomNum value for this GetOnlineUserByIp.
     * 
     * @param randomNum
     */
    public void setRandomNum(java.lang.String randomNum) {
        this.randomNum = randomNum;
    }


    /**
     * Gets the theTimestamp value for this GetOnlineUserByIp.
     * 
     * @return theTimestamp
     */
    public java.lang.String getTheTimestamp() {
        return theTimestamp;
    }


    /**
     * Sets the theTimestamp value for this GetOnlineUserByIp.
     * 
     * @param theTimestamp
     */
    public void setTheTimestamp(java.lang.String theTimestamp) {
        this.theTimestamp = theTimestamp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GetOnlineUserByIp)) return false;
        GetOnlineUserByIp other = (GetOnlineUserByIp) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.authType==null && other.getAuthType()==null) || 
             (this.authType!=null &&
              this.authType.equals(other.getAuthType()))) &&
            ((this.authenticateReserved==null && other.getAuthenticateReserved()==null) || 
             (this.authenticateReserved!=null &&
              this.authenticateReserved.equals(other.getAuthenticateReserved()))) &&
            ((this.authenticateStr==null && other.getAuthenticateStr()==null) || 
             (this.authenticateStr!=null &&
              this.authenticateStr.equals(other.getAuthenticateStr()))) &&
            ((this.ip==null && other.getIp()==null) || 
             (this.ip!=null &&
              this.ip.equals(other.getIp()))) &&
            ((this.randomNum==null && other.getRandomNum()==null) || 
             (this.randomNum!=null &&
              this.randomNum.equals(other.getRandomNum()))) &&
            ((this.theTimestamp==null && other.getTheTimestamp()==null) || 
             (this.theTimestamp!=null &&
              this.theTimestamp.equals(other.getTheTimestamp())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAuthType() != null) {
            _hashCode += getAuthType().hashCode();
        }
        if (getAuthenticateReserved() != null) {
            _hashCode += getAuthenticateReserved().hashCode();
        }
        if (getAuthenticateStr() != null) {
            _hashCode += getAuthenticateStr().hashCode();
        }
        if (getIp() != null) {
            _hashCode += getIp().hashCode();
        }
        if (getRandomNum() != null) {
            _hashCode += getRandomNum().hashCode();
        }
        if (getTheTimestamp() != null) {
            _hashCode += getTheTimestamp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GetOnlineUserByIp.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://localhost/axis/services/LocalOnlineSoa", "GetOnlineUserByIp"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticateReserved");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authenticateReserved"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticateStr");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authenticateStr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ip");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ip"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("randomNum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "randomNum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("theTimestamp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "theTimestamp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
