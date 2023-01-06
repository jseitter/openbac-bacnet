package io.openbac.bacnet.net.npdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.openbac.bacnet.net.apdu.BACnetAPDU;

public class BACnetNPDUHandler extends ChannelDuplexHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetNPDUHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		LOG.debug("channel read");
		
		BACnetBVLL bvll = (BACnetBVLL) msg;
		BACnetNPDU npdu = new BACnetNPDU(bvll);
		
		if(npdu.hasBacnetAPDU()) {
			//handle APDU
			LOG.debug("handle APDU");
			ctx.fireChannelRead(npdu);
			
		} 
		else {
			// handle network message
			LOG.debug("TODO: handle network message");
			
		}

	}

	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

		LOG.debug("got an APDU to send, creating NPDU");

		BACnetNPDU npdu = new BACnetNPDU((BACnetAPDU)msg);
		
		ctx.writeAndFlush(npdu);
//		}
	}

}
