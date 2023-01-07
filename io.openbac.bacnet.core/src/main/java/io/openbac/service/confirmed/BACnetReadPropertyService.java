package io.openbac.service.confirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.BACnetAny;
import io.openbac.bacnet.type.enumerated.BACnetPropertyIdentifier;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

/**
 * Handles the ReadProperty Service
 * @author joerg
 *
 */
public class BACnetReadPropertyService extends BACnetConfirmedService{

	/**
	 * Request for ReadProperty
	 * @author Joerg Seitter
	 *
	 */
	public static class ReadPropertyRequest {
		// Structure of the ReadProperty Request
		// [0] BACnetObjectIdentifier
		private  BACnetObjectIdentifier objectIdentifier;
		// [1] BACnetPropertyIdentifier
		private BACnetPropertyIdentifier propertyIdentifier;
		// [2] Unsigned OPTIONAL
		private BACnetUnsignedInteger propertyArrayIndex;
		
		
		
		
		public BACnetObjectIdentifier getObjectIdentifier() {
			return objectIdentifier;
		}



		public void setObjectIdentifier(BACnetObjectIdentifier objectIdentifier) {
			this.objectIdentifier = objectIdentifier;
		}



		public BACnetPropertyIdentifier getPropertyIdentifier() {
			return propertyIdentifier;
		}



		public void setPropertyIdentifier(BACnetPropertyIdentifier propertyIdentifier) {
			this.propertyIdentifier = propertyIdentifier;
		}



		public BACnetUnsignedInteger getPropertyArrayIndex() {
			return propertyArrayIndex;
		}



		public void setPropertyArrayIndex(BACnetUnsignedInteger propertyArrayIndex) {
			this.propertyArrayIndex = propertyArrayIndex;
		}



		/**
		 * writes the encoded service into the ByteBuffer
		 * starting with the Service Choice Element of the APDU
		 * @param data
		 */
		public void encode(final ByteBuf data) {
			// write service choice
			data.writeByte(BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice);
			objectIdentifier.encode(data,0);
			propertyIdentifier.encode(data,1);
		}



		public byte getServiceChoice() {
			return BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice;
		}
		
	}
	
	/**
	 * Response for ReadProperty
	 * @author Joerg Seitter
	 *
	 */
	public static class ReadPropertyACK {
		
		private BACnetObjectIdentifier objectIdentifier; // ctx 0
		private BACnetPropertyIdentifier propertyIdentifier; // ctx 1
		private BACnetUnsignedInteger propertyArrayIndex; // ctx 2 optional
		private BACnetAny propertyValue; // ctx 3
		
		public BACnetObjectIdentifier getObjectIdentifier() {
			return objectIdentifier;
		}

		public void setObjectIdentifier(BACnetObjectIdentifier objectIdentifier) {
			this.objectIdentifier = objectIdentifier;
		}

		public BACnetPropertyIdentifier getPropertyIdentifier() {
			return propertyIdentifier;
		}

		public void setPropertyIdentifier(BACnetPropertyIdentifier propertyIdentifier) {
			this.propertyIdentifier = propertyIdentifier;
		}

		public BACnetUnsignedInteger getPropertyArrayIndex() {
			return propertyArrayIndex;
		}

		public void setPropertyArrayIndex(BACnetUnsignedInteger propertyArrayIndex) {
			this.propertyArrayIndex = propertyArrayIndex;
		}

		public BACnetAny getPropertyValue() {
			return propertyValue;
		}

		public void setPropertyValue(BACnetAny propertyValue) {
			this.propertyValue = propertyValue;
		}


		
		public void encode(final ByteBuf data) {
			data.writeByte(BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice);
			objectIdentifier.encode(data, 0);
			propertyIdentifier.encode(data, 1);
			if(propertyArrayIndex!=null) 
				propertyArrayIndex.encode(data, 2);
			propertyValue.encode(data, 3);
			
		}
		
	}
	

	
}
