/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import java.math.BigInteger;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetSignedInteger extends BACnetPrimitive {

	private long valueInteger = 0;

	public BACnetSignedInteger(int valueInteger) {
		this.valueInteger = valueInteger;
	}

	public BACnetSignedInteger(final ByteBuf data) {
		buf = data;
		decodeTag();

		if (tagLVT > 0) {

			if (tagLVT == 1) {
				this.valueInteger = buf.readByte();
			} else if (tagLVT == 2) {
				this.valueInteger = buf.readShort();
			} else if (tagLVT == 4) {
				this.valueInteger = buf.readInt();
			}

		} else {
			throw new IllegalArgumentException("Invalid signed integer data");
		}

	}

	public long getValue() {
		return valueInteger;
	}


	public void setValue(int value) {
		this.valueInteger = value;
	}

	@Override
	public String toString() {
		return "BACnetSignedInteger{" + "value=" + valueInteger + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetSignedInteger.class;
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
