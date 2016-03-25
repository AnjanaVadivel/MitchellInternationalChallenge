package mitchell.com.web.service;

public class MitchellWSProxy implements mitchell.com.web.service.MitchellWS {
  private String _endpoint = null;
  private mitchell.com.web.service.MitchellWS mitchellWS = null;
  
  public MitchellWSProxy() {
    _initMitchellWSProxy();
  }
  
  public MitchellWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initMitchellWSProxy();
  }
  
  private void _initMitchellWSProxy() {
    try {
      mitchellWS = (new mitchell.com.web.service.MitchellWSServiceLocator()).getMitchellWS();
      if (mitchellWS != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)mitchellWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)mitchellWS)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (mitchellWS != null)
      ((javax.xml.rpc.Stub)mitchellWS)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public mitchell.com.web.service.MitchellWS getMitchellWS() {
    if (mitchellWS == null)
      _initMitchellWSProxy();
    return mitchellWS;
  }
  
  public float addValue(float value) throws java.rmi.RemoteException{
    if (mitchellWS == null)
      _initMitchellWSProxy();
    return mitchellWS.addValue(value);
  }
  
  public float subtractValue(float value) throws java.rmi.RemoteException{
    if (mitchellWS == null)
      _initMitchellWSProxy();
    return mitchellWS.subtractValue(value);
  }
  
  
}