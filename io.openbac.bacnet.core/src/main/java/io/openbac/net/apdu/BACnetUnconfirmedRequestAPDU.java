package io.openbac.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.net.npdu.BACnetNPDU;

public class BACnetUnconfirmedRequestAPDU extends BACnetAPDU {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetUnconfirmedRequestAPDU.class);



//	private UnconfirmedServiceChoiceType serviceChoiceType = null;


	@Override
	public PDUType getPDUType() {
		return PDUType.UNCONFIRMED_REQUEST;
	}

	/**
	 * decode APDU from NPDU
	 * @param npdu
	 * @throws BACnetParseException
	 */
	public BACnetUnconfirmedRequestAPDU(BACnetNPDU npdu) throws BACnetParseException {

		super(npdu);
		ByteBuf rawAPDU = npdu.getPayload();
		// skip apci octet
		rawAPDU.readerIndex(rawAPDU.readerIndex() + 1);
		byte serviceChoiceRaw = rawAPDU.readByte();
//		serviceChoiceType = UnconfirmedServiceChoiceType.getServiceChoiceType(serviceChoiceRaw);
//		LOG.debug("serviceChoice: 0x" + HexUtils.convert(serviceChoiceRaw) + ": " + serviceChoiceType.name());

		// copy the unprocessed part of the datagram
		payload = rawAPDU.slice();

	}
	
	/**
	 * encode APDU for service
	 * @param srv
	 */
//	public  BACnetUnconfirmedRequestAPDU(BACnetService srv) {
//		
//		
//	}

//	@Override
//	public String toString() {
//		return "UnconfirmedRequestAPDU{" + "serviceChoice=" + serviceChoiceType.getServiceChoiceType() + '}';
//	}

//	public UnconfirmedServiceChoiceType getServiceChoiceType() {
//		return serviceChoiceType;
//	}

}
