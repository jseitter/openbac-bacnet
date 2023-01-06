package io.openbac.service.unconfirmed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

/**
 * 
 * @author Joerg Seitter
 *
 */
public class BACnetWhoIsService extends BACnetUnconfirmedService {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetWhoIsService.class);

	/**
	 * optional parameter
	 */
	private BACnetUnsignedInteger deviceInstanceRangeLowLimit = null;
	/**
	 * optional parameter
	 */
	private BACnetUnsignedInteger deviceInstanceRangeHighLimit = null;

	@Override
	public byte getServiceChoice() {
		return BACnetUnconfirmedService.Choice.WHO_IS.serviceChoice;
	}
	
	/**
	 * Constrcutor decodes service details.
	 * It is expected that the service choice is evaluated before
	 * @param apdu
	 * @throws BACnetParseException
	 */
	public BACnetWhoIsService(final ByteBuf apdu)  throws BACnetParseException {

		// check if the WhoIS Request has parameters
		if (apdu.readableBytes() > 0) {
			// expecting 2 unsigned integer
			LOG.debug("expecting unsigned integer for deviceInstanceRangeLowLimit");
			deviceInstanceRangeLowLimit = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);
			LOG.debug("expecting unsigned integer for deviceInstanceRangeHighLimit");
			deviceInstanceRangeHighLimit = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);
		}
		LOG.debug(this.toString());

	}
	/**
	 * Encodes the service choice and service details.
	 * @param buf the buffer to write to
	 */
	public void encode(final ByteBuf buf) {
		buf.writeByte(BACnetUnconfirmedService.Choice.WHO_IS.serviceChoice);
		// if both are set then encode the fields to the buffer
		if (deviceInstanceRangeLowLimit == null || deviceInstanceRangeHighLimit == null) {
			deviceInstanceRangeLowLimit.encodeApplication(buf);
			deviceInstanceRangeHighLimit.encodeApplication(buf);
		}
	}
	
	
	@Override
	public String toString() {
		return "WhoIsService{" + "deviceInstanceRangeLowLimit=" + deviceInstanceRangeLowLimit
				+ ", deviceInstanceRangeHighLimit=" + deviceInstanceRangeHighLimit + '}';
	}

	/**
	 * @return the deviceInstanceRangeLowLimit
	 */
	public BACnetUnsignedInteger getDeviceInstanceRangeLowLimit() {
		return deviceInstanceRangeLowLimit;
	}

	/**
	 * @param deviceInstanceRangeLowLimit the deviceInstanceRangeLowLimit to set
	 */
	public void setDeviceInstanceRangeLowLimit(BACnetUnsignedInteger deviceInstanceRangeLowLimit) {
		this.deviceInstanceRangeLowLimit = deviceInstanceRangeLowLimit;
	}

	/**
	 * @return the deviceInstanceRangeHighLimit
	 */
	public BACnetUnsignedInteger getDeviceInstanceRangeHighLimit() {
		return deviceInstanceRangeHighLimit;
	}

	/**
	 * @param deviceInstanceRangeHighLimit the deviceInstanceRangeHighLimit to set
	 */
	public void setDeviceInstanceRangeHighLimit(BACnetUnsignedInteger deviceInstanceRangeHighLimit) {
		this.deviceInstanceRangeHighLimit = deviceInstanceRangeHighLimit;
	}


}
