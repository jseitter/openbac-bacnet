package io.openbac.net.apdu;

import io.openbac.net.npdu.BACnetNPDU;

public class BACnetErrorAPDU extends BACnetAPDU {

    public BACnetErrorAPDU(BACnetNPDU npdu) {
		super(npdu);
		// TODO Auto-generated constructor stub
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.ERROR;
    }

}
