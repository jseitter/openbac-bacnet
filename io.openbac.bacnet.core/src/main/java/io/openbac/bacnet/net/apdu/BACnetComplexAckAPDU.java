package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;
import io.openbac.service.confirmed.BACnetConfirmedService.BACnetResponse;
import io.openbac.service.confirmed.BACnetReadPropertyService;
import io.openbac.service.confirmed.BACnetReadPropertyService.ReadPropertyACK;

public class BACnetComplexAckAPDU extends BACnetAPDU {

	/*
	 * pdu-type [0] Unsigned (0..15), -- 3 for this PDU type segmented-message [1]
	 * BOOLEAN, more-follows [2] BOOLEAN, reserved [3] Unsigned (0..3), -- must be
	 * set to zero invokeID [4] Unsigned (0..255), sequence-number [5] Unsigned
	 * (0..255) OPTIONAL,--only if segment proposed-window-size [6] Unsigned
	 * (1..127) OPTIONAL, -- only if segment service-ACK-choice [7]
	 * BACnetConfirmedServiceChoice, service-ACK [8] BACnet-Confirmed-Service-ACK
	 */
	byte pduType; // shift left by 4 in first byte
	boolean segementedMessage;
	boolean moreFollows;
	int invokeId;
	int sequenceNumber; // optional
	byte proposedWindowSize; // optional
	byte serviceAckChoice;
	BACnetResponse response;

	private BACnetComplexAckAPDU() {
		this.pduType = 3;
	}

	public BACnetComplexAckAPDU(final ByteBuf buf) {
	}

	@Override
	public PDUType getPDUType() {
		return PDUType.COMPLEX_ACK;
	}

	@Override
	public void encode(ByteBuf buf) {
		int apci = pduType << 4; // pdutype
		if (segementedMessage)
			apci |= 0x08;
		if (moreFollows)
			apci |= 0x04;
		buf.writeByte(apci);
		buf.writeByte(invokeId);
//		buf.writeByte(serviceAckChoice); the choice is part of the response
		response.encode(buf); // encode the response

	}

	public static BACnetComplexAckAPDU createFor(ReadPropertyACK result, int invokeId) {
		BACnetComplexAckAPDU ack = new BACnetComplexAckAPDU();
		ack.response = result;
		ack.segementedMessage = false;
		ack.moreFollows = false;
		ack.invokeId = invokeId;
		ack.serviceAckChoice = BACnetReadPropertyService.serviceChoice;

		return ack;
	}

}
