package io.openbac.bacnet.type.constructed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.type.BACnetEncodable;

public abstract class BAcnetConstructedType extends BACnetEncodable {

	@Override
	protected abstract void encode(ByteBuf buf, int contextId);

	@Override
	public abstract Class<?> getType();



}
