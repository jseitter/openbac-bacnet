package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.util.TagUtils;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetDouble extends BACnetPrimitive {

	private double value;

	public BACnetDouble(double real) {

		this.value = real;

	}

	public BACnetDouble(final ByteBuf data) {
		buf=data;
		decodeTag();
		
		if (length != 8) {
			throw new IllegalArgumentException("Invalid length: " + length);
		}

		value = buf.readDouble();

	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BACnetDouble{" + "value=" + value + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetDouble.class;
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		TagUtils.encodeApplicationTagAndLength(data, BACnetPrimitive.Type.DOUBLE.type, 8);
		buf.writeDouble(value);
		
	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		TagUtils.encodeTagIdAndLength(buf, contextId, 8);
		buf.writeDouble(value);
		
	}
}
