package io.openbac.bacnet.net.npdu;

import java.net.SocketAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultAddressedEnvelope;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.openbac.bacnet.net.apdu.BACnetAPDU;

public class BACnetNPDUEncoder extends MessageToMessageEncoder<DefaultAddressedEnvelope<BACnetAPDU, SocketAddress>> {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetNPDUEncoder.class);
	
	@Override
	protected void encode(ChannelHandlerContext ctx, DefaultAddressedEnvelope<BACnetAPDU, SocketAddress> msg, List<Object> out) throws Exception {
		LOG.debug("got an APDU to send, creating NPDU");

		BACnetNPDU npdu = new BACnetNPDU(msg.content());

		DefaultAddressedEnvelope<BACnetNPDU, SocketAddress> outMsg = new DefaultAddressedEnvelope<>(npdu, msg.recipient());
		out.add(outMsg);
	}

}
