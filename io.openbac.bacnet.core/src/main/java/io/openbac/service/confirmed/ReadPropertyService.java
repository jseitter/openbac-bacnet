package io.openbac.service.confirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.enumerated.BACnetPropertyIdentifier;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

public class ReadPropertyService {

	// Structure of the ReadProperty Service
	// [0] BACnetObjectIdentifier
	private  BACnetObjectIdentifier objectIdentifier;
	// [1] BACnetPropertyIdentifier
	private BACnetPropertyIdentifier propertyIdentifier;
	// [2] Unsigned OPTIONAL
	private BACnetUnsignedInteger propertyArrayIndex;
	
	
	/**
	 * writes the encoded service into the ByteBuffer
	 * starting with the Service Choice Element of the APDU
	 * @param data
	 */
	public void encode(final ByteBuf data) {
		// write service choice
		data.writeByte(ConfirmedServiceChoice.READ_PROPERTY.value);
		objectIdentifier.encode(data,0);
		propertyIdentifier.encode(data,1);
	}
	
	
	

	
}
