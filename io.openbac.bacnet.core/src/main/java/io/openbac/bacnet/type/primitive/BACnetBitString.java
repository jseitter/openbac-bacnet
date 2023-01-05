/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import java.util.Arrays;

import io.netty.buffer.ByteBuf;

/**
 *
 * @author Tobias Breckle
 */
public class BACnetBitString extends BACnetPrimitive {

        private boolean[] value;

        public BACnetBitString() {
        		this.value = new boolean[0];
        }
        
        public BACnetBitString(final boolean[] real) {

                this.value = real;

        }

        public BACnetBitString(final ByteBuf data) {

        		buf=data;
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
			// TODO Auto-generated method stub
			
		}

		@Override
		public void encode(ByteBuf buf, int contextId) {
			// TODO Auto-generated method stub
			
		}

}
