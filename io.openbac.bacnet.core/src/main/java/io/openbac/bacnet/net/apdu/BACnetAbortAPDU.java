package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;

public class BACnetAbortAPDU extends BACnetAPDU {

    public BACnetAbortAPDU(final ByteBuf buf) {

	}

	@Override
    public PDUType getPDUType() {
        return PDUType.ABORT;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}


}
