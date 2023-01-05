package io.openbac.api.device;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.openbac.bacnet.object.BACnetDeviceObject;

public class BACnetLocalDevice {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetLocalDevice.class);
	
	/**
	 * The BACnet Device Object containing all BACnet Properties
	 */
    private final BACnetDeviceObject deviceObject = new BACnetDeviceObject();

    /**
     * 
     * 
     */
    private List<BACnetRemoteDevice> remoteDeviceList = new ArrayList<>();
    
    
	public BACnetDeviceObject getDeviceObject() {
		return deviceObject;
	}


	//TODO encapsulate the handler in a subclass/interface cinstruction to keep 
	// the LocalDevice API uncluttered
	
	/**
	 * Service Handler for WhoIs
	 * @param srv
	 * @param ctx
	 */
//   public void handleWhoIsService(WhoIsService srv, ChannelHandlerContext ctx) {
//	   LOG.debug("handle WhoIs");
//	   // the IAm Service should be created by the local device values ...
//	   // TODO fix this
//	   ctx.writeAndFlush(new IAmService());
	  
//   }

	/**
	 * Service Handler for IAm
	 * @param srv
	 * @param ctx
	 */

//   public void handleIAmService(IAmService srv, ChannelHandlerContext ctx) {
//	   LOG.debug("handle IAm");
//	  
//	   //remoteDeviceList.add(new BACnetRemoteDevice(,, srv.getMaxAPDULengthAccepted(), srv.getSegmentationSupported()))
//   }
}
