package io.openbac.bacnet.type;

import io.netty.buffer.ByteBuf;

/**
 *
 * Every encodeable is based on a tag. this class provides general functions for
 * tag processing
 * 
 * @author Tobias Breckle
 * @author Joerg Seitter
 */
public abstract class BACnetEncodable {

	// Tag Structure
	// 7 6 5 4 3 2 1 0
	// |-----|-----|-----|-----|-----|-----|-----|-----|
	// | Tag Number |Class|Length/Value/Type|
	// |-----|-----|-----|-----|-----|-----|-----|-----|

	// Encoding of Class Bit
	public static final boolean CLASS_PRIMITIVE = false;
	public static final boolean CLASS_CONSTRUCTED = true;

	// Value of Length Value Type field for open Tags
	public static final int OPEN_TAG_LVT = 0x0e;
	// Value of Length Value Type field for close Tags
	public static final int CLOSE_TAG_LVT = 0x0f;

	protected ByteBuf buf;
	protected int tagNumber;
	protected boolean tagClass;
	protected int tagLVT;
	protected long length;

	// Rules to be processed
	// 1. Tag Numbers up to 14 are encoded in the top 4 bit
	// if tag number is > 14 the top bits are set to 0xf and the tag number is
	// stored in a second byte

	// 2. Check if opening tag --> process encapsulated tags till closing tag

	// 3. decode the tag and it's data

	// 3.1 If the Class is Application (Class Bit = 0)
	// the Tag Number designated the datatype and has to be decoded out of the tag
	// and the following bytes

	// 3.2 If the Class is context specific (Class Bit = 1)
	// the
	// - the bits 0-2 are always the length of the data
	// - if the datatype is ANY it always has to be put in opening and closing tags
	//

	/**
	 * Every subclass has to implement that it can encode itself into a given
	 * bytebuf with a context tag
	 * 
	 * @param buf     the buffer to encode into
	 * @param context the context id for the tag
	 */
	public abstract void encode(ByteBuf buf, int contextId);

	/**
	 * Encodes an opening tag
	 * @param contextId
	 * @return context ID shifted left 4 bits and lower bits 0xE
	 */
	protected byte encodeOpeningTag(int contextId) {
		return (byte) ((contextId << 4) | 0x0E);
	}
	
	/**
	 * Encodes a closing tag
	 * @param contextId
	 * @return context ID shifted left 4 bits and lower bits 0xF
	 */
	protected byte encodeClosingTag(int contextId) {
		return (byte) ((contextId << 4) | 0x0F);
	}
	
	/**
	 * Method to decode the tag data and positions the ByteBuf readpointer on the
	 * first byte after the tag if it is an extended tag on the first byte after the
	 * extended tag
	 */
	protected void decodeTag() {
		// read the first byte, this has to be a tag
		short tag = buf.readUnsignedByte();
		tagClass = ((tag & 0b00001000) >> 3 == 1);
		tagLVT = (tag & 0b00000111);
		// check if we have an extended tag
		if ((tag & 0xf0) != 0xf0) {
			// normal case
			tagNumber = ((tag >> 4) & 0b00001111);
		} else {
			// extended case, the read pointer is increment by 1
			tagNumber = buf.readUnsignedByte();
		}
		if (tagLVT < 5) {
			length = tagLVT;
		} else if (tagLVT == 5) {
			// extended length decoding doing a lookahead read to decode
			int next = buf.getByte(buf.readerIndex());
			if (next >= 5 && next < 254) {
				// 8bit
				length = buf.readByte();
			}
			if (next == 0xFE) {
				// 16 bit
				length = buf.readUnsignedShort();

			}
			if (next == 0xFF) {
				// 32 bit
				length = buf.readUnsignedInt();
			}
		}

	}

	public abstract Class<?> getType();

	protected String toDebugString() {
		return "[tagNumber:" + tagNumber + ", tagClass:" + tagClass + ", tagLVT:" + tagLVT + ", length: "+length+"]";
	}
}
