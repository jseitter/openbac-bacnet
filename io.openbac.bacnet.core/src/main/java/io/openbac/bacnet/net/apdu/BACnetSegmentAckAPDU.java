package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.net.npdu.BACnetNPDU;

public class BACnetSegmentAckAPDU extends BACnetAPDU {

    public BACnetSegmentAckAPDU(final ByteBuf buf) {

	}

	@Override
    public PDUType getPDUType() {
        return PDUType.SEGMENT_ACK;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
