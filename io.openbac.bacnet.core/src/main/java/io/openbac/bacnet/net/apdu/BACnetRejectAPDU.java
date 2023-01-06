package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;

public class BACnetRejectAPDU extends BACnetAPDU {

    public BACnetRejectAPDU(final ByteBuf buf) {
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.REJECT;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
