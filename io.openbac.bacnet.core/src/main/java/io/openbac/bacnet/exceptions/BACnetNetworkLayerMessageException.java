package io.openbac.bacnet.exceptions;

public class BACnetNetworkLayerMessageException extends BACnetBaseException {

    public static enum NetworkLayerExceptionType implements IBACnetExceptionEnum {
        INVALID_MESSAGE_TYPE(0x00, "Datagram has invalid network layer message type");

        private final int exceptionType;
        private final String text;
        
        NetworkLayerExceptionType(final int exceptionType, final String text) {
            this.exceptionType = exceptionType;
            this.text = text;
        }

        @Override
        public int getExceptionType() {
            return this.exceptionType;
        }
        
        @Override
        public String getText() {
            return this.text;
        }

    }        

    public BACnetNetworkLayerMessageException(final String msg, final NetworkLayerExceptionType exceptionType) {
        super(msg, exceptionType);
    }

    public BACnetNetworkLayerMessageException(final String msg, final Throwable t, final NetworkLayerExceptionType exceptionType) {
        super(msg, t, exceptionType);
    }

    public BACnetNetworkLayerMessageException(final String msg) {
        super(msg);
    }

    public BACnetNetworkLayerMessageException(final String msg, final Throwable t) {
        super(msg, t);
    }

}
