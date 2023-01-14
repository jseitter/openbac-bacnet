package io.openbac.api.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openbac.bacnet.object.BACnetDeviceObject;
import io.openbac.bacnet.object.BACnetObject;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;

public class BACnetLocalDevice {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetLocalDevice.class);
	
	
	/**
	 * The BACnet Device Object for this local device
	 */
    private final BACnetDeviceObject deviceObject = new BACnetDeviceObject();

    /**
     * The map of objects of this device
     */
    private final HashMap<BACnetObjectIdentifier, BACnetObject> objectMap = new HashMap<>();
    
    
    /**
     * The list of known remote devices 
     * 
     */
    private List<BACnetRemoteDevice> remoteDeviceList = new ArrayList<>();
    
    
	public BACnetDeviceObject getDeviceObject() {
		return deviceObject;
	}


	public void addObject(Integer instanceNr, BACnetObject object) {
		
		
		
	
		BACnetObjectIdentifier id = new BACnetObjectIdentifier(null, instanceNr);
		objectMap.put(id, object);
	}
	
}
