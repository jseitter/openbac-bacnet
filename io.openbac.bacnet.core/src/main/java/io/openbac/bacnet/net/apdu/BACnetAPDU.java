package io.openbac.bacnet.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.exceptions.BACnetParseException.ParseExceptionType;
import io.openbac.util.HexUtils;

/**
 * APDU class that handles all generic APDU fields
 *
 * @author jseitter
 *
 */
public abstract class BACnetAPDU {

        private static final Logger LOG = LoggerFactory.getLogger(BACnetAPDU.class);

        enum PDUType {
            CONFIRMED_REQUEST(  (byte) 0x00),
            UNCONFIRMED_REQUEST((byte) 0x01),
            SIMPLE_ACK(         (byte) 0x02),
            COMPLEX_ACK(        (byte) 0x03),
            SEGMENT_ACK(        (byte) 0x04),
            ERROR(              (byte) 0x05),
            REJECT(             (byte) 0x06),
            ABORT(              (byte) 0x07);

            private final byte typeId;

            PDUType(byte typeId) {
                this.typeId = typeId;
            }

            public byte getTypeId() {
                return typeId;
            }

            public static PDUType getPDUType(byte pduTypeId) {

                for (PDUType p : values()) {
                    if (p.typeId == pduTypeId) {
                        return p;
                    }
                }
                throw new IllegalArgumentException("pduTypeId has invalid value");

            }

        }
        
       	/**
    	 * Buffer containing higher level protocol parts
    	 */
    	protected ByteBuf buffer;
    	 
        
        public abstract PDUType getPDUType();
        
        /**
         * factory method to create APDU from NPDU
         *
         * @param ByteBuf containing the payload of an npdu
         * @return
         */
        public static BACnetAPDU createAPDU(final ByteBuf buf) throws BACnetParseException {
        		
                BACnetAPDU apdu = null; 
                // get first octet (apci)
                byte apci = buf.readByte();
                //pduType is encoded in upper 4 bits
                byte pduTypeId = (byte) (apci >> 4);
                PDUType pduType = PDUType.getPDUType(pduTypeId);
                LOG.debug("apdu type: 0x" + HexUtils.convert(pduTypeId) + " = " + pduType.name());
                
                switch (pduType) {

                        case UNCONFIRMED_REQUEST: {
                                apdu = new BACnetUnconfirmedRequestAPDU(buf);
                                break;
                        }

                        case CONFIRMED_REQUEST: {
                                apdu = new BACnetConfirmedRequestAPDU(buf);
                                break;
                        }

                        case SIMPLE_ACK:
                                break;

                        case SEGMENT_ACK:
                                break;

                        case COMPLEX_ACK:
                                break;

                        case ABORT:
                                break;

                        case ERROR:
                                break;

                        case REJECT:
                                break;

                        default:
                                throw new BACnetParseException("invalid pdu type "+pduType.typeId, ParseExceptionType.INVALID_PDU_TYPE);

                }

                return apdu;

        }

		public ByteBuf getPayload() {
			return buffer;
		}

		public abstract void encode(ByteBuf buf);

}
