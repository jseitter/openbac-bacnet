package io.openbac.net.apdu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.net.npdu.BACnetNPDU;
import io.openbac.util.HexUtils;
  
/**
 *
 *
 * @author jseitter
 *
 */
public class BACnetConfirmedRequestAPDU extends BACnetAPDU {

        private static final Logger LOG = LoggerFactory.getLogger(BACnetConfirmedRequestAPDU.class);

 
    	

        private boolean segmentedMessage = false;
        private boolean moreFollows = false;
        private boolean segmentedResponseAccepted = false;
        private int maxSegmentsAccepted = 0;
        private int maxAPDULengthAccepted = 480;
        private byte invokeID = 0;
        private byte sequenceNumber = 0;
        private byte proposedWindowSize = 0;
        
        @Override
        public PDUType getPDUType() {
                return PDUType.CONFIRMED_REQUEST;
        }

        public BACnetConfirmedRequestAPDU(BACnetNPDU npdu) throws BACnetParseException {

        		super(npdu);
        		ByteBuf rawAPDU = npdu.getPayload();
                byte apci = rawAPDU.readByte();
                byte segmentedMessageByte = (byte) ((apci & 0b00001000) >> 3);
                byte moreFollowsByte = (byte) ((apci & 0b00000100) >> 2);
                byte segmentedResponseAcceptedByte = (byte) ((apci & 0b00000010) >> 1);

                if (segmentedMessageByte == (byte)0x01) {
                        segmentedMessage = true;
                }
                LOG.debug("segmented message: " + segmentedMessage);

                if (moreFollowsByte == (byte)0x01) {
                        moreFollows = true;
                }
                LOG.debug("more follows: " + moreFollows);

                if (segmentedResponseAcceptedByte == (byte)0x01) {
                        segmentedResponseAccepted = true;
                }
                LOG.debug("segmented response accepted: " + segmentedResponseAccepted);
                
                byte segmentConfiguration = rawAPDU.readByte();
                
                Byte maxSegmentsAcceptedByte = (byte) ((segmentConfiguration & 0b01110000) >> 4);
                if (maxSegmentsAcceptedByte == 0b00000000) {
                        maxSegmentsAccepted = 0;
                } else if (maxSegmentsAcceptedByte == 0b00000111) {
                        maxSegmentsAccepted = Integer.MAX_VALUE;
                } else if (maxSegmentsAcceptedByte > 0 && maxSegmentsAcceptedByte < 7) {
                        Double d = Math.pow(2.0, maxSegmentsAcceptedByte.doubleValue());
                        maxSegmentsAccepted = d.intValue();
                } else {
                        // TODO invalid maxSegmentAccepted
                        // throw exception?
                        maxSegmentsAccepted = 0;
                }
                
                byte maxAPDULengthAcceptedByte = (byte) (segmentConfiguration & 0b00001111);
                if (maxAPDULengthAcceptedByte == 0b00000000) {
                        maxAPDULengthAccepted = 50;
                } else if (maxAPDULengthAcceptedByte == 0b00000001) {
                        maxAPDULengthAccepted = 128;
                } else if (maxAPDULengthAcceptedByte == 0b00000010) {
                        maxAPDULengthAccepted = 206;
                } else if (maxAPDULengthAcceptedByte == 0b00000011) {
                        maxAPDULengthAccepted = 480;
                } else if (maxAPDULengthAcceptedByte == 0b00000100) {
                        maxAPDULengthAccepted = 1024;
                } else if (maxAPDULengthAcceptedByte == 0b00000101) {
                        maxAPDULengthAccepted = 1476;
                } else {
                        // TODO invalid maxAPDULengthAccepted
                        // throw exception?
                        maxAPDULengthAccepted = 0;
                }
                
                LOG.debug("max segments accepted: " + maxSegmentsAccepted);
                LOG.debug("max apdu length accepted: " + maxAPDULengthAccepted);
                invokeID = rawAPDU.readByte();
                LOG.debug("invoke id: 0x" + HexUtils.convert(invokeID));
                
                if (segmentedMessage) {
                        
                        sequenceNumber = rawAPDU.readByte();
                        LOG.debug("sequence number: 0x" + HexUtils.convert(sequenceNumber));
                        proposedWindowSize = rawAPDU.readByte();
                        LOG.debug("proposed window size: 0x" + HexUtils.convert(proposedWindowSize));
                        
                }
                
                byte serviceChoiceRaw = rawAPDU.readByte();
//                serviceChoiceType = ConfirmedServiceChoice.getServiceChoiceType(serviceChoiceRaw);
//                LOG.debug("serviceChoice: 0x" + HexUtils.convert(serviceChoiceRaw) + ": " + serviceChoiceType.name());

        		// copy the unprocessed part of the datagram
        		payload=rawAPDU.slice();

        }
        
        
 
        public boolean isSegmentedMessage() {
                return segmentedMessage;
        }

        public void setSegmentedMessage(boolean segmentedMessage) {
                this.segmentedMessage = segmentedMessage;
        }

        public boolean isMoreFollows() {
                return moreFollows;
        }

        public void setMoreFollows(boolean moreFollows) {
                this.moreFollows = moreFollows;
        }

        public boolean isSegmentedResponseAccepted() {
                return segmentedResponseAccepted;
        }

        public void setSegmentedResponseAccepted(boolean segmentedResponseAccepted) {
                this.segmentedResponseAccepted = segmentedResponseAccepted;
        }

        public int getMaxSegmentsAccepted() {
                return maxSegmentsAccepted;
        }

        public void setMaxSegmentsAccepted(int maxSegmentsAccepted) {
                this.maxSegmentsAccepted = maxSegmentsAccepted;
        }

        public int getMaxAPDULengthAccepted() {
                return maxAPDULengthAccepted;
        }

        public void setMaxAPDULengthAccepted(int maxAPDULengthAccepted) {
                this.maxAPDULengthAccepted = maxAPDULengthAccepted;
        }

        public byte getInvokeID() {
                return invokeID;
        }

        public void setInvokeID(byte invokeID) {
                this.invokeID = invokeID;
        }

        public byte getSequenceNumber() {
                return sequenceNumber;
        }

        public void setSequenceNumber(byte sequenceNumber) {
                this.sequenceNumber = sequenceNumber;
        }

        public byte getProposedWindowSize() {
                return proposedWindowSize;
        }

        public void setProposedWindowSize(byte proposedWindowSize) {
                this.proposedWindowSize = proposedWindowSize;
        }

}
