package io.openbac.bacnet.object;

import io.openbac.bacnet.type.enumerated.BACnetServicesSupported;
import io.openbac.bacnet.type.primitive.BACnetBitString;
import io.openbac.bacnet.type.primitive.BACnetCharacterString;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

public class BACnetDeviceObject implements BACnetObject {

	//hardcoded supported services (see below)
	private BACnetServicesSupported.Service[] supportedServices = {BACnetServicesSupported.Service.who_is,BACnetServicesSupported.Service.i_am};
	
	private BACnetObjectIdentifier objectIdentifier=new BACnetObjectIdentifier(BACnetObjectType.ObjectType.DEVICE, 1);
	private BACnetCharacterString objectName=new BACnetCharacterString("TestDevice");
	private BACnetObjectType.ObjectType objectType = BACnetObjectType.ObjectType.DEVICE;
//	private BACnetDeviceStatus systemStatus = BACnetDeviceStatus.operational;
	private BACnetCharacterString vendorName=new BACnetCharacterString("openbac.io");
	private BACnetUnsignedInteger vendorIdentifier; // Is this correct Unsigned16
	private BACnetCharacterString modelName;
	private BACnetCharacterString firmwareRevision;
	private BACnetCharacterString applicationSoftwareVersion;
	private BACnetCharacterString location;
	private BACnetCharacterString description;
	private BACnetUnsignedInteger protocolVersion=new BACnetUnsignedInteger(1);
	private BACnetUnsignedInteger protocolRevision=new BACnetUnsignedInteger(12);
	private BACnetBitString protocolServicesSupported = new BACnetServicesSupported(supportedServices).getServicesSupported();  // helper class needed for this
//	private BACnetObjectTypesSupported protocolObjectTypesSupported;
//	private BACnetARRAY[N]of BACnetObjectIdentifier 
	//structuredObjectList (optional)
	private BACnetUnsignedInteger maxApduLengthAccepted=new BACnetUnsignedInteger(1476);
//	private BACnetSegmentation segmentationSupported = BACnetSegmentation.noSegmentation; // hardcoded to no segmentation
	//Max_Segments_Accepted need if we do segmentation
	// skipping optional
	private BACnetUnsignedInteger apduTimeout;
	private BACnetUnsignedInteger numberOfApduRetries;
	// skipping optional
//	private List of BACnetAddressBinding deviceAddressBinding;
	private BACnetUnsignedInteger databaseRevision;
	
	
}
