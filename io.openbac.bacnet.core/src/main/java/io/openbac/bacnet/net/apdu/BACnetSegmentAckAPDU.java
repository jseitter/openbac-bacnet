package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetSegmentAckAPDU extends BACnetAPDU {

    public BACnetSegmentAckAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.SEGMENT_ACK;
    }

}
