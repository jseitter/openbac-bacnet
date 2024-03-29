package io.openbac.service.confirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.api.device.BACnetLocalDevice;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.net.apdu.BACnetAPDU;
import io.openbac.bacnet.net.apdu.BACnetComplexAckAPDU;
import io.openbac.bacnet.type.BACnetAny;
import io.openbac.bacnet.type.BACnetEncodable;
import io.openbac.bacnet.type.BACnetSequenceOf;
import io.openbac.bacnet.type.enumerated.BACnetObjectType;
import io.openbac.bacnet.type.enumerated.BACnetPropertyIdentifier;
import io.openbac.bacnet.type.primitive.BACnetEnumerated;
import io.openbac.bacnet.type.primitive.BACnetObjectIdentifier;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.bacnet.type.primitive.BACnetUnsignedInteger;

/**
 * Handles the ReadProperty Service
 * 
 * @author joerg
 *
 */
public class BACnetReadPropertyService extends BACnetConfirmedService {

	/**
	 * The request handling
	 */
	private ReadPropertyRequest request;
	/**
	 * the response handling
	 */
	private ReadPropertyACK response;
	// public static final byte serviceChoice =
	// BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice;

	public BACnetReadPropertyService(ByteBuf apdu) throws BACnetParseException {
		request = new ReadPropertyRequest(apdu);
	}

	// TODO needed ?
	public Choice getServiceChoice() {
		return BACnetConfirmedService.Choice.READ_PROPERTY;
	}

	/**
	 * get the object Identifier of the service
	 * 
	 * @return BACnetObjectIdentifier
	 */
	public BACnetObjectIdentifier getObjectIdentifier() {
		return request.getObjectIdentifier();
	}

	/**
	 * get the property identifier of the service
	 * 
	 * @return
	 */
	public BACnetPropertyIdentifier getPropertyIdentifier() {
		return request.getPropertyIdentifier();

	}

	public BACnetAPDU handleService(int invokeId, BACnetLocalDevice device) {

		BACnetObjectIdentifier id = this.getObjectIdentifier();
		switch (id.getObjectType().intValue()) {

		case BACnetObjectType.DEVICE: {
			BACnetPropertyIdentifier prop = this.getPropertyIdentifier();
			// prepare response
			ReadPropertyACK result = new ReadPropertyACK();
			result.setObjectIdentifier(id);
			result.setPropertyIdentifier(prop);
			// get result value
			switch (prop.intValue()) {

			case BACnetPropertyIdentifier.OBJECTLIST: { // object list
				BACnetSequenceOf<BACnetObjectIdentifier> res = device.getDeviceObject().getObjectList();
				BACnetAny<BACnetEncodable> any = new BACnetAny<BACnetEncodable>(res);
				result.setPropertyValue(any);
				break;
			}

			default: {
				// if we reach default the requested Property is not supported and we have to
				// handle this
				// TODO: handle the error
			}

			}

			// encapsulate in ComplexAck
			BACnetComplexAckAPDU ack = BACnetComplexAckAPDU.createFor(result, invokeId);
			return ack;

		}

		default: {
			// BACNet
			// create error
		}

		}
		return null;
	}

	/**
	 * Request for ReadProperty
	 * 
	 * @author Joerg Seitter
	 *
	 */
	public static class ReadPropertyRequest {
		// Structure of the ReadProperty Request
		// [0] BACnetObjectIdentifier
		private BACnetObjectIdentifier objectIdentifier;
		// [1] BACnetPropertyIdentifier
		private BACnetPropertyIdentifier propertyIdentifier;
		// [2] Unsigned OPTIONAL
		private BACnetUnsignedInteger propertyArrayIndex = null;

		/**
		 * decoding constructor
		 * 
		 * @param apdu
		 * @throws BACnetParseException
		 */
		protected ReadPropertyRequest(ByteBuf apdu) throws BACnetParseException {
			objectIdentifier = new BACnetObjectIdentifier(apdu);
			propertyIdentifier = new BACnetPropertyIdentifier(
					BACnetPrimitive.createPrimitive(BACnetEnumerated.class, apdu));
			// TODO: handle optional field

		}

		// default ctor
		public ReadPropertyRequest() {

		}

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
		 * writes the encoded service into the ByteBuffer starting with the Service
		 * Choice Element of the APDU
		 * 
		 * @param data
		 */
		public void encode(final ByteBuf data) {
			// write service choice
			data.writeByte(BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice);
			objectIdentifier.encode(data, 0);
			propertyIdentifier.encode(data, 1);
		}

	}

	/**
	 * Response for ReadProperty
	 * 
	 * @author Joerg Seitter
	 *
	 */
	public static class ReadPropertyACK implements BACnetConfirmedService.BACnetResponse {
		// ctx 0
		private BACnetObjectIdentifier objectIdentifier;
		// ctx 1
		private BACnetPropertyIdentifier propertyIdentifier;
		// ctx 2 optional
		private BACnetUnsignedInteger propertyArrayIndex;
		// ctx 3
		private BACnetAny<? extends BACnetEncodable> propertyValue;

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

		public BACnetAny<? extends BACnetEncodable> getPropertyValue() {
			return propertyValue;
		}

		public void setPropertyValue(BACnetAny<? extends BACnetEncodable> propertyValue) {
			this.propertyValue = propertyValue;
		}

		@Override
		public void encode(ByteBuf data) {
			data.writeByte(BACnetConfirmedService.Choice.READ_PROPERTY.serviceChoice);
			objectIdentifier.encode(data, 0);
			propertyIdentifier.encode(data, 1);
			if (propertyArrayIndex != null)
				propertyArrayIndex.encode(data, 2);
			propertyValue.encode(data, 3);

		}

	}

}
