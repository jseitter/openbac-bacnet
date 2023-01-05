package io.openbac.bacnet.type.primitive;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.openbac.util.HexUtils;

/**
 * @author Tobias Breckle
 * @author Joerg Seitter
 */
public class BACnetOctetString extends BACnetPrimitive {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(value);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BACnetOctetString other = (BACnetOctetString) obj;
		if (!Arrays.equals(value, other.value))
			return false;
		return true;
	}

	private byte[] value;

	public BACnetOctetString(final byte[] value) {
		this.value = value;
	}

	public BACnetOctetString(final ByteBuf data) {
		buf = data;
		decodeTag();
		
		value = new byte[(int)length];
		buf.readBytes(value);
	}

	public byte[] getValue() {
		return value;
	}

	public void setValue(byte[] value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BACnetOctetString{" + "value=0x");
		for (byte b : value) {
			sb.append(HexUtils.convert(b));
		}
		sb.append('}');
		return sb.toString();
	}

	@Override
	public Class<?> getType() {
		return BACnetOctetString.class;
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		// TODO Auto-generated method stub

	}

}
