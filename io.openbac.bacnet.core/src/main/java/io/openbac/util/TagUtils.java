package io.openbac.util;

import io.netty.buffer.ByteBuf;


//TODO probably move this into BACNetEncodable
public class TagUtils {

	/**
	 * encodes the given context Id as tag into the buffer writeindex is advanced so
	 * that afterwards the data can be written
	 * 
	 * @param buffer
	 * @param contextId
	 */
	public static void encodeTagIdAndLength(ByteBuf buffer, int contextId, int length) {
		// TODO dynamic length encoding
		// Case 1: 0-4 in tag
		// Case 2: 5-253 in a byte after (extended) tag and tag length set to b101
		// Case 3: 254-65535 byte 0xFE after (extended) tag and the two bytes length and
		// initial
		// tag length set to b101
		// Case 4 : 65536 to 2^32 byte 0xFF after (extended) tag and 4 bytes length and
		// initial tag length set to b101
		byte tag=0x00;
		// set context bit
		tag = (byte) (tag | 0x08);
		// simple or extended tag ?
		if (contextId < 15) {
			// simple case
			tag |= (byte) ((contextId << 4) & 0xf0);
			// dynamic length encoding
			if (length < 5) {
				// Case 1:
				// encode the length in the lower 3 bit
				tag = (byte) (tag | (length & 0x07));
				buffer.writeByte(tag);
			} else if (length >= 5 && length < 254) {
				// Case 2:
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(tag);
				buffer.writeByte(length);
			} else if (length >= 254 && length < 65536) {
				// Case 3:
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(0xFE);
				buffer.writeShort(length);
			} else if (length >= 65536 && length < 4294967295l) {
				// Case4: < 2^32-1
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(0xFF);
				buffer.writeInt(length);
			}

			
		} else {
			
			// extended encoding
			tag = (byte) 0xf0;
			// encode the length in the lower 3 bit
			tag = (byte) (tag | (length & 0x07));

			if (length < 5) {
				// Case 1:
				// encode the length in the lower 3 bit
				tag = (byte) (tag | (length & 0x07));
				buffer.writeByte(tag);
				tag = (byte) contextId;
				buffer.writeByte(tag);

			} else if (length >= 5 && length < 254) {
				// Case 2:
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(tag);
				tag = (byte) contextId;
				buffer.writeByte(tag);
				buffer.writeByte(length);
			} else if (length >= 254 && length < 65536) {
				// Case 3:
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(tag);
				tag = (byte) contextId;
				buffer.writeByte(tag);				
				buffer.writeByte(0xFE);
				buffer.writeShort(length);
			} else if (length >= 65536 && length < 4294967295l) {
				// Case4: < 2^32-1
				tag = (byte) (tag | (length & 0x05));
				buffer.writeByte(tag);
				tag = (byte) contextId;
				buffer.writeByte(tag);
				buffer.writeByte(0xFF);
				buffer.writeInt(length);
			}
		}
	}

	public static void encodeApplicationTagAndLength(ByteBuf buffer, int applicationTag, int length) {
		byte tag = (byte) ((applicationTag << 4) & 0xf0);
		// set application bit (class=0)
		tag = (byte) (tag & 0xf8);
		// encode the length in the lower 3 bit
		tag = (byte) (tag | (length & 0x07));
		buffer.writeByte(tag);
	}
}
