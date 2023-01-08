package io.openbac.bacnet.net.apdu;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultAddressedEnvelope;
import io.openbac.api.device.BACnetLocalDevice;
import io.openbac.bacnet.net.apdu.BACnetAPDU.PDUType;
import io.openbac.bacnet.net.npdu.BACnetNPDU;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.service.confirmed.BACnetConfirmedService;
import io.openbac.service.confirmed.BACnetReadPropertyService;
import io.openbac.service.unconfirmed.BACnetIAmService;
import io.openbac.service.unconfirmed.BACnetUnconfirmedService;
import io.openbac.service.unconfirmed.BACnetWhoIsService;

/**
 * This is the final handler of the network message. Here the APDU is decoded
 * and the reaction is generated
 * 
 * @author Joerg Seitter
 *
 */
public class BACnetAPDUHandler extends ChannelDuplexHandler {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetAPDUHandler.class);
	private BACnetLocalDevice device;

	public BACnetAPDUHandler(BACnetLocalDevice device) {
		this.device = device;
	}

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
				LOG.debug("handling WhoIs Service");
				// TODO do we need to instantiate this at all ?
				BACnetWhoIsService whois = (BACnetWhoIsService) srv;
				BACnetIAmService iam = new BACnetIAmService();
				LOG.debug("sending IAM APDU");
				ctx.writeAndFlush(new DefaultAddressedEnvelope<BACnetAPDU, SocketAddress>(
						BACnetUnconfirmedRequestAPDU.createAPDUForService(iam), npdu.getBvll().getSourceAddress()));

				break;
			}
			}
		}

		if (apdu.getPDUType() == PDUType.CONFIRMED_REQUEST) {

			BACnetConfirmedRequestAPDU c_apdu = (BACnetConfirmedRequestAPDU) apdu;
			BACnetConfirmedService srv = c_apdu.getService();

			switch (srv.getServiceChoice()) {

			case 0x0C: {
				LOG.debug("handling ReadProperty Service");
				BACnetReadPropertyService rp_srv = (BACnetReadPropertyService) srv;
				BACnetAPDU result = handleReadPropertyService(rp_srv);
				ctx.writeAndFlush(new DefaultAddressedEnvelope<BACnetAPDU, SocketAddress>(result,
						npdu.getBvll().getSourceAddress()));

			}

			}
		}
	}

	private BACnetAPDU handleReadPropertyService(BACnetReadPropertyService srv) {

		BACnetObjectIdentifier id = srv.getObjectIdentifier();
		switch (id.getObjectType()) {

		case DEVICE: {
			
			break;
		}

		default: {
			// create error
		}

		}
		return null;
	}

}
