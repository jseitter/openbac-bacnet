package io.openbac.bacnet.net.apdu;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultAddressedEnvelope;
import io.openbac.bacnet.net.apdu.BACnetAPDU.PDUType;
import io.openbac.bacnet.net.npdu.BACnetNPDU;
import io.openbac.service.unconfirmed.BACnetIAmService;
import io.openbac.service.unconfirmed.BACnetUnconfirmedService;
import io.openbac.service.unconfirmed.BACnetWhoIsService;

public class BACnetAPDUHandler extends ChannelDuplexHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetAPDUHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		BACnetNPDU npdu = (BACnetNPDU) msg;

		BACnetAPDU apdu = BACnetAPDU.createAPDU(npdu.getPayload());
		LOG.debug("APDU received : " + apdu.getPDUType().toString());
		if (apdu.getPDUType() == PDUType.UNCONFIRMED_REQUEST) {
			BACnetUnconfirmedRequestAPDU uc_apdu = (BACnetUnconfirmedRequestAPDU) apdu;
			BACnetUnconfirmedService srv = uc_apdu.getService();

			switch (srv.getServiceChoice()) {
			// TODO: solve this with the Choice enum
			// WHO_IS
			case 0x08: {
				LOG.debug("handling WHO IS");
				BACnetWhoIsService whois = (BACnetWhoIsService) srv;
				BACnetIAmService iam = new BACnetIAmService();
				LOG.debug("sending IAM APDU");
				ctx.writeAndFlush(
				new DefaultAddressedEnvelope<BACnetAPDU, SocketAddress>(
						BACnetUnconfirmedRequestAPDU.createAPDUForService(iam),
						npdu.getBvll().getSourceAddress())
				);

				break;
			}
			}
		}



		// find neccesary listeners that shall be called

		// find out what is the return we shall send

		// ctx.writeAndFlush();
//		if(msg instanceof IAmService) {
//		LOG.debug("Send Iam Service");
		// UnconfirmedRequestAPDU apdu = new Un

	}

}
