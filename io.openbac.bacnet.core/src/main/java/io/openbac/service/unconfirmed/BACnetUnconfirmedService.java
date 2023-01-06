package io.openbac.service.unconfirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;

public abstract class BACnetUnconfirmedService {

	public enum Choice {
		I_AM((byte) 0x00,BACnetIAmService.class), 
		I_HAVE((byte) 0x01,null),
		UNCONFIRMED_COV_NOTIFICATION((byte) 0x02,null),
		UNCONFIRMED_EVENT_NOTIFICATION((byte) 0x03,null), 
		UNCONFIRMED_PRIVATE_TRANSFER((byte) 0x04,null),
		UNCONFIRMED_TEXT_MESSAGE((byte) 0x05,null), 
		TIME_SYNCHRONISATION((byte) 0x06,null), 
		WHO_HAS((byte) 0x07,null),
		WHO_IS((byte) 0x08,BACnetWhoIsService.class), 
		UTC_TIME_SYNCHRONISATION((byte) 0x09,null);

		public final byte serviceChoice;
		public final Class implementationClass;

		Choice(byte serviceChoice, Class implementationClass) {

			this.serviceChoice = serviceChoice;
			this.implementationClass= implementationClass;
		}

		public static Choice forId(byte serviceChoice) {

			Choice[] vals = values();

			for (int i = 0; i < vals.length; i++) {
				if (serviceChoice == vals[i].serviceChoice)
					return vals[i];

			}
			return null;
		}
	}

	/**
	 * Factory method for unconfirmed service classes
	 * @param <T>
	 * @param clazz the class to create
	 * @param apdu the apdu
	 * @return
	 * @throws BACnetParseException
	 */
	public static <T extends BACnetUnconfirmedService> T create(Class<? extends BACnetUnconfirmedService> clazz,
			final ByteBuf apdu) throws BACnetParseException {
		//TODO: does it make sense to parse the service choice here and decide on that basis ?
		if (clazz == BACnetIAmService.class) {
			return (T) new BACnetIAmService(apdu);
		} else if (clazz == BACnetWhoIsService.class) {
			return (T) new BACnetWhoIsService(apdu);
		} else if (clazz == BACnetTimeSynchronizationService.class) {
			return (T) new BACnetTimeSynchronizationService(apdu);
		}

		return null;
	}

	public abstract byte getServiceChoice();
	
	public abstract void encode(final ByteBuf buf);

}
