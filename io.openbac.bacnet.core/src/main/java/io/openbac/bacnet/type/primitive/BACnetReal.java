/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.util.TagUtils;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetReal extends BACnetPrimitive {

	private float value;

	public BACnetReal(float real) {

		this.value = real;

	}

	public BACnetReal(final ByteBuf data) {

		buf = data;
		decodeTag();
		
		if (length != 4) {
			throw new IllegalArgumentException("Invalid length: " + length);
		}
		value = buf.readFloat();

	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "BACnetReal{" + "value=" + value + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetPrimitive.class;
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		TagUtils.encodeApplicationTagAndLength(data, BACnetPrimitive.Type.REAL.type, 8);
		buf.writeFloat(value);
		
	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		TagUtils.encodeTagIdAndLength(buf, contextId, 8);
		buf.writeFloat(value);
		
	}

}
