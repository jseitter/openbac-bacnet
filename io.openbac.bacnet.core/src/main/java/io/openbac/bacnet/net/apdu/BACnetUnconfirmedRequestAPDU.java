package io.openbac.bacnet.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.service.unconfirmed.BACnetUnconfirmedService;
import io.openbac.util.HexUtils;

public class BACnetUnconfirmedRequestAPDU extends BACnetAPDU {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetUnconfirmedRequestAPDU.class);

	@Override
	public PDUType getPDUType() {
		return PDUType.UNCONFIRMED_REQUEST;
	}

	private ByteBuf buf;
	private BACnetUnconfirmedService srv;

	public BACnetUnconfirmedRequestAPDU(BACnetUnconfirmedService srv) {
		this.srv = srv;

	}

	/**
	 * decode APDU
	 * 
	 * @param apdu
	 * @throws BACnetParseException
	 */
	public BACnetUnconfirmedRequestAPDU(final ByteBuf apdu) throws BACnetParseException {
		/*
		 * given bytebuf is positioned on serviceChoice
		 * 
		 */
		byte apci = apdu.readByte(); // can be skipped
		byte serviceChoiceRaw = apdu.readByte();
		BACnetUnconfirmedService.Choice serviceChoiceType = BACnetUnconfirmedService.Choice.forId(serviceChoiceRaw);
		LOG.debug("serviceChoice: 0x" + HexUtils.convert(serviceChoiceRaw) + ": " + serviceChoiceType.name());

		srv = BACnetUnconfirmedService
				.create(serviceChoiceType.implementationClass, apdu);

	}

	public BACnetUnconfirmedService getService() {
		return srv;
	}

	public ByteBuf getBuffer() {
		return buf;
	}

	public static BACnetUnconfirmedRequestAPDU createAPDUForService(BACnetUnconfirmedService srv) {
		
		BACnetUnconfirmedRequestAPDU result = new BACnetUnconfirmedRequestAPDU(srv);

		return result;
		
	}

	@Override
	public void encode(final ByteBuf buf) {
		buf.writeByte(PDUType.UNCONFIRMED_REQUEST.getTypeId() << 4);
		srv.encode(buf);
	}
	
}
