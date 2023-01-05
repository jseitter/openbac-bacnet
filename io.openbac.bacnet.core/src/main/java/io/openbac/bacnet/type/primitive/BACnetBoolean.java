package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.exceptions.BACnetParseException.ParseExceptionType;
import io.openbac.util.TagUtils;

/**
 * Implementation of BACnet Boolean datatype
 * 
 * @author JSeitter
 */
public class BACnetBoolean extends BACnetPrimitive {

	private boolean value;

	/**
	 * Constructor for construction messsages
	 * 
	 * @param bool
	 */
	public BACnetBoolean(boolean bool) {
		this.value = bool;

	}

	/**
	 * constructor for decoding primitive from ByteBuf
	 * 
	 * @param data
	 * @throws BACnetParseException
	 */
	public BACnetBoolean(ByteBuf data) throws BACnetParseException {

		buf = data;
		decodeTag();

		// sanity check
		if (tagLVT > 1) {
			throw new BACnetParseException("Invalid boolean data", ParseExceptionType.INVALID_SERVICE_ENCODING);
		}

		// decoding
		if (tagClass == CLASS_PRIMITIVE) {
			// if tagLVT is 1 it's true (no explicit check for false)
			value = (tagLVT == 0x01);
		}
		// in constructed case the boolean is stored in teh next byte
		if (tagClass == CLASS_CONSTRUCTED) {
			value = buf.readBoolean();
		}

	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BACnetBoolean{" + "value=" + value + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetBoolean.class;
	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		TagUtils.encodeTagIdAndLength(buf, contextId, 1);
		buf.writeByte(value == true ? 1 : 0);
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		TagUtils.encodeTagIdAndLength(data, BACnetPrimitive.Type.BOOLEAN.type, value == true ? 1 : 0);

	}

}
