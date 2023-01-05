package io.openbac.bacnet.exceptions;

public class BACnetBaseException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -6796233589070425084L;
    private final IBACnetExceptionEnum exceptionType;
    
    public BACnetBaseException(final String msg, final IBACnetExceptionEnum exceptionType) {
        super(msg);
        this.exceptionType = exceptionType;
    }

    public BACnetBaseException(final String msg, final Throwable t, final IBACnetExceptionEnum exceptionType) {
        super(msg, t);
        this.exceptionType = exceptionType;
    }

    public BACnetBaseException(final String msg) {
        super(msg);
        this.exceptionType = null;
    }

    public BACnetBaseException(final String msg, final Throwable t) {
        super(msg, t);
        this.exceptionType = null;
    }

    @Override
    public String getMessage() {
        
        StringBuilder msg = new StringBuilder();
        msg.append(super.getMessage());
        msg.append("\n");
        msg.append(this.exceptionType.getExceptionType());
        msg.append(" - ");
        msg.append(this.exceptionType.getText());
        return msg.toString();
        
    }

    @Override
    public String getLocalizedMessage() {
        return this.getMessage();
    }
        
}
