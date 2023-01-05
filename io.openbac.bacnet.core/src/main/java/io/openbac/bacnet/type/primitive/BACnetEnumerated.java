package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;

public class BACnetEnumerated extends BACnetPrimitive {

	private long valueInteger = 0;

	public BACnetEnumerated(int valueInteger) {
		if (valueInteger < 0) {
			throw new IllegalArgumentException("Value has to be unsigned!");
		}
		this.valueInteger = valueInteger;
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

	}

	public int intValue() {
		return (int) valueInteger;
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
