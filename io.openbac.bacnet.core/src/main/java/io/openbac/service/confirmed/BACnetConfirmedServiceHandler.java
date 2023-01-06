package io.openbac.service.confirmed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.openbac.bacnet.net.apdu.BACnetAPDUHandler;
import io.openbac.bacnet.net.apdu.BACnetConfirmedRequestAPDU;

public class BACnetConfirmedServiceHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetConfirmedServiceHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		if (!(msg instanceof BACnetConfirmedRequestAPDU)) {
			// if this is not a confirmed APDU pass it on
			ctx.fireChannelRead(msg);
		}
		
		LOG.debug("processing confirmed Request APDU");
		BACnetConfirmedRequestAPDU reqAPDU = (BACnetConfirmedRequestAPDU) msg;

	}

}
