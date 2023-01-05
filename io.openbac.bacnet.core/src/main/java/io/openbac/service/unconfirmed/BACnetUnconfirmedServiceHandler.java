//package io.openbac.service.unconfirmed;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.openbac.api.device.BACnetLocalDevice;
//import io.openbac.bacnet.exceptions.BACnetRejectException;
//import io.openbac.bacnet.type.enumerated.BACnetRejectReason;
//import io.openbac.net.apdu.BACnetUnconfirmedRequestAPDU;
//import io.openbac.service.ServiceChoiceTypes.UnconfirmedServiceChoiceType;
//
//public class BACnetUnconfirmedServiceHandler extends ChannelInboundHandlerAdapter {
//
//	private static final Logger LOG = LoggerFactory.getLogger(BACnetUnconfirmedServiceHandler.class);
//	
//	private BACnetLocalDevice localDevice;
//	
//	//disable the no arg constructor
//	private BACnetUnconfirmedServiceHandler() {
//	}
//
//	public BACnetUnconfirmedServiceHandler(BACnetLocalDevice localDevice) {
//		this.localDevice=localDevice;
//	}
//
//	@Override
//	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//		if (!(msg instanceof BACnetUnconfirmedRequestAPDU)) {
//			// if this is not a confirmed APDU pass it on
//			ctx.fireChannelRead(msg);
//		}
//
//		LOG.debug("processing unconfirmed Request APDU");
//		BACnetUnconfirmedRequestAPDU reqAPDU = (BACnetUnconfirmedRequestAPDU) msg;
//
//		UnconfirmedServiceChoiceType c = (UnconfirmedServiceChoiceType) reqAPDU.getServiceChoiceType();
//	
//		// creation of the ServiceObjects
//		switch(c) {
//		
//		case WHO_IS:
//				WhoIsService whois = new WhoIsService(reqAPDU);
//				localDevice.handleWhoIsService(whois, ctx);
//			break;
//		case I_AM:
//				IAmService iam = new IAmService(reqAPDU);
//				localDevice.handleIAmService(iam,ctx);
//			break;
//				
//		default:
//			throw new BACnetRejectException(BACnetRejectReason.unrecognizedService);
//	
//		}
//	}
//
//}
