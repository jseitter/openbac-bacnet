package io.openbac.bacnet.net.npdu;

import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.openbac.bacnet.exceptions.BACnetNetworkException;
import io.openbac.bacnet.exceptions.BACnetNetworkLayerMessageException;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.util.HexUtils;

public class BACnetBVLL {

        private static final Logger LOG = LoggerFactory.getLogger(BACnetBVLL.class);
       
        private ByteBuf buf;
        
        private InetSocketAddress sourceAddress;
        
        public ByteBuf getPayload() {
			return buf;
		}

        private BACnetNPDU npdu;
        
		/**
         * 0x81 means BACnet/IP
         */
        private byte type = (byte) 0x81;
        /**
         * encodes function
         *
         * <pre>
         * 	 		0x01 	Write Broadcast Distribution Table
         * 			0x02 	Read Broadcast Distribution Table
         * 			0x03 	Read Broadcast Distribution Table ACK
         * 			0x04 	Forwarded-NPDU 							Optional Originating Device IP address and Port included in BVLL header
         * 			0x05 	Register Foreign Device 				Registers Foreign Device with expiration timeout
         * 			0x0a 	Original-Unicast-NPDU					Used to send directed NPDUs to another BACnet/IP device or router.
         * 															Optional Originating Device IP address and Port NOT included in BVLL header.
         * 															See Specification Section J.2.11, page 568
         * 			0x0b 	Original-Broadcast-NPDU					Used by devices (except foreign devices) to broadcast messages on B/IP networks.
         * 															Optional Originating Device IP address and Port NOT included in BVLL header
         * </pre>
         */
        private byte bvlcFunction;
        /**
         * Length of BVLL
         */
        private int bvllLength;
        /**
         * IP Address of originating device
         */
        private byte[] ipAddressOriginDevice = new byte[4];
        /**
         * port number of originating device
         */
        private byte[] portNrOriginDevice = new byte[2];
        /**
         * 
         */
        private FunctionType functionType;

        /**
         * ENUM for values of field bvlcFunction
         *
         * @author jseitter
         *
         */
        public enum FunctionType {

                WRITE_BROADCAST_DISTRIBUTION_TABLE((byte) 0x01),
                READ_BROADCAST_DISTRIBUTION_TABLE((byte) 0x02),
                READ_BROADCAST_DISTRIBUTION_TABLE_ACK((byte) 0x03),
                FORWARDED_NPDU((byte) 0x04),
                REGISTER_FOREIGN_DEVICE((byte) 0x05),
                READ_FOREIGN_DEVICE_TABLE((byte) 0x06),
                READ_FOREIGN_DEVICE_TABLE_ACK((byte) 0x07),
                DELETE_FOREIGN_DEVICE_TABLE_ENTRY((byte) 0x08),
                DISTRIBUTE_BROADCAST_TO_NETWORK((byte) 0x09),
                ORIGINAL_UNICAST_NPDU((byte) 0x0a),
                ORIGINAL_BROADCAST_NPDU((byte) 0x0b);

                private final byte functionId;

                FunctionType(byte functionId) {
                        this.functionId = functionId;
                }

                public static FunctionType getFunctionType(byte fctId) {

                        for (FunctionType f : values()) {

                                if (f.functionId == fctId) {
                                        return f;
                                }

                        }
                        throw new BACnetNetworkException("functionId has invalid value");

                }

        }

        public BACnetBVLL(FunctionType fctType, BACnetNPDU npdu) {
        	this.bvlcFunction=fctType.functionId;
        	this.npdu=npdu;
        }
        
        /**
         * Constructor takes an IoBuffer and decodes the BACnet Virtual Link Layer
         *
         * @param rawDatagram
         * @throws BACnetParseException
         * @throws BACnetNetworkLayerMessageException
         */
        public BACnetBVLL(ByteBuf rawDatagram, InetSocketAddress src) throws BACnetParseException{

              LOG.debug("processing BACnet datagram");
              
              	sourceAddress=src;
                type = rawDatagram.readByte();
                LOG.debug("type 0x" + HexUtils.convert(type));

                if (type != (byte) 0x81) {
                        // no bacnet/ip datagram
                        LOG.debug("position: " + rawDatagram.readerIndex());
                        rawDatagram.resetReaderIndex();
                
                        throw new BACnetParseException("bvll type is not 0x81: " + HexUtils.convert(type), BACnetParseException.ParseExceptionType.NO_BACNET_IP_DATAGRAM);
                }

                bvlcFunction = rawDatagram.readByte();
                functionType = FunctionType.getFunctionType(bvlcFunction);
                LOG.debug("bvlcFunction 0x" + HexUtils.convert(bvlcFunction) + " = " + functionType.name());
                bvllLength = rawDatagram.readShort();
                LOG.debug("bvllLength " + bvllLength);

                // copy the unparsed section of the packet to rawData
                buf = rawDatagram.slice();
                switch (functionType) {
                        case FORWARDED_NPDU: {
                                // extract optional ip address data
                                rawDatagram.readBytes(ipAddressOriginDevice);
                                rawDatagram.readBytes(portNrOriginDevice);

                                LOG.debug("forwarded npdu with optional fields");
                                LOG.debug("origin ipaddress 0x" + HexUtils.convert(ipAddressOriginDevice));
                                LOG.debug("origin port 0x" + HexUtils.convert(portNrOriginDevice));
                                break;
                        }
				default:
					break;
                }

                int npduLength = bvllLength - 4;
                if (npduLength <= 0) {
                        throw new BACnetParseException("npdu length minus header is < 0", BACnetParseException.ParseExceptionType.BVLL_LENGTH_ZERO);
                }
                LOG.debug("size of NPDU " + npduLength + " octets");

        }

        /*
         * return true if the BVLL contains any NPDU
         */
        public boolean isNPDU() {
        	
        	return bvlcFunction==0x0b || bvlcFunction==0x0a || bvlcFunction==0x04;
        }
        
        public byte getBvlcFunction() {
                return bvlcFunction;
        }

        public void setBvlcFunction(byte bvlcFunction) {
                this.bvlcFunction = bvlcFunction;
        }

        public int getBvllLength() {
                return bvllLength;
        }

        public void setBvllLength(int bvllLength) {
                this.bvllLength = bvllLength;
        }

        public byte[] getIpAddressOriginDevice() {
                return ipAddressOriginDevice;
        }

        public void setIpAddressOriginDevice(byte[] ipAddressOriginDevice) {
                this.ipAddressOriginDevice = ipAddressOriginDevice;
        }

        public byte[] getPortNrOriginDevice() {
                return portNrOriginDevice;
        }

        public void setPortNrOriginDevice(byte[] portNrOriginDevice) {
                this.portNrOriginDevice = portNrOriginDevice;
        }

        public FunctionType getFunctionType() {
                return functionType;
        }

        public void setFunctionType(FunctionType ft) {
                this.functionType = ft;
        }

		public InetSocketAddress getSourceAddress() {
			return sourceAddress;
		}
		
		public void encode(final ByteBuf buf) {
			
			ByteBuf temp = Unpooled.buffer();
			npdu.encode(temp);
			bvllLength=temp.writerIndex()+4; //TODO checken ob index 1 off ist
			// write the 0x81
			buf.writeByte(type);
			// encode function
			buf.writeByte(bvlcFunction);
			// encode length
			buf.writeShort(bvllLength);
			buf.writeBytes(temp, temp.writerIndex()); //TODO one off check
			
		}
}
