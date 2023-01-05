package io.openbac.bacnet.exceptions;

public class BACnetParseException extends Exception {

    public static enum ParseExceptionType {
        NO_BACNET_IP_DATAGRAM(0x00, "Datagram is not an BACnet/IP datagram"),
        SLEN_IS_ZERO(0x01, "Datagram NPDU SLEN is zero"),
        HOPCOUNT_IS_ZERO(0x02, "Datagram hopcount is zero"),
        INVALID_BACNET_VERSION(0x03, "Invalid BACnet version number in datagram"),
        BVLL_LENGTH_ZERO(0x04, "BVLL Length is equal or below zero"),
        INVALID_PRIMITIVE_LENGTH(0x05, "Primitve length is below zero"),
    	INVALID_PDU_TYPE(0x06,"pdu type value is unknown"),
    	INVALID_SERVICE_ENCODING(0x07, "part of the service encoding is invalid");

        private final int exceptionType;
        private final String text;
        
        ParseExceptionType(final int exceptionType, final String text) {
            this.exceptionType = exceptionType;
            this.text = text;
        }


        public int getExceptionType() {
            return this.exceptionType;
        }
        

        public String getText() {
            return this.text;
        }

    }   
    
    private final ParseExceptionType exceptionType;

    public BACnetParseException(final String msg, final ParseExceptionType exceptionType) {
    	super(msg);
    	this.exceptionType=exceptionType;
    }

    public BACnetParseException(final String msg, final Throwable t, final ParseExceptionType exceptionType) {
    	super(msg,t);
    	this.exceptionType=exceptionType;
    }

    public String getMessage() {
        
        StringBuilder msg = new StringBuilder();
        msg.append(super.getMessage());
        msg.append("\n");
        msg.append(this.exceptionType.getExceptionType());
        msg.append(" - ");
        msg.append(this.exceptionType.getText());
        return msg.toString();
        
    }
}
