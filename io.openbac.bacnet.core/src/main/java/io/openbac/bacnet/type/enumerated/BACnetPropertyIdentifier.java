package io.openbac.bacnet.type.enumerated;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.primitive.BACnetEnumerated;

public enum BACnetPropertyIdentifier {

	PRESENT_VALUE(85);
	
	private int value;
	
	 BACnetPropertyIdentifier(int value) {
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
	public void encode(final ByteBuf data, int contextId) {
		// PI is encoded as Enumerated
		BACnetEnumerated enumerated = new BACnetEnumerated(value);
		enumerated.encode(data, contextId);

		
	}
}
