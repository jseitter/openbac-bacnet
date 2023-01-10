package io.openbac.service.unconfirmed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.object.BACnetDeviceObject;
import io.openbac.bacnet.object.BACnetObjectType;
import io.openbac.bacnet.type.enumerated.BACnetSegmentation;
import io.openbac.bacnet.type.primitive.BACnetEnumerated;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

public class BACnetIAmService extends BACnetUnconfirmedService{

	private static final Logger LOG = LoggerFactory.getLogger(BACnetIAmService.class);

	private BACnetObjectIdentifier objectIdentifier;
	private BACnetUnsignedInteger maxAPDULengthAccepted;
	private BACnetSegmentation segmentationSupported;
	private BACnetUnsignedInteger vendorID;

	@Override
	public byte getServiceChoice() {
		return BACnetUnconfirmedService.Choice.I_AM.serviceChoice;
	}
	
	/**
	 * default constructor uses device id 0 maxAPDU of 480 No segementation and
	 * vendorID of 0
	 */
	public BACnetIAmService(BACnetDeviceObject device) {
		this.objectIdentifier = device.getObjectIdentifier();
		this.maxAPDULengthAccepted = device.getMaxApduLengthAccepted();
		//TODO align this with the device object
		this.segmentationSupported = new BACnetSegmentation(BACnetSegmentation.noSegmentation);
		this.vendorID = new BACnetUnsignedInteger(195);
	}

	public BACnetIAmService(BACnetObjectIdentifier objectIdentifier, BACnetUnsignedInteger maxAPDULengthAccepted,
			BACnetSegmentation segmentationSupported, BACnetUnsignedInteger vendorID) {
		this.objectIdentifier = objectIdentifier;
		this.maxAPDULengthAccepted = maxAPDULengthAccepted;
		this.segmentationSupported = segmentationSupported;
		this.vendorID = vendorID;
	}

	/**
	 * decode service from APDU
	 * 
	 * @param apdu
	 * @throws BACnetParseException
	 */
	public BACnetIAmService(final ByteBuf apdu) throws BACnetParseException {

		LOG.debug("expecting BACnetObjectIdentifier for iAmDeviceIdentifier");
		objectIdentifier = BACnetPrimitive.createPrimitive(BACnetObjectIdentifier.class, apdu);
		LOG.debug("expecting unsigned integer for maxAPDULengthAccepted");
		maxAPDULengthAccepted = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);
		LOG.debug("expecting BACnetSegmentation, for segmentationSupported");
		segmentationSupported = new BACnetSegmentation(BACnetPrimitive.createPrimitive(BACnetEnumerated.class, apdu));
		LOG.debug("expecting unsigned integer for vendorID");
		vendorID = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);

		LOG.debug(this.toString());

	}

	/**
	 * @return the objectIdentifier
	 */
	public BACnetObjectIdentifier getObjectIdentifier() {
		return objectIdentifier;
	}

	/**
	 * @param objectIdentifier the objectIdentifier to set
	 */
	public void setObjectIdentifier(BACnetObjectIdentifier objectIdentifier) {
		this.objectIdentifier = objectIdentifier;
	}

	/**
	 * @return the maxAPDULengthAccepted
	 */
	public BACnetUnsignedInteger getMaxAPDULengthAccepted() {
		return maxAPDULengthAccepted;
	}

	/**
	 * @param maxAPDULengthAccepted the maxAPDULengthAccepted to set
	 */
	public void setMaxAPDULengthAccepted(BACnetUnsignedInteger maxAPDULengthAccepted) {
		this.maxAPDULengthAccepted = maxAPDULengthAccepted;
	}

	/**
	 * @return the segmentationSupported
	 */
	public BACnetSegmentation getSegmentationSupported() {
		return segmentationSupported;
	}

	/**
	 * @param segmentationSupported the segmentationSupported to set
	 */
	public void setSegmentationSupported(BACnetSegmentation segmentationSupported) {
		this.segmentationSupported = segmentationSupported;
	}

	/**
	 * @return the vendorID
	 */
	public BACnetUnsignedInteger getVendorID() {
		return vendorID;
	}

	/**
	 * @param vendorID the vendorID to set
	 */
	public void setVendorID(BACnetUnsignedInteger vendorID) {
		this.vendorID = vendorID;
	}

	/**
	 * Encodes the service choice and service details.
	 * @param buf the buffer to write to
	 */
	public void encode(final ByteBuf buf) {
		buf.writeByte(BACnetUnconfirmedService.Choice.I_AM.serviceChoice);
		objectIdentifier.encodeApplication(buf);
		maxAPDULengthAccepted.encodeApplication(buf);
		segmentationSupported.encodeApplication(buf);
		vendorID.encodeApplication(buf);
	}
	
	@Override
	public String toString() {
		return "IAmService{" + "objectIdentifier=" + objectIdentifier + ", maxAPDULengthAccepted="
				+ maxAPDULengthAccepted + ", segmentationSupported=" + segmentationSupported + ", vendorID=" + vendorID
				+ '}';
	}

}
