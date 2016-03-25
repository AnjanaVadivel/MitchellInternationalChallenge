/**
 * MitchellWSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mitchell.com.web.service;

public class MitchellWSServiceLocator extends org.apache.axis.client.Service implements mitchell.com.web.service.MitchellWSService {

    public MitchellWSServiceLocator() {
    }


    public MitchellWSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public MitchellWSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for MitchellWS
    private java.lang.String MitchellWS_address = "http://localhost:8080/MitchellCodingExercise/services/MitchellWS";

    public java.lang.String getMitchellWSAddress() {
        return MitchellWS_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String MitchellWSWSDDServiceName = "MitchellWS";

    public java.lang.String getMitchellWSWSDDServiceName() {
        return MitchellWSWSDDServiceName;
    }

    public void setMitchellWSWSDDServiceName(java.lang.String name) {
        MitchellWSWSDDServiceName = name;
    }

    public mitchell.com.web.service.MitchellWS getMitchellWS() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(MitchellWS_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getMitchellWS(endpoint);
    }

    public mitchell.com.web.service.MitchellWS getMitchellWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            mitchell.com.web.service.MitchellWSSoapBindingStub _stub = new mitchell.com.web.service.MitchellWSSoapBindingStub(portAddress, this);
            _stub.setPortName(getMitchellWSWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setMitchellWSEndpointAddress(java.lang.String address) {
        MitchellWS_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (mitchell.com.web.service.MitchellWS.class.isAssignableFrom(serviceEndpointInterface)) {
                mitchell.com.web.service.MitchellWSSoapBindingStub _stub = new mitchell.com.web.service.MitchellWSSoapBindingStub(new java.net.URL(MitchellWS_address), this);
                _stub.setPortName(getMitchellWSWSDDServiceName());
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
        if ("MitchellWS".equals(inputPortName)) {
            return getMitchellWS();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.web.com.mitchell", "MitchellWSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.web.com.mitchell", "MitchellWS"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("MitchellWS".equals(portName)) {
            setMitchellWSEndpointAddress(address);
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
