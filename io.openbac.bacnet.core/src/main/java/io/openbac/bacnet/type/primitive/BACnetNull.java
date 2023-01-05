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
public class BACnetNull extends BACnetPrimitive {

        public BACnetNull() {

        }

        @Override
        public String toString() {
                return "BACnetNull{}";
        }

        @Override
        public Class<?> getType() {
                return BACnetNull.class;
        }

		@Override
		public void encode(ByteBuf buf, int contextId) {
			TagUtils.encodeApplicationTagAndLength(buf, contextId, 0);
		}

		@Override
		public void encodeApplication(ByteBuf data) {
			buf.writeByte(0x00);
			
		}
		


}
