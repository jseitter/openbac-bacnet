package io.openbac.bacnet.type.constructed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.BACnetEncodable;
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
	 * BACnet Any has to always encode with open and closing tags whatever is inside
	 */
	@Override
	public void encode(ByteBuf buf, int contextId) {
		// open tag
		int openTag = 0x00;
		openTag = openTag | (contextId << 4);
		openTag = openTag | 0x0E;
		buf.writeByte(openTag);
		
		// encode encapsulated type
		if(value instanceof BACnetPrimitive) {
			((BACnetPrimitive) value).encodeApplication(buf);
		} else {
			value.encode(buf, 0); // TODO check if it is correct to always use 0 as contextId ??
		}
		//close tag
		int closeTag = 0x00;
		closeTag = closeTag | (contextId << 4);
		closeTag = closeTag | 0x0F;
	}

	@Override
	public Class<?> getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
