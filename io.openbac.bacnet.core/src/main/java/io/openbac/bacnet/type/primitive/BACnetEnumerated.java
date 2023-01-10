package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.util.TagUtils;

/**
 * Enumerated Primitive
 * @author Joerg Seitter
 *
 */
public class BACnetEnumerated extends BACnetPrimitive {

	protected long valueInteger = 0;

	public BACnetEnumerated(int valueInteger) {
		if (valueInteger < 0) {
			throw new IllegalArgumentException("Value has to be unsigned!");
		}
		this.valueInteger = valueInteger;
		this.length=getLength();
	}

	public BACnetEnumerated(final ByteBuf data) {
		buf = data;
		decodeTag();

		if (tagLVT > 0) {
			if (tagLVT == 1) {
				this.valueInteger = buf.readUnsignedByte();
			} else if (tagLVT == 2) {
				this.valueInteger = buf.readUnsignedShort();
			} else if (tagLVT == 4) {
				this.valueInteger = buf.readUnsignedInt();
			}

		} else {
			throw new IllegalArgumentException("Invalid unsigned integer data");
		}
		this.length=tagLVT;
	}

	public int intValue() {
		return (int) valueInteger;
	}

	@Override
	public void encode(final ByteBuf buf, int contextId) {


		TagUtils.encodeTagIdAndLength(buf, contextId, (int)length);
		switch ((int)length) {
		case 1:
			buf.writeByte((int) (valueInteger & 0xff));
			break;
		case 2:
			buf.writeShort((int) (valueInteger & 0xffff));
			break;
		case 4:
			buf.writeInt((int) (valueInteger & 0xffffffff));
			break;
		}
	}

	@Override
	public void encodeApplication(ByteBuf data) {

		TagUtils.encodeApplicationTagAndLength(data, BACnetPrimitive.Type.ENUMERATED.type, (int)length);
		switch ((int)length) {
		case 1:
			data.writeByte((int) (valueInteger & 0xff));
			break;

		case 2:
			data.writeShort((int) (valueInteger & 0xffff));
			break;
		case 4:
			data.writeInt((int) (valueInteger & 0xffffffff));
			break;
		}

	}

	/**
	 * 
	 * @return the needed amount of bytes to store the integer
	 */
	private int getLength() {

		int length;
		if (valueInteger < 0x100)
			length = 1;
		else if (valueInteger < 0x10000)
			length = 2;
		else if (valueInteger < 0x1000000)
			length = 3;
		else
			length = 4;

		return length;

	}

}
