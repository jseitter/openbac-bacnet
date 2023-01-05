package io.openbac.service.unconfirmed;

public enum BACnetUnconfirmedServiceChoice {

	I_AM((byte) 0x00), I_HAVE((byte) 0x01), UNCONFIRMED_COV_NOTIFICATION((byte) 0x02),
	UNCONFIRMED_EVENT_NOTIFICATION((byte) 0x03), UNCONFIRMED_PRIVATE_TRANSFER((byte) 0x04),
	UNCONFIRMED_TEXT_MESSAGE((byte) 0x05), TIME_SYNCHRONISATION((byte) 0x06), WHO_HAS((byte) 0x07), WHO_IS((byte) 0x08),
	UTC_TIME_SYNCHRONISATION((byte) 0x09);

	byte serviceChoice;

	BACnetUnconfirmedServiceChoice(byte serviceChoice) {

		this.serviceChoice = serviceChoice;
	}
	
	public static BACnetUnconfirmedServiceChoice forId(byte serviceChoice) {
		
		BACnetUnconfirmedServiceChoice[] vals = values();
		
		for(int i=0; i<vals.length; i++) {
			if(serviceChoice==vals[i].serviceChoice) 
				return vals[i];
			
		}
		return null;
	}
}
