package io.openbac.service.confirmed;

/**
 * Enum with all supported services
 * @author joerg
 *
 */
public enum ConfirmedServiceChoice {

	READ_PROPERTY((byte)12);

	// using byte here limits us to 128 service (becuase it is signed)
	// this is no problem yet, but keep in mind
	byte value;

	ConfirmedServiceChoice(byte value) {
		this.value=value;
	}
	
}
