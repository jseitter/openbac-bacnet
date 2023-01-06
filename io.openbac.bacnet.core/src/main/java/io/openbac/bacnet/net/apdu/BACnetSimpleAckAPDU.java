package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetSimpleAckAPDU extends BACnetAPDU {

    public BACnetSimpleAckAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.SIMPLE_ACK;
    }

}
