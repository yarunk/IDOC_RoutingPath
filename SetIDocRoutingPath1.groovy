import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.ITApi;
import com.sap.it.api.mapping.ValueMappingApi;

def Message processData(Message message) {
    
    def propertiesMap = message.getProperties();
    
    //def SNDPRN = propertiesMap.get("SNDPRN") as String

    def MESTYP = propertiesMap.get("MESTYP") as String

    String rcvPartnerProfile = ""; 
    rcvPartnerProfile = MESTYP;
    
    def valueMapApi = ITApiFactory.getApi(ValueMappingApi.class, null);

    if( valueMapApi != null) {

        def IDocRoutingPath = valueMapApi.getMappedValue("SAP ECC", 'SND_KEY', rcvPartnerProfile, 'SCPI', 'RCV_RESOURCE') as String
        
        
        if ( IDocRoutingPath.equals("") || IDocRoutingPath == null ){
            message.setProperty("IDOC_ROUTING_PATH", "ERROR_NOT_FOUND")
        }
        else {
            message.setProperty("IDOC_ROUTING_PATH", IDocRoutingPath)
        }
    }


    return message;
}