package io.openbac.service.unconfirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;

public abstract class BACnetUnconfirmedService {

	public enum Choice {
		I_AM((byte) 0x00), I_HAVE((byte) 0x01), UNCONFIRMED_COV_NOTIFICATION((byte) 0x02),
		UNCONFIRMED_EVENT_NOTIFICATION((byte) 0x03), UNCONFIRMED_PRIVATE_TRANSFER((byte) 0x04),
		UNCONFIRMED_TEXT_MESSAGE((byte) 0x05), TIME_SYNCHRONISATION((byte) 0x06), WHO_HAS((byte) 0x07),
		WHO_IS((byte) 0x08), UTC_TIME_SYNCHRONISATION((byte) 0x09);

		byte serviceChoice;

		Choice(byte serviceChoice) {

			this.serviceChoice = serviceChoice;
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
		if (clazz == IAmService.class) {
			return (T) new IAmService(apdu);
		} else if (clazz == WhoIsService.class) {
			return (T) new WhoIsService(apdu);
		} else if (clazz == TimeSynchronizationService.class) {
			return (T) new TimeSynchronizationService(apdu);
		}

		return null;
	}

	public abstract void encode(final ByteBuf buf);

}
