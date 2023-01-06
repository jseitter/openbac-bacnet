package io.openbac.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.exceptions.BACnetParseException.ParseExceptionType;
import io.openbac.net.npdu.BACnetNPDU;
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
    	protected ByteBuf payload;
    	
    	/**
    	 * The NPDU for this APDU
    	 */
    	private BACnetNPDU npdu;

    	/**
    	 * constructors
    	 */
		public BACnetAPDU() {
			this.npdu=null;
		}
		
        public BACnetAPDU(BACnetNPDU npdu) {
        	this.npdu=npdu;
        }
 
        
        public abstract PDUType getPDUType();

        /**
         * factory method to create APDU from NPDU
         *
         * @param BACnetNPDU
         * @return
         */
        public static BACnetAPDU createAPDU(BACnetNPDU npdu) throws BACnetParseException {
        		
                BACnetAPDU apdu = null;

                ByteBuf rawAPDU = npdu.getPayload();
                
                // get first octet (apci)
                byte apci = rawAPDU.readByte();
                byte pduTypeId = (byte) (apci >> 4);
                PDUType pduType = PDUType.getPDUType(pduTypeId);
                LOG.debug("apdu type: 0x" + HexUtils.convert(pduTypeId) + " = " + pduType.name());
                rawAPDU.resetReaderIndex();


                switch (pduType) {

                        case UNCONFIRMED_REQUEST: {
                                apdu = new BACnetUnconfirmedRequestAPDU(npdu);
                                break;
                        }

                        case CONFIRMED_REQUEST: {
                                apdu = new BACnetConfirmedRequestAPDU(npdu);
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
			return payload;
		}

        public BACnetNPDU getNpdu() {
			return npdu;
		}

}
