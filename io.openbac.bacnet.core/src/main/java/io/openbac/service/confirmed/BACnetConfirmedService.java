package io.openbac.service.confirmed;

public abstract class BACnetConfirmedService {

	public enum Choice {

		READ_PROPERTY((byte)12);

		// keep in mind that byte is signed
		byte serviceChoice;

		Choice(byte value) {
			this.serviceChoice=value;
		}
		
	}
	
}
