package io.openbac.bacnet.type.primitive;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.type.BACnetEncodable;
import io.openbac.util.HexUtils;

/**
 *
 * Defines the BACnet Primitive Types as defined
 * in 20.2.1.4 Application Tags
 * 
 *
 * @author Tobias Breckle
 * @author Joerg Seitter
 */
public abstract class BACnetPrimitive extends BACnetEncodable {


	
		// enumeration to define all types
        enum Type {

                NULL((byte) 0, "Null"),
                BOOLEAN((byte) 1,"Boolean"),
                UNSIGNED_INTEGER((byte) 2,"Unsigned Integer"),
                SIGNED_INTEGER((byte) 3,"Signed Integer"),
                REAL((byte) 4,"Real"),
                DOUBLE((byte) 5,"Double"),
                OCTET_STRING((byte) 6,"Octet String"),
                CHARACTER_STRING((byte) 7,"Character String"),
                BIT_STRING((byte) 8,"Bit String"),
                ENUMERATED((byte) 9,"Enumerated"),
                DATE((byte) 10,"Date"),
                TIME((byte) 11,"Time"),
                BACNET_OBJECT_IDENTIFIER((byte) 12,"BACnet Object Identifier");
        		// 13 to 15 are reserved by ASHRAE

                final byte type;
                private final String name;

                Type(byte type, String name) {
                        this.type = type;
                        this.name = name;
                }

                public Type getType() {
                        return BACnetPrimitive.Type.getType(type);
                }

                public static Type getType(byte type) {

                        for (Type t : values()) {
                                if (t.type == type) {
                                        return t;
                                }
                        }
                        throw new IllegalArgumentException("unknown bacnet type id "+type);

                }

				public String getName() {
					return name;
				}

        }

        public static <T extends BACnetPrimitive> T createPrimitive(Class<? extends BACnetEncodable> clazz, ByteBuf data) throws BACnetParseException {
                
                if (clazz == BACnetNull.class) {
                        return (T) new BACnetNull();
                } else if (clazz == BACnetBoolean.class) {
                        return (T) new BACnetBoolean(data);
                } else if (clazz == BACnetUnsignedInteger.class) {
                        return (T) new BACnetUnsignedInteger(data);
                } else if (clazz == BACnetSignedInteger.class) {
                        return (T) new BACnetSignedInteger(data);
                } else if (clazz == BACnetReal.class) {
                        return (T) new BACnetReal(data);
                } else if (clazz == BACnetDouble.class) {
                        return (T) new BACnetDouble(data);
                } else if (clazz == BACnetOctetString.class) {
                        return (T) new BACnetOctetString(data);
                } else if (clazz == BACnetCharacterString.class) {
                        return (T) new BACnetCharacterString(data);
                } else if (clazz == BACnetBitString.class) {
                        return (T) new BACnetBitString(data);
                } else if (clazz == BACnetDate.class) {
                        return (T) new BACnetDate(data);
                } else if (clazz == BACnetTime.class) {
                        return (T) new BACnetTime(data);
                } else if (clazz == BACnetObjectIdentifier.class) {
                        return (T) new BACnetObjectIdentifier(data);
                }
                
                return null;
                
        }
                
        public static BACnetPrimitive createPrimitive(byte tagNumber, final ByteBuf data) throws BACnetParseException {

                Type type = Type.getType(tagNumber);
                if (type == Type.NULL) {
                        return new BACnetNull();
                } else if (type == Type.BOOLEAN) {
                        return new BACnetBoolean(data);
                } else if (type == Type.UNSIGNED_INTEGER) {
                        return new BACnetUnsignedInteger(data);
                } else if (type == Type.SIGNED_INTEGER) {
                        return new BACnetSignedInteger(data);
                } else if (type == Type.REAL) {
                        return new BACnetReal(data);
                } else if (type == Type.DOUBLE) {
                        return new BACnetDouble(data);
                } else if (type == Type.OCTET_STRING) {
                        return new BACnetOctetString(data);
                } else if (type == Type.CHARACTER_STRING) {
                        return new BACnetCharacterString(data);
                } else if (type == Type.BIT_STRING) {
                        return new BACnetBitString(data);
                } else if (type == Type.DATE) {
                        return new BACnetDate(data);
                } else if (type == Type.TIME) {
                        return new BACnetTime(data);
                } else if (type == Type.BACNET_OBJECT_IDENTIFIER) {
                        return new BACnetObjectIdentifier(data);
                } else {
                        throw new IllegalArgumentException("invalid type, tagNumber: 0x" + HexUtils.convert(tagNumber));
                }

        }

        @Override
        public Class<?> getType() {
                return BACnetPrimitive.class;
        }
        
    	/**
    	 * method to encode a primitive with application tag
    	 * @param data
    	 */
    	public abstract void encodeApplication(final ByteBuf data);

}
