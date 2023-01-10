package io.openbac.bacnet.object;

import io.openbac.bacnet.object.BACnetObjectType.ObjectType;
import io.openbac.bacnet.type.BACnetSequenceOf;
import io.openbac.bacnet.type.enumerated.BACnetDeviceStatus;
import io.openbac.bacnet.type.enumerated.BACnetServicesSupported;
import io.openbac.bacnet.type.primitive.BACnetBitString;
import io.openbac.bacnet.type.primitive.BACnetCharacterString;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

/**
 * Represents a device
 * @author Joerg Seitter
 *
 */
public class BACnetDeviceObject {

	//hardcoded supported services (see below)
	private BACnetServicesSupported.Service[] supportedServices = {BACnetServicesSupported.Service.who_is,BACnetServicesSupported.Service.i_am};	
	private BACnetObjectIdentifier objectIdentifier=new BACnetObjectIdentifier(BACnetObjectType.ObjectType.DEVICE, 3330);
	private BACnetCharacterString objectName=new BACnetCharacterString("TestDevice");
	private BACnetObjectType.ObjectType objectType = BACnetObjectType.ObjectType.DEVICE;
	private BACnetDeviceStatus systemStatus = BACnetDeviceStatus.operational;
	private BACnetCharacterString vendorName=new BACnetCharacterString("openbac.io");
	private BACnetUnsignedInteger vendorIdentifier=new BACnetUnsignedInteger(911); // Is this correct Unsigned16
	private BACnetCharacterString modelName = new BACnetCharacterString("openBAC");
	private BACnetCharacterString firmwareRevision = new BACnetCharacterString("1.0");
	private BACnetCharacterString applicationSoftwareVersion;
	private BACnetCharacterString location;
	private BACnetCharacterString description;
	private BACnetUnsignedInteger protocolVersion=new BACnetUnsignedInteger(1);
	private BACnetUnsignedInteger protocolRevision=new BACnetUnsignedInteger(12);
	private BACnetBitString protocolServicesSupported = new BACnetServicesSupported(supportedServices).getServicesSupported();  // helper class needed for this
//	private BACnetObjectTypesSupported protocolObjectTypesSupported;
	private BACnetSequenceOf<BACnetObjectIdentifier> objectList=new BACnetSequenceOf<>();
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
	
	
	public BACnetDeviceObject() {
		//TODO: sample init for testing
		
		objectList.add(new BACnetObjectIdentifier(ObjectType.DEVICE, 3330));
//		objectList.add(new BACnetObjectIdentifier(ObjectType.ANALOG_INPUT, 1000));
//		objectList.add(new BACnetObjectIdentifier(ObjectType.ANALOG_OUTPUT, 11000));
		objectList.add(new BACnetObjectIdentifier(ObjectType.ANALOG_VALUE, 2));

//		objectList.add(new BACnetObjectIdentifier(ObjectType.BINARY_INPUT, 90000));
//		objectList.add(new BACnetObjectIdentifier(ObjectType.BINARY_OUTPUT, 12000));
//		objectList.add(new BACnetObjectIdentifier(ObjectType.BINARY_VALUE, 23000));

	}


	public BACnetServicesSupported.Service[] getSupportedServices() {
		return supportedServices;
	}


	public BACnetObjectIdentifier getObjectIdentifier() {
		return objectIdentifier;
	}


	public BACnetCharacterString getObjectName() {
		return objectName;
	}


	public BACnetObjectType.ObjectType getObjectType() {
		return objectType;
	}


	public BACnetDeviceStatus getSystemStatus() {
		return systemStatus;
	}


	public BACnetCharacterString getVendorName() {
		return vendorName;
	}


	public BACnetUnsignedInteger getVendorIdentifier() {
		return vendorIdentifier;
	}


	public BACnetCharacterString getModelName() {
		return modelName;
	}


	public BACnetCharacterString getFirmwareRevision() {
		return firmwareRevision;
	}


	public BACnetCharacterString getApplicationSoftwareVersion() {
		return applicationSoftwareVersion;
	}


	public BACnetCharacterString getLocation() {
		return location;
	}


	public BACnetCharacterString getDescription() {
		return description;
	}


	public BACnetUnsignedInteger getProtocolVersion() {
		return protocolVersion;
	}


	public BACnetUnsignedInteger getProtocolRevision() {
		return protocolRevision;
	}


	public BACnetBitString getProtocolServicesSupported() {
		return protocolServicesSupported;
	}


	public BACnetSequenceOf<BACnetObjectIdentifier> getObjectList() {
		return objectList;
	}


	public BACnetUnsignedInteger getMaxApduLengthAccepted() {
		return maxApduLengthAccepted;
	}


	public BACnetUnsignedInteger getApduTimeout() {
		return apduTimeout;
	}


	public BACnetUnsignedInteger getNumberOfApduRetries() {
		return numberOfApduRetries;
	}


	public BACnetUnsignedInteger getDatabaseRevision() {
		return databaseRevision;
	}
	
	/*
	 * CREATED METHODS
	 */
	
	
}
