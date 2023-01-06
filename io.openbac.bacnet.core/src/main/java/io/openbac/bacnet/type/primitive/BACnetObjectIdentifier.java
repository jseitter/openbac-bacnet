/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.openbac.bacnet.type.primitive;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.object.BACnetObjectType.ObjectType;
import io.openbac.util.TagUtils;

/**
 *
 * @author Tobias Breckle
 * @author Joerg Seitter
 */
public class BACnetObjectIdentifier extends BACnetPrimitive {

        private ObjectType objectType; // 10 bit Object Type
        private Integer instance; // 22 bit instance ID

        public BACnetObjectIdentifier(final int objectType, final int instance) {

                this(ObjectType.getObjectType(objectType), instance);

        }
        
        public BACnetObjectIdentifier(final ObjectType objectType, final int instance) {
                
                this.objectType = objectType;
                this.instance = instance;
                
        }

        /**
         * decode the object from given byteArray
         * @param data
         */
        public BACnetObjectIdentifier(final ByteBuf buffer) {

        		buf=buffer;
        		decodeTag();
        		
                if (tagLVT != 4) {
                        throw new IllegalArgumentException("Invalid length: " + tagLVT);
                }
                byte[] data = new byte[4];
                
                buf.readBytes(data);
                // extract the object Type
                byte t1 = data[0];
                byte t2 = (byte) (((data[1] & 0b11000000) >> 6) & 0b00000011);
                short s1 = t1;
                s1 = (short) (s1 << 2);
                s1 |= t2;
                int objType = s1;
                this.objectType = ObjectType.getObjectType(objType);

                byte[] arr = new byte[4];
                arr[0] = 0;
                arr[1] = (byte) (data[1] & 0b00111111);
                arr[2] = data[2];
                arr[3] = data[3];
                ByteBuffer bb = ByteBuffer.wrap(arr);
                instance = bb.getInt();
        }
		
        /**
         * serialize the object into the given ByteBuffer
         */
        @Override
		public void encode(final ByteBuf data, int contextId) {
		
        	TagUtils.encodeTagIdAndLength(buf, contextId, 4);
        	
        	byte[] result = new byte[4];
        	int objType = objectType.getObjectType();
        	
        	result[3] = (byte)(instance & 0x000000ff);
        	result[2] = (byte)((instance & 0x0000ff00) >> 8);
        	result[1] = (byte)((instance & 0x003f0000) >> 16);
        	result[1] = (byte) (result[1] | (byte)((objType & 0x03) << 6 ));
        	result[0] = (byte)((objType & 0x3fc ) >> 2);
        	
        	data.writeBytes(result);
        					        					
		}

		@Override
		public void encodeApplication(ByteBuf data) {
			TagUtils.encodeApplicationTagAndLength(data, BACnetPrimitive.Type.BACNET_OBJECT_IDENTIFIER.type, 4);
			
        	byte[] result = new byte[4];
        	int objType = objectType.getObjectType();
        	
        	result[3] = (byte)(instance & 0x000000ff);
        	result[2] = (byte)((instance & 0x0000ff00) >> 8);
        	result[1] = (byte)((instance & 0x003f0000) >> 16);
        	result[1] = (byte) (result[1] | (byte)((objType & 0x03) << 6 ));
        	result[0] = (byte)((objType & 0x3fc ) >> 2);
        	
        	data.writeBytes(result);
		}
		
        @Override
        public String toString() {
                return "BACnetObjectIdentifier{" + "objectType=" + objectType + ", instance=" + instance + '}';
        }

        /**
         * @return the objectType
         */
        public int getObjectTypeInt() {
                return objectType.getObjectType();
        }

        /**
         * @param objectType the objectType to set
         */
        public void setObjectType(int objectType) {
                this.objectType = ObjectType.getObjectType(objectType);
        }
        
        /**
         * @return the objectType
         */
        public ObjectType getObjectType() {
                return objectType;
        }

        /**
         * @param objectType the objectType to set
         */
        public void setObjectType(ObjectType objectType) {
                this.objectType = objectType;
        }

        /**
         * @return the instance
         */
        public int getInstance() {
                return instance;
        }

        /**
         * @param instance the instance to set
         */
        public void setInstance(int instance) {
                this.instance = instance;
        }

        @Override
        public Class<?> getType() {
                return BACnetObjectIdentifier.class;
        }





}
