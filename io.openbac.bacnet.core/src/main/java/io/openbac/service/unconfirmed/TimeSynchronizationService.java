package io.openbac.service.unconfirmed;

import io.netty.buffer.ByteBuf;
import io.openbac.bacnet.exceptions.BACnetParseException;
import io.openbac.bacnet.type.primitive.BACnetDate;
import io.openbac.bacnet.type.primitive.BACnetPrimitive;
import io.openbac.bacnet.type.primitive.BACnetTime;

/**
 * Service to send data and time information to other BACnet Systems
 * 
 * @author Joerg Seitter
 *
 */
public class TimeSynchronizationService extends BACnetUnconfirmedService {

	BACnetDate date;
	BACnetTime time;

	public TimeSynchronizationService(BACnetDate date, BACnetTime time) {
		this.date = date;
		this.time = time;
	}

	public TimeSynchronizationService(final ByteBuf buf) throws BACnetParseException {
		date = BACnetPrimitive.createPrimitive(BACnetDate.class, buf);
		time = BACnetPrimitive.createPrimitive(BACnetTime.class, buf);
	}

	@Override
	public void encode(final ByteBuf buf) {
		buf.writeByte(BACnetUnconfirmedService.Choice.TIME_SYNCHRONISATION.serviceChoice);
		date.encodeApplication(buf);
		time.encodeApplication(buf);
	}

}
