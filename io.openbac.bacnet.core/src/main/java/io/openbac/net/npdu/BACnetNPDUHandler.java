package io.openbac.net.npdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

public class BACnetNPDUHandler extends ChannelDuplexHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetNPDUHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		LOG.debug("channel read");
		
		BACnetBVLL bvll = (BACnetBVLL) msg;
		BACnetNPDU npdu = new BACnetNPDU(bvll);
		
		if(npdu.hasBacnetAPDU()) {
			//handle APDU
			LOG.debug("handle network message");
			ctx.fireChannelRead(npdu);
		} 
		else {
			// handle network message
			LOG.debug("handle network message");
			
		}

	}

}
