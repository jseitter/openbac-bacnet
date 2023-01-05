package io.openbac.util;

public class ByteUtils {

	/**
	 * Read a unsigned value from a byte
	 * 
	 * @param signed
	 *            byte value
	 * @return unsigned int value
	 */
	public static int readUnsignedByte(byte b) {
		return b & 0xFF;
	}

	/**
	 * Read a short from the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array to read from
	 * @param offset
	 *            The offset to start reading at
	 * @return The short read
	 */
	public static short readShort(byte[] bytes, int offset) {
		return (short) ((bytes[offset] << 8) | (bytes[offset + 1] & 0xff));
	}

	/**
	 * Read an unsigned short from the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array to read from
	 * @param offset
	 *            The offset to start reading at
	 * @return The short read
	 */
	public static int readUnsignedShort(byte[] bytes, int offset) {
		return (((bytes[offset] & 0xff) << 8) | (bytes[offset + 1] & 0xff));
	}

	/**
	 * Read an int from the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array to read from
	 * @param offset
	 *            The offset to start reading at
	 * @return The int read
	 */
	public static int readInt(byte[] bytes, int offset) {
		return (((bytes[offset + 0] & 0xff) << 24) | ((bytes[offset + 1] & 0xff) << 16)
				| ((bytes[offset + 2] & 0xff) << 8) | (bytes[offset + 3] & 0xff));
	}

	/**
	 * Read an unsigned integer from the given byte array
	 * 
	 * @param bytes
	 *            The bytes to read from
	 * @param offset
	 *            The offset to begin reading at
	 * @return The integer as a long
	 */
	public static long readUnsignedInt(byte[] bytes, int offset) {
		return (((bytes[offset + 0] & 0xffL) << 24) | ((bytes[offset + 1] & 0xffL) << 16)
				| ((bytes[offset + 2] & 0xffL) << 8) | (bytes[offset + 3] & 0xffL));
	}

	/**
	 * Read a long from the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array to read from
	 * @param offset
	 *            The offset to start reading at
	 * @return The long read
	 */
	public static long readLong(byte[] bytes, int offset) {
		return (((long) (bytes[offset + 0] & 0xff) << 56) | ((long) (bytes[offset + 1] & 0xff) << 48)
				| ((long) (bytes[offset + 2] & 0xff) << 40) | ((long) (bytes[offset + 3] & 0xff) << 32)
				| ((long) (bytes[offset + 4] & 0xff) << 24) | ((long) (bytes[offset + 5] & 0xff) << 16)
				| ((long) (bytes[offset + 6] & 0xff) << 8) | ((long) bytes[offset + 7] & 0xff));
	}

	/**
	 * Read the given number of bytes into a long
	 * 
	 * @param bytes
	 *            The byte array to read from
	 * @param offset
	 *            The offset at which to begin reading
	 * @param numBytes
	 *            The number of bytes to read
	 * @return The long value read
	 */
	public static long readBytes(byte[] bytes, int offset, int numBytes) {
		int shift = 0;
		long value = 0;
		for (int i = offset + numBytes - 1; i >= offset; i--) {
			value |= (bytes[i] & 0xFFL) << shift;
			shift += 8;
		}
		return value;
	}

	/**
	 * Write a short to the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array
	 * @param value
	 *            The short to write
	 * @param offset
	 *            The offset to begin writing at
	 */
	public static void writeShort(byte[] bytes, short value, int offset) {
		bytes[offset] = (byte) (0xFF & (value >> 8));
		bytes[offset + 1] = (byte) (0xFF & value);
	}

	/**
	 * Write an unsigned short to the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array
	 * @param value
	 *            The short to write
	 * @param offset
	 *            The offset to begin writing at
	 */
	public static void writeUnsignedShort(byte[] bytes, int value, int offset) {
		bytes[offset] = (byte) (0xFF & (value >> 8));
		bytes[offset + 1] = (byte) (0xFF & value);
	}

	/**
	 * Write an int to the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array
	 * @param value
	 *            The int to write
	 * @param offset
	 *            The offset to begin writing at
	 */
	public static void writeInt(byte[] bytes, int value, int offset) {
		bytes[offset] = (byte) (0xFF & (value >> 24));
		bytes[offset + 1] = (byte) (0xFF & (value >> 16));
		bytes[offset + 2] = (byte) (0xFF & (value >> 8));
		bytes[offset + 3] = (byte) (0xFF & value);
	}

	/**
	 * Write a long to the byte array starting at the given offset
	 * 
	 * @param bytes
	 *            The byte array
	 * @param value
	 *            The long to write
	 * @param offset
	 *            The offset to begin writing at
	 */
	public static void writeLong(byte[] bytes, long value, int offset) {
		bytes[offset] = (byte) (0xFF & (value >> 56));
		bytes[offset + 1] = (byte) (0xFF & (value >> 48));
		bytes[offset + 2] = (byte) (0xFF & (value >> 40));
		bytes[offset + 3] = (byte) (0xFF & (value >> 32));
		bytes[offset + 4] = (byte) (0xFF & (value >> 24));
		bytes[offset + 5] = (byte) (0xFF & (value >> 16));
		bytes[offset + 6] = (byte) (0xFF & (value >> 8));
		bytes[offset + 7] = (byte) (0xFF & value);
	}

}
