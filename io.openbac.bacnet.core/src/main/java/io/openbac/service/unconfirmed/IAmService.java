//package io.openbac.service.unconfirmed;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.buffer.ByteBuf;
//import io.openbac.bacnet.exceptions.BACnetParseException;
//import io.openbac.bacnet.type.BACnetTagParser;
//import io.openbac.bacnet.type.enumerated.BACnetSegmentation;
//import io.openbac.bacnet.type.primitive.BACnetEnumerated;
//import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
//import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;
//import io.openbac.net.apdu.BACnetAPDU;
//import io.openbac.object.BACnetObjectType;
//import io.openbac.service.ServiceChoiceTypes.UnconfirmedServiceChoiceType;
//
//public class IAmService extends BACnetUnconfirmedService {
//
//		private static BACnetTagParser parser = BACnetTagParser.getInstance();
//        private static final Logger LOG = LoggerFactory.getLogger(IAmService.class);
//
//        private BACnetObjectIdentifier objectIdentifier;
//        private BACnetUnsignedInteger maxAPDULengthAccepted;
//        private BACnetSegmentation segmentationSupported;
//        private BACnetUnsignedInteger vendorID;
//
//        @Override
//        public UnconfirmedServiceChoiceType getServiceChoiceType() {
//                return UnconfirmedServiceChoiceType.I_AM;
//        }
//
//        public IAmService() {
//                this.objectIdentifier = new BACnetObjectIdentifier(BACnetObjectType.ObjectType.DEVICE, 0);
//                this.maxAPDULengthAccepted = new BACnetUnsignedInteger(480);
//                this.segmentationSupported = new BACnetSegmentation(BACnetSegmentation.noSegmentation);
//                this.vendorID = new BACnetUnsignedInteger(195);
//        }
//
//        public IAmService(BACnetObjectIdentifier objectIdentifier, BACnetUnsignedInteger maxAPDULengthAccepted, BACnetSegmentation segmentationSupported, BACnetUnsignedInteger vendorID) {
//                this.objectIdentifier = objectIdentifier;
//                this.maxAPDULengthAccepted = maxAPDULengthAccepted;
//                this.segmentationSupported = segmentationSupported;
//                this.vendorID = vendorID;
//        }
//
//        /**
//         * decode service from APDU
//         * @param apdu
//         * @throws BACnetParseException
//         */
//        public IAmService(BACnetAPDU apdu) throws BACnetParseException {
//
//        		super(apdu);
//        		ByteBuf rawAPDU = apdu.getPayload();
//                LOG.debug("expecting BACnetObjectIdentifier for iAmDeviceIdentifier");
//                objectIdentifier = (BACnetObjectIdentifier)parser.parseTag(rawAPDU, BACnetObjectIdentifier.class, null);
//                LOG.debug("expecting unsigned integer for maxAPDULengthAccepted");
//                maxAPDULengthAccepted = (BACnetUnsignedInteger)parser.parseTag(rawAPDU, BACnetUnsignedInteger.class, null);
//                LOG.debug("expecting BACnetSegmentation, for segmentationSupported");
//                segmentationSupported = new BACnetSegmentation((BACnetEnumerated)parser.parseTag(rawAPDU, BACnetSegmentation.class, null));
//                LOG.debug("expecting unsigned integer for vendorID");
//                vendorID = (BACnetUnsignedInteger)parser.parseTag(rawAPDU, BACnetUnsignedInteger.class, null);
//
//                LOG.debug(this.toString());
//                
//        }
//
//        /**
//         * @return the objectIdentifier
//         */
//        public BACnetObjectIdentifier getObjectIdentifier() {
//                return objectIdentifier;
//        }
//
//        /**
//         * @param objectIdentifier the objectIdentifier to set
//         */
//        public void setObjectIdentifier(BACnetObjectIdentifier objectIdentifier) {
//                this.objectIdentifier = objectIdentifier;
//        }
//
//        /**
//         * @return the maxAPDULengthAccepted
//         */
//        public BACnetUnsignedInteger getMaxAPDULengthAccepted() {
//                return maxAPDULengthAccepted;
//        }
//
//        /**
//         * @param maxAPDULengthAccepted the maxAPDULengthAccepted to set
//         */
//        public void setMaxAPDULengthAccepted(BACnetUnsignedInteger maxAPDULengthAccepted) {
//                this.maxAPDULengthAccepted = maxAPDULengthAccepted;
//        }
//
//        /**
//         * @return the segmentationSupported
//         */
//        public BACnetSegmentation getSegmentationSupported() {
//                return segmentationSupported;
//        }
//
//        /**
//         * @param segmentationSupported the segmentationSupported to set
//         */
//        public void setSegmentationSupported(BACnetSegmentation segmentationSupported) {
//                this.segmentationSupported = segmentationSupported;
//        }
//
//        /**
//         * @return the vendorID
//         */
//        public BACnetUnsignedInteger getVendorID() {
//                return vendorID;
//        }
//
//        /**
//         * @param vendorID the vendorID to set
//         */
//        public void setVendorID(BACnetUnsignedInteger vendorID) {
//                this.vendorID = vendorID;
//        }
//        
//        public void serializeToBuffer(ByteBuf buf) {
//        	
//        }
//
//        @Override
//        public String toString() {
//                return "IAmService{" + "objectIdentifier=" + objectIdentifier + ", maxAPDULengthAccepted=" + maxAPDULengthAccepted + ", segmentationSupported=" + segmentationSupported + ", vendorID=" + vendorID + '}';
//        }
//        
//}
