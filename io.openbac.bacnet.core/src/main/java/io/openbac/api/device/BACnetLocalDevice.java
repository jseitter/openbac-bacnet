package io.openbac.api.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openbac.bacnet.object.BACnetDeviceObject;

public class BACnetLocalDevice {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetLocalDevice.class);
	
	
	/**
	 * The BACnet Device Object for this local device
	 */
    private final BACnetDeviceObject deviceObject = new BACnetDeviceObject();

    /**
     * The list of known remote devices 
     * 
     */
    private List<BACnetRemoteDevice> remoteDeviceList = new ArrayList<>();
    
    
	public BACnetDeviceObject getDeviceObject() {
		return deviceObject;
	}


}
