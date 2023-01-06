package io.openbac.bacnet.net.apdu;

import io.netty.buffer.ByteBuf;

public class BACnetSimpleAckAPDU extends BACnetAPDU {

    public BACnetSimpleAckAPDU(final ByteBuf buf) {
    	this.buffer=buf;
	}

	@Override
    public PDUType getPDUType() {
        return PDUType.SIMPLE_ACK;
    }

	@Override
	public void encode(ByteBuf buf) {
		// TODO Auto-generated method stub
		
	}

}
