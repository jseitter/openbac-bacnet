package io.openbac.bacnet.net.npdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetNetworkLayerMessageException;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.net.apdu.BACnetAPDU;
import io.openbac.util.HexUtils;

/**
 * The container for APDUs on IP Networks
 * 
 * @author jseitter
 *
 */
public final class BACnetNPDU {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetNPDU.class);

	/**
	 * Buffer containing higher level protocol parts
	 */
	private ByteBuf payload;

	/**
	 * The BVLL object for this packet
	 */
	private BACnetBVLL bvll;

	public BACnetBVLL getBvll() {
		return bvll;
	}

	private BACnetAPDU apdu;

	public BACnetNPDU(BACnetAPDU apdu) {
		this.priority = 0;
		this.expectingReply = false;
		this.apdu = apdu;
	}

	/**
	 * get Buffer with higher level PDU
	 * 
	 * @return ByteBuf containing higher level PDU
	 */
	public ByteBuf getPayload() {
		return payload;
	}

	/**
	 * Version byte is always 0x01
	 */
	private byte version = (byte) 0x01;
	/**
	 * npciControlOctet
	 * 
	 * <pre>
	 * Bit     Meaning
	 * 7        1=NetworkLayerMessage, 0=APDU
	 * 6		reserved
	 * 5        1=DNET DLEN DADR present, 0=DNET DLEN DADR absent
	 * 4        reserved
	 * 3		1=SNET SLEN SADR present, 0=SNET SLEN SADR absent
	 * 2        expecting replay
	 * 1,0      priority (11 =life safety, 10=critical equipment, 01=urgent, 00=normal)
	 * 
	 * </pre>
	 */
	private byte npciControlOctet = (byte) 0x00;
	private int dnet;
	private byte dlen;
	// 4 byte ip, 2 byte port
	private final byte[] dadr = new byte[6];
	private int snet;
	private byte slen;
	// 4 byte ip, 2 byte port
	private final byte[] sadr = new byte[6];
	private byte hopcount;
	private final int priority;
	private final boolean expectingReply;

	/**
	 * Parses the NPDU
	 * 
	 * @param rawNPDU
	 * @throws BACnetParseException
	 * @throws BACnetNetworkLayerMessageException
	 */
	protected BACnetNPDU(BACnetBVLL bvll) throws BACnetParseException, BACnetNetworkLayerMessageException {

		this.bvll = bvll; // store the bvll since it contains the sender address
		// get the Version field
		ByteBuf rawNPDU = bvll.getPayload();
		version = rawNPDU.readByte();
		LOG.debug("version 0x" + HexUtils.convert(version));
		// eject here if version field is not correct
		if (version != (byte) 0x01) {
			throw new BACnetParseException("bacnet version number not 0x01: " + HexUtils.convert(version),
					BACnetParseException.ParseExceptionType.INVALID_BACNET_VERSION);
		}

		npciControlOctet = rawNPDU.readByte();
		LOG.debug("npci: 0x" + HexUtils.convert(npciControlOctet));

		if (getDestinationSpecifier() == 1) {
			LOG.debug("destination specifier");
			// get DNET,DLEN and Hop Count
			dnet = rawNPDU.readUnsignedShort();
			dlen = rawNPDU.readByte();
			// dlen = 0 -> DADR is absent (broadcast, chapter 6.2.2)
			if (dlen == 0) {
				LOG.debug("destination specifier but dlen = 0 -> no dadr present -> broadcast message");
			} else {
				// TODO: lookup in standard, when 0x20 then no DADR, not sure
				// where this is defined but its true
				if (!(npciControlOctet == (byte) 0x20)) {
					rawNPDU.readBytes(dadr, 0, dlen);
				}
			}
			LOG.debug("dnet: 0x" + Integer.toHexString(dnet) + " dlen: " + dlen + " dadr: 0x" + HexUtils.convert(dadr));
		}

		if (getSourceSpecifier() == 1) {
			LOG.debug("source specifier");
			// get SNET, SLEN and SADR
			snet = rawNPDU.readUnsignedShort();
			slen = rawNPDU.readByte();
			if (slen == 0) {
				// if slen is zero this is an invalid packet
				throw new BACnetParseException("slen is zero", BACnetParseException.ParseExceptionType.SLEN_IS_ZERO);
			}

			rawNPDU.readBytes(sadr, 0, slen);
			LOG.debug("snet: 0x" + Integer.toHexString(snet) + " slen: " + slen + " sadr: 0x" + HexUtils.convert(sadr));
		}

		// fetch hop count if dnet is present
		if (getDestinationSpecifier() == 1) {

			hopcount = rawNPDU.readByte();
			LOG.debug("hop count: " + HexUtils.convert(hopcount));
			// eject if the hopcount is zero (chapter 6.2.3)
			if (hopcount == 0) {
				// if hopcount is zero parsing should be aborted
				throw new BACnetParseException("hopcount is zero",
						BACnetParseException.ParseExceptionType.HOPCOUNT_IS_ZERO);
			}

		}

		priority = getPriority();
		LOG.debug("priority: " + priority);

		int expectingReplyInt = getExpectingReply();
		expectingReply = (expectingReplyInt == 1);
		LOG.debug("expecting reply: " + expectingReply);

		// copy the unprocessed part of the datagram
		payload = rawNPDU;// .slice();

	}

	/**
	 * semantic helper for message type
	 */
	public boolean hasBacnetAPDU() {
		return (this.getMessageType() == 0);
	}

	/**
	 * semantic helper for message type
	 */
	public boolean hasBacnetNetworkLayerMessage() {
		return (this.getMessageType() == 1);
	}

	/**
	 * 
	 * @return 1 if it is a networkMessage, 0 if it is an APDU
	 */
	public int getMessageType() {
		return (npciControlOctet & 0x0080) >> 7;
	}

	/**
	 * 
	 * @return 1 if destination information is set, 0 if not
	 */
	public int getDestinationSpecifier() {
		return (npciControlOctet & 0x0020) >> 5;
	}

	/**
	 * @return 1 if source information is set, 0 if not
	 */
	public int getSourceSpecifier() {
		return (npciControlOctet & 0x0008) >> 3;
	}

	/**
	 * if sender expects a reply
	 * 
	 * @return 1 if sender expects reply, 0 if not
	 */
	public int getExpectingReply() {
		return (npciControlOctet & 0x0004) >> 2;
	}

	/**
	 * the message priority
	 * 
	 * @return message priority as int value
	 */
	public int getPriority() {
		return (npciControlOctet & 0x0003);
	}

	public void encode(final ByteBuf buf) {
		// bacnet protocol version
		buf.writeByte(version);

		if (apdu.getPDUType() == BACnetAPDU.PDUType.UNCONFIRMED_REQUEST) {
			// write NPCI
			buf.writeByte(0x20);
			// dnet
			buf.writeShort(0xffff);
			// dlen
			buf.writeByte(0x00);
			// hop count
			buf.writeByte(0xff);
		} else {
			// NPCI=0
			buf.writeByte(0x00);
		}
		// APDU
		apdu.encode(buf);

	}

}
