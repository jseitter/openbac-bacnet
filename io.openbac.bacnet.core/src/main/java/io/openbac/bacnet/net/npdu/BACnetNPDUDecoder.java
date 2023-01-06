package io.openbac.bacnet.net.npdu;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class BACnetNPDUDecoder extends MessageToMessageDecoder<BACnetBVLL> {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetNPDUDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext ctx, BACnetBVLL msg, List<Object> out) throws Exception {
		LOG.debug("NPDU Decode");
		
		BACnetBVLL bvll = (BACnetBVLL) msg;
		BACnetNPDU npdu = new BACnetNPDU(bvll);
		
		if(npdu.hasBacnetAPDU()) {
			//handle APDU
			LOG.debug("handle APDU");
			//ctx.fireChannelRead(npdu);
			out.add(npdu);
		} 
		else {
			// handle network message
			LOG.debug("TODO: handle network message");
			
		}		
	}

}
