package io.openbac.bacnet.type;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;

public class BACnetAny<T extends BACnetEncodable> extends BACnetEncodable {

	private T value;

	public BACnetAny(T val) {
		this.value = val;

	}

	public T getValue() {
		return value;
	}

	/**
	 * BACnet Any has to always encode with open and closing tags whatever is inside??
	 */
	@Override
	public void encode(ByteBuf buf, int contextId) {
		// open tag
//		buf.writeByte(encodeOpeningTag(contextId));
		
		// encode encapsulated type
		if(value instanceof BACnetPrimitive) {
			((BACnetPrimitive) value).encodeApplication(buf);
		} else {
			value.encode(buf, contextId); // TODO check if it is correct to always use 0 as contextId ??
		}
		// close tag
//		buf.writeByte(encodeClosingTag(contextId));
	}

	@Override
	public Class<?> getType() {
		return BACnetAny.class;
	}

}
