package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetRejectAPDU extends BACnetAPDU {

    public BACnetRejectAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.REJECT;
    }

}
