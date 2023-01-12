package io.openbac.service.confirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.api.device.BACnetLocalDevice;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.net.apdu.BACnetAPDU;

public abstract class BACnetConfirmedService {

	public interface BACnetResponse {
		
		public void encode(final ByteBuf buf);
	}
	
	/**
	 * Choice to resolve IDs and implementation classes;
	 * @author joerg
	 *
	 */
	public enum Choice {

		READ_PROPERTY((byte) 12, BACnetReadPropertyService.class);

		// keep in mind that byte is signed
		public final byte serviceChoice;
		public final Class implementationClass;

		Choice(byte value, Class implementationClass) {
			this.serviceChoice = value;
			this.implementationClass = implementationClass;
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

	public static <T extends BACnetConfirmedService> T create(Class<? extends BACnetConfirmedService> clazz,
			final ByteBuf apdu) throws BACnetParseException {
		// TODO: does it make sense to parse the service choice here and decide on that
		// basis ?
		if (clazz == BACnetReadPropertyService.class) {
			return (T) new BACnetReadPropertyService(apdu);
		}

		return null;
	}

	public abstract Choice getServiceChoice();
	
	/**
	 * handles the service for the given invokeId and uses the given Device
	 * @param invokeId
	 * @param device
	 * @return
	 */
	public abstract BACnetAPDU handleService(int invokeId, BACnetLocalDevice device);
}
