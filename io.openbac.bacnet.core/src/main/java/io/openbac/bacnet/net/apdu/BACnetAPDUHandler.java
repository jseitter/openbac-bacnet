package io.openbac.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.openbac.net.npdu.BACnetNPDU;

public class BACnetAPDUHandler extends ChannelDuplexHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetAPDUHandler.class);
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		BACnetNPDU npdu = (BACnetNPDU) msg;
		
		BACnetAPDU apdu = BACnetAPDU.createAPDU(npdu);
		LOG.debug("APDU received : "+apdu.getPDUType().toString());
		
		ctx.fireChannelRead(apdu);
		
	}
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		LOG.debug("write happened");
//		if(msg instanceof IAmService) {
//			LOG.debug("Send Iam Service");
		//	UnconfirmedRequestAPDU apdu = new Un
//		}
	}
}
