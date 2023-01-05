package io.openbac.bacnet.exceptions;



public class BACnetNetworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6796233589070425084L;

	public BACnetNetworkException(String msg) {
		super(msg);
	}

	public BACnetNetworkException(String msg,Throwable t) {
		super(msg,t);
	}

}
