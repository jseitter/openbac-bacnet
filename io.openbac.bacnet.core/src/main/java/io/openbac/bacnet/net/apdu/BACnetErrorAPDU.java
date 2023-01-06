package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;

public class BACnetErrorAPDU extends BACnetAPDU {

    public BACnetErrorAPDU(final ByteBuf buf) {

	}

	@Override
    public PDUType getPDUType() {
        return PDUType.ERROR;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
