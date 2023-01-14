/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import java.util.Arrays;
import java.util.BitSet;

import org.apache.commons.lang3.BitField;
import org.apache.commons.lang3.NotImplementedException;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Tobias Breckle
 * @author Joerg Seitter
 */
public class BACnetBitString extends BACnetPrimitive {

	private boolean[] value;

	public BACnetBitString() {
		this.value = new boolean[0];
	}

	public BACnetBitString(final boolean[] bits) {
		this.value = bits;
	}

	public BACnetBitString(final BACnetBitString bitstring) {
		this.value = bitstring.value;
		this.length = bitstring.length;
	}

	/**
	 * initialize from a 64bit value (max. 64 bits can be initialized)
	 * 
	 * @param bits
	 */
	protected BACnetBitString(long bits) {
		boolean[] values = new boolean[64];
		for(int i=0; i<64; i++) {
			values[i] = ((bits >> i) & 0x01)==1 ? true:false; 
		}
		
	}

	public BACnetBitString(final ByteBuf data) {

		buf = data;
		decodeTag();

		if (tagLVT < 1) {
			throw new IllegalArgumentException("Invalid length: " + tagLVT);
		}

		final int unusedBitsInLastOctet = buf.readByte();

		final byte[] dataWithoutTailLength = new byte[tagLVT - 1];
		buf.readBytes(dataWithoutTailLength);

		final int length = dataWithoutTailLength.length * 8 - unusedBitsInLastOctet;
		value = new boolean[length];
		for (int i = 0; i < length; i++) {
			value[i] = (((dataWithoutTailLength[i / 8] >> (7 - (i % 8))) & 0x01) == 1);
		}

	}

	public boolean[] getValue() {
		return value;
	}

	public void setValue(final boolean[] value) {
		this.value = value;
	}

	public boolean getBitValue(final int index) {

		if (index > value.length || index < 0) {
			throw new IllegalArgumentException("Invalid index: " + index);
		}

		return value[index];

	}

	@Override
	public String toString() {
		return "BACnetBitString{" + "value=" + Arrays.toString(value) + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetBitString.class;
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		throw new NotImplementedException("This is not implemented, yet");

	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		throw new NotImplementedException("This is not implemented, yet");

	}

}
