//package io.openbac.service.unconfirmed;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.buffer.ByteBuf;
//import io.openbac.bacnet.exceptions.BACnetParseException;
//import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;
//import io.openbac.net.apdu.BACnetAPDU;
//
//public class WhoIsService extends BACnetUnconfirmedService {
//
//	private static final Logger LOG = LoggerFactory.getLogger(WhoIsService.class);
//
//	private BACnetUnsignedInteger deviceInstanceRangeLowLimit;
//	private BACnetUnsignedInteger deviceInstanceRangeHighLimit;
//
////	public UnconfirmedServiceChoiceType getServiceChoiceType() {
////		return UnconfirmedServiceChoiceType.WHO_IS;
////	}
//
//	public WhoIsService(BACnetAPDU apdu) throws BACnetParseException {
//		
////		super(apdu);
//		ByteBuf rawAPDU = apdu.getPayload();
//		// check if the WhoIS Request has parameters
//		if (rawAPDU.readableBytes() > 0) {
//			// expecting 2 unsigned integer
//			LOG.debug("expecting unsigned integer for deviceInstanceRangeLowLimit");
////			deviceInstanceRangeLowLimit = (BACnetUnsignedInteger) BACnetTagParser.parseTag(rawAPDU,
////					BACnetUnsignedInteger.class, 0);
//			LOG.debug("expecting unsigned integer for deviceInstanceRangeHighLimit");
////			deviceInstanceRangeHighLimit = (BACnetUnsignedInteger) BACnetTagParser.parseTag(rawAPDU,
////					BACnetUnsignedInteger.class, 1);
//
//			// if not the complete range is addressed and everybody who gets
//			// this request
//			// should reply with iam
//			if (deviceInstanceRangeLowLimit == null || deviceInstanceRangeHighLimit == null) {
//				deviceInstanceRangeLowLimit = new BACnetUnsignedInteger(0);
//				deviceInstanceRangeHighLimit = new BACnetUnsignedInteger(4294967296l);
//			}
//
//		}
//		LOG.debug(this.toString());
//
//	}
//
//	@Override
//	public String toString() {
//		return "WhoIsService{" + "deviceInstanceRangeLowLimit=" + deviceInstanceRangeLowLimit
//				+ ", deviceInstanceRangeHighLimit=" + deviceInstanceRangeHighLimit + '}';
//	}
//
//	/**
//	 * @return the deviceInstanceRangeLowLimit
//	 */
//	public BACnetUnsignedInteger getDeviceInstanceRangeLowLimit() {
//		return deviceInstanceRangeLowLimit;
//	}
//
//	/**
//	 * @param deviceInstanceRangeLowLimit
//	 *            the deviceInstanceRangeLowLimit to set
//	 */
//	public void setDeviceInstanceRangeLowLimit(BACnetUnsignedInteger deviceInstanceRangeLowLimit) {
//		this.deviceInstanceRangeLowLimit = deviceInstanceRangeLowLimit;
//	}
//
//	/**
//	 * @return the deviceInstanceRangeHighLimit
//	 */
//	public BACnetUnsignedInteger getDeviceInstanceRangeHighLimit() {
//		return deviceInstanceRangeHighLimit;
//	}
//
//	/**
//	 * @param deviceInstanceRangeHighLimit
//	 *            the deviceInstanceRangeHighLimit to set
//	 */
//	public void setDeviceInstanceRangeHighLimit(BACnetUnsignedInteger deviceInstanceRangeHighLimit) {
//		this.deviceInstanceRangeHighLimit = deviceInstanceRangeHighLimit;
//	}
//
//}
