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
public class WhoIsService {

	private static final Logger LOG = LoggerFactory.getLogger(WhoIsService.class);

	/**
	 * optional parameter
	 */
	private BACnetUnsignedInteger deviceInstanceRangeLowLimit = null;
	/**
	 * optional parameter
	 */
	private BACnetUnsignedInteger deviceInstanceRangeHighLimit = null;

	public WhoIsService(final ByteBuf apdu) throws BACnetParseException {

		// check if the WhoIS Request has parameters
		if (apdu.readableBytes() > 0) {
			// expecting 2 unsigned integer
			LOG.debug("expecting unsigned integer for deviceInstanceRangeLowLimit");
			deviceInstanceRangeLowLimit = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);
			LOG.debug("expecting unsigned integer for deviceInstanceRangeHighLimit");
			deviceInstanceRangeHighLimit = BACnetPrimitive.createPrimitive(BACnetUnsignedInteger.class, apdu);
		}
		// if not the complete range is addressed and everybody who gets
		// this request should reply with iam
		if (deviceInstanceRangeLowLimit == null || deviceInstanceRangeHighLimit == null) {
			deviceInstanceRangeLowLimit = new BACnetUnsignedInteger(0);
			deviceInstanceRangeHighLimit = new BACnetUnsignedInteger(4294967296l);
		}
		LOG.debug(this.toString());

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
