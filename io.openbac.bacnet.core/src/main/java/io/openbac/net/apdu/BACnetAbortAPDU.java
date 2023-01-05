package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetAbortAPDU extends BACnetAPDU {

    public BACnetAbortAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.ABORT;
    }


}
