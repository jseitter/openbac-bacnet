package io.openbac.bacnet.net.npdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetNetworkLayerMessageException;
import io.openbac.util.HexUtils;

/**
 * Represents Network Layer Messages
 * @author jseitter
 *
 */
public class BACnetNetworkLayerMessage {

	private static final Logger LOG = LoggerFactory.getLogger(BACnetBVLL.class);
	/**
	   <pre>
	  	0x00 	Who-Is-Router-To-Network
		0x01 	I-Am-Router-To-Network
		0x02 	I-Could-Be-Router-To-Network
		0x03 	Reject-Message-To-Network
		0x04 	Router-Busy-To-Network
		0x05 	Router-Available-To-Network
		0x06 	Initialize-Routing-Table
		0x07 	Initialize-Routing-Table-ACK
		0x08 	Establish-Connection-To-Network
		0x09 	Disconnect-Connection-To-Network
		0x0A to 0x7F 	Reserved for use by ASHRAE
		0x80 to 0xFF 	Available for Vendor Proprietary Messages
	   </pre>
	 */
	
	public static enum MessageType {
            
            WHO_IS_ROUTER_TO_NETWORK((byte)0x00),
            I_AM_ROUTER_TO_NETWORK((byte)0x01),
            I_COULD_BE_ROUTER_TO_NETWORK((byte)0x02),
            REJECT_MESSAGE_TO_NETWORK((byte)0x03),
            ROUTER_BUSY_TO_NETWORK((byte)0x04),
            ROUTER_AVAILABLE_TO_NETWORK((byte)0x05),
            INIT_ROUTING_TABLE((byte)0x06),
            INIT_ROUTING_TABLE_ACK((byte)0x07),
            ESTABLISH_CONNECTION_TO_NETWORK((byte)0x08),
            DISCONNECT_CONNECTION_TO_NETWORK((byte)0x09);

            private final byte messageType;

            MessageType(byte messageType) {
                this.messageType = messageType;
            }

            public byte getMessageType() {
                return this.messageType;
            }

            public static MessageType createMessageType(byte messageType) {

                for (MessageType mt : values()) {
                    if (mt.messageType == messageType) {
                        return mt;
                    }
                }
                throw new IllegalArgumentException("messageType has invalid value");

            }
                
	}
	
        private final MessageType messageType;
	
	public BACnetNetworkLayerMessage(ByteBuf rawDatagram) throws BACnetNetworkLayerMessageException {
            // rawDatagrams position is already at the position of the nsdu
            // we expect an nsdu type of network layer message here
            
            // first octet is message type
            byte rawMessageType = rawDatagram.readByte();
            LOG.debug("messageType 0x" + HexUtils.convert(rawMessageType));
            try {
                messageType = MessageType.createMessageType(rawMessageType);
            } catch (final IllegalArgumentException ex) {
                throw new BACnetNetworkLayerMessageException("invalid message type: " + HexUtils.convert(rawMessageType), BACnetNetworkLayerMessageException.NetworkLayerExceptionType.INVALID_MESSAGE_TYPE);
            }
            
            // process different nsdu types
            switch (messageType) {
                case WHO_IS_ROUTER_TO_NETWORK:
                    break;
                case I_AM_ROUTER_TO_NETWORK:
                    break;
                case I_COULD_BE_ROUTER_TO_NETWORK:
                    break;
                case REJECT_MESSAGE_TO_NETWORK:
                    break;
                case ROUTER_BUSY_TO_NETWORK:
                    break;
                case ROUTER_AVAILABLE_TO_NETWORK:
                    break;
                case INIT_ROUTING_TABLE:
                    break;
                case INIT_ROUTING_TABLE_ACK:
                    break;
                case ESTABLISH_CONNECTION_TO_NETWORK:
                    break;
                case DISCONNECT_CONNECTION_TO_NETWORK:
                    break;
                default:
                    LOG.error("invalid message type in switch, this shouldn't happen", messageType);
                    throw new BACnetNetworkLayerMessageException("invalid message type", BACnetNetworkLayerMessageException.NetworkLayerExceptionType.INVALID_MESSAGE_TYPE);
            }
            
	}
	
	public BACnetNetworkLayerMessage() {
	
            messageType = null;
		
	}
        
}
