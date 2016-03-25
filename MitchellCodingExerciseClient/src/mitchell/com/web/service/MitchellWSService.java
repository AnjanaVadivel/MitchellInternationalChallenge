/**
 * MitchellWSService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mitchell.com.web.service;

public interface MitchellWSService extends javax.xml.rpc.Service {
    public java.lang.String getMitchellWSAddress();

    public mitchell.com.web.service.MitchellWS getMitchellWS() throws javax.xml.rpc.ServiceException;

    public mitchell.com.web.service.MitchellWS getMitchellWS(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
