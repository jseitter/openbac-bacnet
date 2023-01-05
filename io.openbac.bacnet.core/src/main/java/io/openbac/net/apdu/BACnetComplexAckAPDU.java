package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetComplexAckAPDU extends BACnetAPDU {

    public BACnetComplexAckAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.COMPLEX_ACK;
    }


}
