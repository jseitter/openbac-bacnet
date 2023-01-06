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
public class BACnetTimeSynchronizationService extends BACnetUnconfirmedService {

	BACnetDate date;
	BACnetTime time;

	@Override
	public byte getServiceChoice() {
		return BACnetUnconfirmedService.Choice.TIME_SYNCHRONISATION.serviceChoice;
	}
	
	public BACnetTimeSynchronizationService(BACnetDate date, BACnetTime time) {
		this.date = date;
		this.time = time;
	}

	public BACnetTimeSynchronizationService(final ByteBuf buf) throws BACnetParseException {
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
