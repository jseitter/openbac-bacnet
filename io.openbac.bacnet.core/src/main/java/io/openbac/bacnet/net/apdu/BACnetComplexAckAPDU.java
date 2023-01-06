package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;

public class BACnetComplexAckAPDU extends BACnetAPDU {

    public BACnetComplexAckAPDU(final ByteBuf buf) {
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.COMPLEX_ACK;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}


}
