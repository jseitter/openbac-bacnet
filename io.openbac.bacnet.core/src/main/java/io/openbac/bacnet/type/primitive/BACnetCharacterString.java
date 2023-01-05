package io.openbac.bacnet.type.primitive;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

/**
 * @author Tobias Breckle
 * @author Joerg Seitter
 * 
 */
public class BACnetCharacterString extends BACnetPrimitive {

	public enum Encodings {

		ANSI_X3_4((byte) 0x00), IBM_MS_DBCS((byte) 0x01), JIS_C_6226((byte) 0x02), ISO_10646_UCS_4((byte) 0x03),
		ISO_10646_UCS_2((byte) 0x04), ISO_8859_1((byte) 0x05);

		private final byte encoding;

		Encodings(final byte encoding) {
			this.encoding = encoding;
		}

		public int getEncoding() {
			return encoding;
		}

		public static Encodings getEncoding(byte encoding) {

			for (Encodings e : values()) {
				if (e.encoding == encoding) {
					return e;
				}
			}
			throw new IllegalArgumentException("encoding has invalid value");

		}

	}

	private String value;
	private Encodings encoding;

	public BACnetCharacterString(String value) {

		this.value = value;
		this.encoding = Encodings.ANSI_X3_4;

	}

	public BACnetCharacterString(final ByteBuf data) {

		buf=data;
		decodeTag();
		
		this.encoding = Encodings.getEncoding(buf.readByte());
		byte[] dataWithoutEncoding = new byte[(int)length - 1];
		buf.readBytes(dataWithoutEncoding);
		
		try {

			switch (this.encoding) {
			case ANSI_X3_4:
				this.value = new String(dataWithoutEncoding, "UTF-8");
				break;
			case ISO_10646_UCS_2:
				this.value = new String(dataWithoutEncoding, "UTF-16");
				break;
			case ISO_8859_1:
				this.value = new String(dataWithoutEncoding, "ISO-8859-1"); //TODO: testcase
				break;
			case IBM_MS_DBCS:
				this.value = new String(dataWithoutEncoding, "Cp850"); //TODO: testcase
				break;
			case ISO_10646_UCS_4:
				this.value = new String(dataWithoutEncoding, "UTF-32"); //TODO: testcase
				break;
			case JIS_C_6226:
				this.value = new String(dataWithoutEncoding, "x-JIS0208"); //TODO: testcase
				break;

			}

		} catch (UnsupportedEncodingException ex) {
				//TODO: Exception handling
		}

	}

	public BACnetCharacterString(byte encoding, byte[] data) {

		this.encoding = Encodings.getEncoding(encoding);

		try {

			switch (this.encoding) {
			case ANSI_X3_4:
				this.value = new String(data, "UTF-8");
				break;
			case ISO_10646_UCS_2:
				this.value = new String(data, "UTF-16");
				break;
			case ISO_8859_1:
				this.value = new String(data, "ISO-8859-1");
				break;
			case IBM_MS_DBCS:
				this.value = new String(data, "Cp850");
				break;
			case ISO_10646_UCS_4:
				this.value = new String(data, "UTF-32");
				break;
			case JIS_C_6226:
				this.value = new String(data, "x-JIS0208");
				break;

			}

		} catch (UnsupportedEncodingException ex) {
			// TODO throw encoding not supported bacnet exception
		}

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Encodings getEncoding() {
		return this.encoding;
	}



	@Override
	public String toString() {
		return "BACnetCharacterString{" + "encoding="+encoding.name()+", value=" + value + '}';
	}

	@Override
	public Class<?> getType() {
		return BACnetCharacterString.class;
	}

	@Override
	public void encodeApplication(ByteBuf data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void encode(ByteBuf buf, int contextId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BACnetCharacterString other = (BACnetCharacterString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
