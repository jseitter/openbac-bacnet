package io.openbac.bacnet.net.npdu;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.openbac.bacnet.net.npdu.BACnetBVLL.FunctionType;

public class BACnetBVLLOutboundHandler
		extends MessageToMessageEncoder<DefaultAddressedEnvelope<BACnetNPDU, SocketAddress>> {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetBVLLOutboundHandler.class);

	@Override
	protected void encode(ChannelHandlerContext ctx, DefaultAddressedEnvelope<BACnetNPDU, SocketAddress> msg,
			List<Object> out) throws Exception {

		LOG.debug("got an NPDU to send, creating BVLL");
		LOG.debug(msg.toString());
		BACnetNPDU npdu = msg.content();
		BACnetBVLL bvll = null;
		if (npdu.getDestinationSpecifier() == 1) { // TODO this implementation is not correct
			bvll = new BACnetBVLL(FunctionType.ORIGINAL_BROADCAST_NPDU, npdu);
		} else {
			bvll = new BACnetBVLL(FunctionType.ORIGINAL_UNICAST_NPDU, npdu);
		}
		ByteBuf outBuf = Unpooled.buffer();
		bvll.encode(outBuf);
		DatagramPacket pkt = new DatagramPacket(outBuf, (InetSocketAddress) msg.recipient());
		out.add(pkt);

		LOG.debug("done");

	}

}
