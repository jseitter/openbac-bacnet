package io.openbac.bacnet.net.npdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import io.openbac.bacnet.exceptions.BACnetParseException;

public class BACnetBVLLInboundHandler extends ChannelInboundHandlerAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetBVLLInboundHandler.class);

//	private BACnetBBMD bbmd;
	private boolean bbmdEnabled;

	public BACnetBVLLInboundHandler() {
		bbmdEnabled = false;
	}

	public BACnetBVLLInboundHandler(boolean bbmdEnabled) {
		this.bbmdEnabled = bbmdEnabled;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {

		if (bbmdEnabled) {
			LOG.info("BBMD is enabled");
//			bbmd = new BACnetBBMD(ctx);
		} else {
			LOG.info("BBMD is disabled");
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		DatagramPacket packet = (DatagramPacket) msg;
		LOG.debug("channelRead src ip: " + packet.sender());

		ByteBuf buffer = packet.content();
		BACnetBVLL bvll = null;
		try {
			bvll = new BACnetBVLL(buffer, packet.sender());
		} catch (BACnetParseException e) {
			// break if something went wrong
			ctx.close();
		}
		// call the next Handler if it is a NPDU
		if (bvll.isNPDU()) {
			ctx.fireChannelRead(bvll);
		} else {
			// forward to BBMD
//			if (bbmdEnabled)
//				bbmd.handleBVLL(bvll);
		}
	}
		


}
